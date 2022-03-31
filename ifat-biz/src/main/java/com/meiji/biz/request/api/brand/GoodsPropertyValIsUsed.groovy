package com.meiji.biz.request.api.brand

import com.meiji.biz.request.api.DubboRequest
import com.miyuan.ifat.support.test.TestContext

class GoodsPropertyValIsUsed extends DubboRequest{
    {
        super.interfaceName = "com.platform.goods.proxy.api.GoodPropertyMgrService"  //接口路径
        super.methodName="goodsPropertyValIsUsed"
        super.version="1.0"
        super.group="on"
        super.timeOut=10000
        super.paramsType=["com.platform.goods.proxy.vo.request.GoodsPropertyValueVO"] //请求参数
        super.params = [["id"]]
    }

    DubboRequest invoke(TestContext testContext) {
        super.invoke(testContext)
        return this
    }
}
