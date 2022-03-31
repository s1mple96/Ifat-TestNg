package com.meiji.biz.service

import com.miyuan.ifat.support.util.HttpUtil
import com.miyuan.ifat.support.util.MD5Utils
import com.miyuan.ifat.support.util.TokenUtil
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.util.EntityUtils

class CookieService {
    static Map cacheCookie = new HashMap()
    /*
      avatar: string,
      isRegistered: boolean,
      nickname: string,
      openId: string,
      phone: string,
      token: string,
      userId: number,
      inviteUserId: number | null,
      jmUser: ChatAccount | null,
   */
    static String getMallCookie(String url,Long userId,Long dealerId) {
        String cookie = cacheCookie.get("mall:" + userId)
        if (cookie != null) {
            return cookie
        }
        StringBuilder stringBuilder = new StringBuilder()
        String token = TokenUtil.generateToken(userId)
        Map cookieParam = new HashMap()
        Map userInfo = new HashMap()
        userInfo.put("userId", userId)
        userInfo.put("token", token)
        userInfo.put("userType",2)
        Map shopInfo = new HashMap()
        shopInfo.put("dealerId",dealerId)
        shopInfo.put("authState",40)
        userInfo.put("shopInfo",shopInfo)
        cookieParam.put("secret", "+0ea81c0ea81557c9==")
        cookieParam.put("info", userInfo)
        CloseableHttpResponse response = HttpUtil.postV2(url + "/login/__test__", [:], cookieParam)
        Header[] headers = response.getHeaders("Set-Cookie")
        for (Header header : headers) {
            stringBuilder.append(header.elements.head().toString().split(";")[0])
            stringBuilder.append(";")
        }
        cookie = stringBuilder.toString()
        cacheCookie.put("mall:" + userId, cookie)
        return cookie
    }

    /*
        avatar: string,
        dealerId: number,
        isRegistered: boolean,
        nickname: string,
        openId: string,
        phone: string,
        shopId: number,
        shopIdList: number[],
        token: string,
        city?: string,
        country?: string,
        gender?: number,
        province?: string,
        imUserId?: string,
        imPassword?: string,
        jmUser: ChatAccount | null,
     */
    static String getShopCookie(String url,Long dealerId){
        String cookie = cacheCookie.get("dealerId:"+dealerId)
        if(cookie!=null){
            return cookie
        }
        StringBuilder stringBuilder = new StringBuilder()
        String token = TokenUtil.generateToken(dealerId)
        Map cookieParam = new HashMap()
        Map cookieInfo = new HashMap()
        cookieInfo.put("leaderId",dealerId)
        cookieInfo.put("token",token)
        cookieParam.put("secret","+0ea81c0ea81557c9==")
        cookieParam.put("info",cookieInfo)
        CloseableHttpResponse response = HttpUtil.postV2(url+"/login/__test__", [:], cookieParam)
        Header[] headers = response.getHeaders("Set-Cookie")
        for(Header header:headers){
            stringBuilder.append(header.elements.head().toString().split (";")[0])
            stringBuilder.append(";")
        }
        cookie =  stringBuilder.toString()
        cacheCookie.put("dealerId:"+dealerId,cookie)
        return cookie
    }

    static String getPlatformCookie(String url,String username,String password){
        String cookie = cacheCookie.get("platform:"+username)
        if(cookie!=null){
            return cookie
        }
        Map param = new HashMap()
        param.put("username",username)
        param.put("password",password)
        StringBuilder stringBuilder = new StringBuilder()
        CloseableHttpResponse response = HttpUtil.postV2(url+"/login/check", [:], param)
        HttpEntity entity = response.getEntity()
        String result = EntityUtils.toString(entity, "UTF-8")
        Header[] headers = response.getHeaders("Set-Cookie")
        for(Header header:headers){
            stringBuilder.append(header.elements.head().toString().split (";")[0])
            stringBuilder.append(";")
        }
        cookie =  stringBuilder.toString()
        cacheCookie.put("platform:"+username,cookie)
        return cookie
    }
}
