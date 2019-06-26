package com.kuugen.modular.wechat.api.response;

import com.kuugen.modular.wechat.api.entity.UserReadHour;

import java.util.List;

/**
 * @author peiyu
 */
public class GetUserReadHourResponse extends BaseResponse {

    private List<UserReadHour> list;

    public List<UserReadHour> getList() {
        return list;
    }

    public void setList(List<UserReadHour> list) {
        this.list = list;
    }
}
