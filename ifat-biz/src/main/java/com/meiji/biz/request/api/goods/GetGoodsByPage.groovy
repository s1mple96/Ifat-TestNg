package com.meiji.biz.request.api.goods

import com.meiji.biz.request.api.DubboRequest
import com.miyuan.ifat.support.test.TestContext

class GetGoodsByPage extends DubboRequest{
    {
        super.interfaceName = "com.platform.goods.proxy.api.GoodsMgrService"  //接口路径
        super.methodName="getGoodsByPage"
        super.version="1.0"
        super.group="on"
        super.timeOut=3000
        super.paramsType=["com.platform.goods.proxy.vo.request.GoodsConditionRequestVO"] //请求参数
        super.params = [["categoryId","spuCodeAndName","skuCode","startPrice","endPrice","startNum","endNum","startDate","endDate","pageNum","pageSize"]]
    }


    DubboRequest invoke(TestContext testContext) {
        super.invoke(testContext)
        return this
    }

}
