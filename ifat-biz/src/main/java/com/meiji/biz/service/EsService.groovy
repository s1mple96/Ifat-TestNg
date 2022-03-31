package com.meiji.biz.service

import com.meiji.biz.api.EsAPI
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder

class EsService extends EsAPI{
    static List<Map> getItemByitemTitle(String itemTitle){
        SearchResponse searchResponse = transportClient.prepareSearch("item")
                .setQuery(QueryBuilders.matchQuery("itemTitle",itemTitle))
                .setFrom(0).setSize(10).setExplain(true)
                .execute()
                .actionGet()
        List list = new ArrayList()
        searchResponse.hits.forEach { x ->
            list.add(x.getSource())
        }
        return list
    }

    static List<Map> findShopGoodsDetailByES(String spuId) {
        SearchRequest searchRequest = new SearchRequest("spu17")
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("spuId", spuId))).size(10).explain(true)
        searchRequest.source(searchSourceBuilder)
        SearchResponse searchResponse = prodGoodsClient.search(searchRequest, RequestOptions.DEFAULT)
//        System.out.println("searchResponse输出：" + searchResponse)
        List list = new ArrayList()
        searchResponse.hits.forEach { x ->
            list.add(x.getSourceAsMap())
        }
        return list
    }
}
