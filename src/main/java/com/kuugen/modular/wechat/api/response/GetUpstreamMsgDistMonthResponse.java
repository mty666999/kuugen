package com.kuugen.modular.wechat.api.response;

import com.kuugen.modular.wechat.api.entity.UpstreamMsgDistMonth;

import java.util.List;

/**
 * @author peiyu
 */
public class GetUpstreamMsgDistMonthResponse extends BaseResponse {

    private List<UpstreamMsgDistMonth> list;

    public List<UpstreamMsgDistMonth> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgDistMonth> list) {
        this.list = list;
    }
}
