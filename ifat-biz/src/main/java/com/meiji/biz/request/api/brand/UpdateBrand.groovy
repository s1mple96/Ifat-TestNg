package com.meiji.biz.request.api.brand

import com.meiji.biz.request.api.DubboRequest
import com.miyuan.ifat.support.test.TestContext

class UpdateBrand extends DubboRequest{
    {
        super.interfaceName = "com.platform.goods.proxy.api.BrandMgrService"  //接口路径
        super.methodName="updateBrand"
        super.version="1.0"
        super.group="on"
        super.timeOut=10000
        super.paramsType=["com.platform.goods.proxy.vo.request.UpdateBrandReqVO"] //请求参数
        super.params = [["name","id","icon","remark","updateBy"]]
    }

    DubboRequest invoke(TestContext testContext) {
        super.invoke(testContext)
        return this
    }
}
