package com.meiji.biz.service

import com.meiji.biz.api.RedisAPI


class RedisService extends RedisAPI {

    static Object get(String key){
        Object value = meijiRedis.get(key)
        return value
    }

    static Long ttl(String key){
        Long value = meijiRedis.ttl(key)
        return value
    }


}
