package com.miyuan.ifat.support.test

import com.alibaba.fastjson.JSON
import com.miyuan.ifat.support.vo.Record


class TestContext extends HashMap {
    public String getToken() {
        return get("token")
    }

    public void setUser(String userId){
        put("userId", userId)
    }


    public Map getResponse() {
        if(super.get("response") instanceof Map){
            return super.get("response") as Map
        }
        return (Map) JSON.parse(super.get("response").toString())
    }

    public Map getRequest() {
        return (Map) JSON.parse(super.get("request").toString())
    }

    public void setResponse(Object response) {
        put("response", response)
    }

    public void setRequest(Object request) {
        put("request", request)
    }

    public void appendLog(Record record) {
        List list = new ArrayList<>()
        def last = get("record")
        if (last) {
            list.addAll(last as List)
        }
        list.add(record)
        super.put("record", list)
    }

    @Override
    String toString() {
        return get("description")
    }
}
