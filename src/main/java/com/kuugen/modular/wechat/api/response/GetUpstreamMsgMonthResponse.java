package com.kuugen.modular.wechat.api.response;

import com.kuugen.modular.wechat.api.entity.UpstreamMsgMonth;

import java.util.List;

/**
 * @author peiyu
 */
public class GetUpstreamMsgMonthResponse extends BaseResponse {

    private List<UpstreamMsgMonth> list;

    public List<UpstreamMsgMonth> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgMonth> list) {
        this.list = list;
    }
}
