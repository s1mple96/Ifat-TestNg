package com.meiji.biz.request.api

import com.meiji.biz.service.DubboService
import com.miyuan.ifat.support.test.TestContext
import com.miyuan.ifat.support.util.ResourceUtil
import com.miyuan.ifat.support.vo.Record
import org.apache.commons.lang3.ObjectUtils

import java.lang.reflect.Method

abstract class DubboRequest {
    public String interfaceName
    public String methodName
    public String version
    public String group
    public Integer timeOut
    public List paramsType
    public List params
    public String preInvoke

    DubboRequest invoke(TestContext testContext){
        String address  = ResourceUtil.getBeanData("dubbo").get("url")
        List req = new ArrayList()
        Map reqMapForLog = new HashMap()
        for(int i=0;i<params.size();i++){
            if(!paramsType.get(i).toString().startsWith("java.")) {
                List list = params.get(i) as List
                Map reqParam = new HashMap()
                for (String str : list) {
                    if (ObjectUtils.isNotEmpty(testContext.get(str))) {
                        reqParam.put(str, testContext.get(str))
                    }
                }
                reqParam.put("class",paramsType.get(i))
                req.add(reqParam)
                reqMapForLog.put(paramsType.get(i),reqParam)
            }else {
                String str
                if(params.get(i).class.isInstance(new ArrayList()) ){
                    List list = params.get(i) as List
                    str = list.get(0)
                }else {
                    str = params.get(i) as String
                }
                if (ObjectUtils.isNotEmpty(testContext.get(str))) {
                    req.add(testContext.get(str).toString())
                    reqMapForLog.put(str,testContext.get(str))
                }
            }
        }
        Object customData = testContext.get("customData")
        testContext.appendLog(new Record("interface",interfaceName))
        testContext.appendLog(new Record("method",methodName))
        testContext.appendLog(new Record("params",reqMapForLog))
        Object result = DubboService.invoke(address,interfaceName,methodName,version,group,timeOut,paramsType as String[],req as Object[],customData)
        testContext.setResponse(result)
        testContext.appendLog(new Record("response",result))
        return this
    }

    DubboRequest preInvoke(TestContext testContext){
        if(preInvoke!=null){
            Class clazz = Class.forName(preInvoke)
            Method method1 = clazz.getMethod("invoke", TestContext.class)
            method1.invoke(clazz.newInstance(),testContext)
            Method method2 = clazz.getMethod("afterInvoke", TestContext.class)
            method2.invoke(clazz.newInstance(),testContext)
        }
        return this
    }

    DubboRequest afterInvoke(TestContext testContext){

    }

    DubboRequest baseAssert(TestContext testContext){
        assert testContext.getResponse().code == "0"
        return this
    }

    DubboRequest specialAssert(TestContext testContext){

    }
}
