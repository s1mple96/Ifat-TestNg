package com.meiji.biz.request.api.brand

import com.meiji.biz.request.api.DubboRequest
import com.miyuan.ifat.support.test.TestContext

class SelectPage extends DubboRequest{
    {
        super.interfaceName = "com.platform.goods.proxy.api.GoodPropGroupMgrService"  //接口路径
        super.methodName="selectPage"
        super.version="1.0"
        super.group="on"
        super.timeOut=10000
        super.paramsType=["com.meiji.common.bean.vo.PageReqVO"] //请求参数
        super.params = [["queryCondition","pageIndex","pageSize"]]
    }

    DubboRequest invoke(TestContext testContext) {
        super.invoke(testContext)
        return this
    }
}
