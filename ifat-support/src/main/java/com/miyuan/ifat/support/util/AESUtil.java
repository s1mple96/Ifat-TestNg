package com.miyuan.ifat.support.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESUtil {
    private static String ivParameter = "1020304050607080";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String KEY_AES = "AES";
    private static final BASE64Encoder encoder = new BASE64Encoder();


    /**
     * 解密
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            sKey = sKey.substring(0, 16);
            byte[] raw = sKey.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, StandardCharsets.UTF_8);
            return originalString;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * 加密
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        sKey = sKey.substring(0, 16);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(encrypted).replaceAll("[\\s*\t\n\r]", "");
    }


    /**
     * 加密token
     * @param nonce
     * @param token
     * @return
     */
    public static String encryptToken(String nonce, String timestamp,String token) throws Exception {
        // 获取密钥
        String aeskey = MD5Utils.MD5Encode(timestamp + nonce, "utf-8");
        String encrypt = AESUtil.encrypt(token, aeskey.substring(0,16));
        return encrypt;
    }
}
