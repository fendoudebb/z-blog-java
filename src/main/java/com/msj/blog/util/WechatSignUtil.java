package com.msj.blog.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * zbj: create on 2018/06/12 16:09
 */
public class WechatSignUtil {

    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (String anArr : arr) {
            content.append(anArr);
        }
        MessageDigest md;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null && tmpStr.equals(signature.toUpperCase());
    }

    //byteToStr()方法作用：将字节数组转换为十六进制字符串
    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (byte aByteArray : byteArray) {
            strDigest.append(byteToHexStr(aByteArray));
        }
        return strDigest.toString();
    }

    //byteToHexStr()方法作用：将字节转换为十六进制字符串
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return new String(tempArr);
    }

}
