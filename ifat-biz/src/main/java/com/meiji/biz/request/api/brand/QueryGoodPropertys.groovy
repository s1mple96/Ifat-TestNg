package com.meiji.biz.request.api.brand

import com.meiji.biz.request.api.DubboRequest
import com.miyuan.ifat.support.test.TestContext

class QueryGoodPropertys extends DubboRequest{
    {
        super.interfaceName = "com.platform.goods.proxy.api.GoodPropertyMgrService"  //接口路径
        super.methodName="queryGoodPropertys"
        super.version="1.0"
        super.group="on"
        super.timeOut=10000
        super.paramsType=["com.meiji.common.bean.vo.PageReqVO"] //请求参数
        super.params = [["pageIndex","pageSize","queryCondition"]]
    }

    DubboRequest invoke(TestContext testContext) {
        super.invoke(testContext)
        return this
    }
}
