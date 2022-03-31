package com.meiji.biz.request.api.category

import com.meiji.biz.request.api.DubboRequest
import com.miyuan.ifat.support.test.TestContext

class GetCategoryByName extends DubboRequest{
    {
        super.interfaceName = "com.platform.goods.proxy.api.CategoryMgrService"  //接口路径
        super.methodName="getCategoryByName"
        super.version="1.0"
        super.group="on"
        super.timeOut=10000
        super.paramsType=["com.platform.goods.proxy.vo.request.CategorySearchBaseRequestVO"] //请求参数
        super.params = [["name","type"]]
    }

    DubboRequest invoke(TestContext testContext) {
        super.invoke(testContext)
        return this
    }
}
