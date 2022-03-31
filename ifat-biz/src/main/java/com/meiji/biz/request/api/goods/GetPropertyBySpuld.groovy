package com.meiji.biz.request.api.goods

import com.meiji.biz.request.api.DubboRequest
import com.miyuan.ifat.support.test.TestContext

class GetPropertyBySpuld extends DubboRequest{
    {
        super.interfaceName = "com.platform.goods.proxy.api.GoodsMgrService"  //接口路径
        super.methodName="getPropertyBySpuId"
        super.version="1.0"
        super.group="on"
        super.timeOut=3000
        super.paramsType = ["com.platform.goods.proxy.vo.request.PropertySearchRequestVO"] //请求参数
        super.params = ["spuId"]
    }


    DubboRequest invoke(TestContext testContext) {
        super.invoke(testContext)
        return this
    }

}
