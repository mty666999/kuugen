package com.kuugen.modular.wechat.api;

import com.kuugen.modular.wechat.api.config.ApiConfig;
import com.kuugen.modular.wechat.api.enums.MediaType;
import com.kuugen.modular.wechat.api.response.BaseResponse;
import com.kuugen.modular.wechat.api.response.DownloadMediaResponse;
import com.kuugen.modular.wechat.api.response.UploadMediaResponse;
import com.kuugen.modular.wechat.util.APIConfigUtils;
import com.kuugen.modular.wechat.util.JSONUtil;
import com.kuugen.modular.wechat.util.NetWorkCenter;
import com.kuugen.modular.wechat.util.StreamUtil;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 多媒体资源API
 *
 * @author peiyu
 */
public class MediaAPI extends BaseAPI {

    private static final Logger LOG = LoggerFactory.getLogger(MediaAPI.class);

    public MediaAPI(ApiConfig config) {
        super(config);
    }

    /**
     * 上传资源，会在微信服务器上保存3天，之后会被删除
     *
     * @param type 资源类型
     * @param file 需要上传的文件
     * @return 响应对象
     */
    public UploadMediaResponse uploadMedia(MediaType type, File file) {
        UploadMediaResponse response;
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=#&type=" + type.toString();
        BaseResponse r = executePost(url, null, file);
        response = JSONUtil.toBean(r.getErrmsg(), UploadMediaResponse.class);
        return response;
    }
    /**
     * 上传资源，永久素材
     *
     * @param type 资源类型
     * @param file 需要上传的文件
     * @return 响应对象
     * @throws Exception 
     */
    public String addMedia(MediaType type, File file) throws Exception {
    	String access_token =APIConfigUtils.getApiConfig().getAccessToken();
        String result="";
        String action = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+access_token+"&type=" + type.toString();
        URL url = new URL(action);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
 
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (StringUtils.isEmpty(result)) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        
        return result;
    }
    /**
     * 下载资源，实现的很不好，反正能用了。搞的晕了，之后会优化
     *
     * @param mediaId 微信提供的资源唯一标识
     * @return 响应对象
     */
    public DownloadMediaResponse downloadMedia(String mediaId) {
        DownloadMediaResponse response = new DownloadMediaResponse();
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + this.config.getAccessToken() + "&media_id=" + mediaId;
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(NetWorkCenter.CONNECT_TIMEOUT).setConnectTimeout(NetWorkCenter.CONNECT_TIMEOUT).setSocketTimeout(NetWorkCenter.CONNECT_TIMEOUT).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpGet get = new HttpGet(url);
        try {
            CloseableHttpResponse r = client.execute(get);
            if (HttpStatus.SC_OK == r.getStatusLine().getStatusCode()) {
                InputStream inputStream = r.getEntity().getContent();
                Header[] headers = r.getHeaders("Content-disposition");
                if (null != headers && 0 != headers.length) {
                    Header length = r.getHeaders("Content-Length")[0];
                    response.setContent(inputStream, Integer.valueOf(length.getValue()));
                    response.setFileName(headers[0].getElements()[0].getParameterByName("filename").getName());
                } else {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    StreamUtil.copy(inputStream, out);
                    String json = out.toString();
                    response = JSONUtil.toBean(json, DownloadMediaResponse.class);
                }
            }
        } catch (IOException e) {
            LOG.error("IO处理异常", e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                LOG.error("异常", e);
            }
        }
        return response;
    }
}
