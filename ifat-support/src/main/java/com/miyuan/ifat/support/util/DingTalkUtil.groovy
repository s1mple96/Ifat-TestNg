package com.miyuan.ifat.support.util

import org.apache.commons.codec.binary.Base64
import org.testng.annotations.Test

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class DingTalkUtil {
    public static void send(String url, String secret, String title,String content){
        StringBuilder urlBuilder = new StringBuilder()
        Long timestamp = System.currentTimeMillis()
        String stringToSign = timestamp + "\n" + secret
        Mac mac = Mac.getInstance("HmacSHA256")
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"))
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"))
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8")
        urlBuilder.append(url)
        urlBuilder.append("&timestamp=")
        urlBuilder.append(timestamp)
        urlBuilder.append("&sign=")
        urlBuilder.append(sign)
        Map message = new HashMap()
        Map markdown = new HashMap()
        markdown.put("text",content)
        markdown.put("title",title)
        message.put("markdown",markdown)
        message.put("msgtype","markdown")
        HttpUtil.post(urlBuilder.toString(),[:],message)
    }

    @Test
    void test(){
        String url = "https://oapi.dingtalk.com/robot/send?access_token=b80b5fc83641dd472f50277b36733badbd497ea0171cb82d33bea273217c8936"
        String secret = "SECed079a98463eb0d4fc98862c9cdc29b4143a9c304ca1ca5e334d477f1245b98c"
        send(url,secret,"assert testContext.getResponse().code == \"B10018\"\n" +
                "       |           |             |    |\n" +
                "       |           |             |    false\n" +
                "       |           |             'B00000'")
    }
}
