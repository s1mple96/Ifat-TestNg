package com.meiji.biz.util
import com.miyuan.ifat.support.util.MD5Utils
import sun.misc.BASE64Encoder

class KJSign {
    static String userCode = "MEIJI_API";
    static String privateKey = "TR4lgJ1K"
    static String sign(String xmlBase64str){
        BASE64Encoder encoder = new BASE64Encoder()
        String msgDigstParams = encoder.encode(xmlBase64str.getBytes())
        StringBuffer md5str = new StringBuffer(msgDigstParams)
        md5str.append(userCode)
        md5str.append(privateKey)
        String md5 = MD5Utils.MD5Encode(md5str.toString(), "UTF-8")
        return md5
    }

}
