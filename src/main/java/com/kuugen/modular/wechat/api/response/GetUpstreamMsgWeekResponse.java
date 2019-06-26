package com.kuugen.modular.wechat.api.response;



import com.kuugen.modular.wechat.api.entity.UpstreamMsgWeek;

import java.util.List;

/**
 * @author peiyu
 */
public class GetUpstreamMsgWeekResponse extends BaseResponse {

    private List<UpstreamMsgWeek> list;

    public List<UpstreamMsgWeek> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgWeek> list) {
        this.list = list;
    }
}
