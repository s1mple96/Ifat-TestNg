package com.meiji.biz.api

import com.miyuan.ifat.support.factory.FactorySupport
import org.elasticsearch.client.RestHighLevelClient

class EsAPI {
    @Lazy
    static RestHighLevelClient transportClient = FactorySupport.createHighLevelEs("es-miyuan")
    static RestHighLevelClient prodGoodsClient = FactorySupport.createHighLevelEs("meiji_es")
}
