package com.meiji.biz.service

import com.meiji.biz.api.MongoAPI
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Accumulators
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import org.bson.conversions.Bson

class MongoService extends MongoAPI{

    static List getUserOderByUserId(int userId){
        MongoCollection mongoCollection =mongoDatabase.getCollection("miyuan_user_order")
        Bson filter = Filters.eq("userId", userId)
        List list = mongoCollection.find(filter).skip(0).limit(10).asList()
        return list
    }

    static Map getUserOderByOrderId(Integer userId,String channelOrderId){
        MongoCollection mongoCollection = mongoDatabase.getCollection("miyuan_user_order")
        Bson filter = Filters.and(Filters.eq("channelOrderId", channelOrderId),Filters.eq("userId", userId))
        List<Map> list = mongoCollection.find(filter).skip(0).limit(5).toList()
        return list.get(0)
    }

    static List<Map> getUserOdersByOrderId(Integer userId, List orders){
        MongoCollection mongoCollection = mongoDatabase.getCollection("miyuan_user_order")
        Bson filter = Filters.and(Filters.in("channelOrderId", orders),Filters.eq("userId", userId))
        List<Map> list = mongoCollection.find(filter).skip(0).limit(10).toList()
        return list
    }

    static List<Map> getUserOdersBychannel(Integer userId, String channel,Date strattime ,Date endtime){
        MongoCollection mongoCollection = mongoDatabase.getCollection("miyuan_user_order")
        Bson filter = Filters.and(Filters.in("channelCode", channel),Filters.eq("userId", userId),Filters.gte("orderCreateTime",strattime),Filters.lt("orderCreateTime",endtime))
        List<Map> list = mongoCollection.find(filter).toList()
        return list
    }
//mongod taobao_order 订单集合
    static List<Map> getTbOdersByOrderId(List orders){
        MongoCollection mongoCollection = mongoDatabase.getCollection("miyuan_taobao_order")

        Bson filter = Filters.and(Filters.in("_id", orders))
        List<Map> list = mongoCollection.find(filter).skip(0).limit(10).toList()
        return list
    }
// mongodb miyuan_applet_order 京东 订单集合
    static List<Map> getJdOdersByOrderId(List orders){
        MongoCollection mongoCollection = mongoDatabase.getCollection("miyuan_applet_order")

        Bson filter = Filters.and(Filters.in("_id", orders))
        List<Map> list = mongoCollection.find(filter).skip(0).limit(10).toList()
        return list
    }
    // mongodb miyuan_common_order cps 订单集合
    static List<Map> getCpsOdersByOrderId(List orders){
        MongoCollection mongoCollection = mongoDatabase.getCollection("miyuan_common_order")

        Bson filter = Filters.and(Filters.in("_id", orders))
        List<Map> list = mongoCollection.find(filter).skip(0).limit(10).toList()
        return list
    }
  //mongodb 佣金预估汇总
    static List<Map> getSumOrder(Integer userId,String channelCode,List orderStatus,Date startTime,Date endTime){
        MongoCollection mongoCollection = mongoDatabase.getCollection("miyuan_user_order")
        def list = mongoCollection.aggregate(Arrays.asList(
                Aggregates.match(Filters.and(Filters.eq("userId", userId),
                        Filters.in("channelCode",channelCode),
                       Filters.in("orderStatus",orderStatus),
                       Filters.gte("orderCreateTime",startTime),Filters.lt("orderCreateTime",endTime))),
                Aggregates.group("\$userId",
                        Accumulators.sum("commission","\$commission"),
                        Accumulators.sum("commissionTotalAfterTax","\$commissionTotalAfterTax"),
                        Accumulators.sum("paymentAmount","\$paymentAmount"),
                        Accumulators.sum("cnt",1L))))
        list.each {x->println(x)}
        return list as List
    }
//mongo 结算佣金汇总
    static List<Map> getSumSettleOrder(Integer userId,String channelCode,Date startTime,Date endTime){
        MongoCollection mongoCollection = mongoDatabase.getCollection("miyuan_user_order")
        def list = mongoCollection.aggregate(Arrays.asList(
                Aggregates.match(Filters.and(Filters.eq("userId", userId),
                        Filters.in("channelCode",channelCode),
                        Filters.in("orderStatus",3),
                        Filters.gte("settlementTime",startTime),Filters.lt("settlementTime",endTime))),
                Aggregates.group("\$userId",
                        Accumulators.sum("commission","\$commission"),
                        Accumulators.sum("commissionTotalAfterTax","\$commissionTotalAfterTax"),
                        Accumulators.sum("paymentAmount","\$paymentAmount"),
                        Accumulators.sum("cnt",1L))))
        list.each {x->println(x)}
        return list as List
    }
}
