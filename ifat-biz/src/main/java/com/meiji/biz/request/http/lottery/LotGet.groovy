package com.meiji.biz.request.http.lottery


import com.miyuan.ifat.support.test.TestContext
import com.miyuan.ifat.support.util.HttpUtil
import com.miyuan.ifat.support.util.JsonUtil
import com.miyuan.ifat.support.util.ResourceUtil
import com.miyuan.ifat.support.vo.Record
import org.apache.commons.lang3.ObjectUtils

import java.lang.reflect.Method

abstract class LotGet {
    public String api
    public List params
    public String preInvoke

    LotGet invoke(TestContext testContext){
        String scrmUrl  = ResourceUtil.getBeanData("http").get("lottery")
        String url = scrmUrl + api
        Map heads = new HashMap()
        heads.put("authority", "etool.meiji8888.com")
        heads.put("sec-ch-ua",'[{"key":"sec-ch-ua","value":"\\" Not;A Brand\\";v=\\"99\\", \\"Google Chrome\\";v=\\"97\\", \\"Chromium\\";v=\\"97\\"","enabled":true}]')
        heads.put("sec-ch-ua-mobile", "?0")
        heads.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36")
        heads.put("request-origion", "SwaggerBootstrapUi")
        heads.put("accept", "*/*")
        heads.put("userid", "1")
        heads.put("x-requested-with", "XMLHttpRequest")
        heads.put("content-type","application/json")
        heads.put("companyId", "1")
        heads.put("sec-ch-ua-platform", '"Windows"')
        heads.put("origin", "https://etool.meiji8888.com")
        heads.put("sec-fetch-site", "same-origin")
        heads.put("sec-fetch-mode", "cors")
        heads.put("sec-fetch-dest", "empty")
        heads.put("referer", "https://etool.meiji8888.com/doc.html")
        heads.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8")
        /*if(TestEnv.getIsGray()=="true"){
            heads.put("isGrayRelease",true)
        }*/
        Map req = new HashMap()
        for(String str:params){
            if(ObjectUtils.isNotEmpty(testContext.get(str))){
                req.put(str, JsonUtil.objectParse(testContext.get(str)))
            }
        }
        testContext.appendLog(new Record("接口地址",url))
        testContext.appendLog(new Record("请求头",heads))
        testContext.appendLog(new Record("请求参数",req))
        String res = HttpUtil.get(url,heads, req)
        testContext.setResponse(res)
        testContext.appendLog(new Record("返回结果",res))
        return this
    }

    LotGet preInvoke(TestContext testContext){
        if(preInvoke!=null){
            Class clazz = Class.forName(preInvoke)
            Method method1 = clazz.getMethod("invoke", TestContext.class)
            method1.invoke(clazz.newInstance(),testContext)
            Method method2 = clazz.getMethod("afterInvoke", TestContext.class)
            method2.invoke(clazz.newInstance(),testContext)
        }
        return this
    }

    LotGet afterInvoke(TestContext testContext){

    }

    LotGet baseAssert(TestContext testContext){
        assert testContext.getResponse().code == "0"
        return this
    }

    LotGet specialAssert(TestContext testContext){
    }

}
