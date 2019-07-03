package com.kuugen.modular.wechat.api.response;

/**
 * @author peiyu
 */
public class GetGroupsResponse extends BaseResponse {

    private Integer id;
    private String  name;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}