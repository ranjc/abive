package com.abive.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5生成器
 */
public class MD5 {

    public static String parse(String msg) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] b1 = msg.getBytes();
        byte[] b2 = null;
        if (md != null) {
            b2 = md.digest(b1);
        }

        /* 把字节数组编码成字符串 */
        /*
         * BASE64Encoder encoder = new BASE64Encoder(); String password =
         * encoder.encode(b2);
         */

        /* UUID64方式 */
        UUID64 code = UUID64.parse(b2);
        return code.getValue();
    }

}
