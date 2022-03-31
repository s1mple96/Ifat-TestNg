package com.meiji.biz.api

import com.miyuan.ifat.support.factory.FactorySupport
import redis.clients.jedis.JedisCluster

class RedisAPI {
    static JedisCluster meijiRedis = FactorySupport.createRedis("meiji-redis")

}
