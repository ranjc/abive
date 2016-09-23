package com.abive.util;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

/**
 * 64进制表示的UUID
 */
public class UUID64 {

    private static volatile SecureRandom numberGenerator = null;

    /**
     * 64进制字符表，该表的改变会影响方法char64ToDigit方法的实现
     */
    private static char                  CHAR_64[]       = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '@', '-' };

    private byte[]                       data;

    private String value;

    private UUID64(byte[] data) {
        StringBuffer sb = new StringBuffer();

        this.data = data;

        for (int l = 0; l < 4; l++) // 16 byte / 4 = 4 int
        {
            int i = (data[l * 4] & 0xFF) << 24 | (data[l * 4 + 1] & 0xFF) << 16 | (data[l * 4 + 2] & 0xFF) << 8
                    | data[l * 4 + 3] & 0xFF;
            sb.append(intToChars64(i));
        }

        value = sb.toString();
    }

    private UUID64(String s) {
        this.value = s;
        byte[] bytes = new byte[16];

        for (int i = 0; i < 4; i++) // 4 int
        {
            int v = chars64ToInt(s.substring(6 * i, 6 * i + 6).toCharArray());

            /** 用一个int填充4byte */
            bytes[i * 4] = (byte) (v >>> 24 & 0xFF);
            bytes[i * 4 + 1] = (byte) (v >>> 16 & 0xFF);
            bytes[i * 4 + 2] = (byte) (v >>> 8 & 0xFF);
            bytes[i * 4 + 3] = (byte) (v & 0xFF);

        }

        this.data = bytes;
    }

    /**
     * 将UUID64对应的字符串反向解析为UUID64对象
     */
    public static UUID64 parse(String s) {
        return new UUID64(s);
    }

    /**
     * 将指定的byte array(16个字符)反向为UUID64对象
     */
    public static UUID64 parse(byte[] bytes) {
        return new UUID64(bytes);
    }

    /**
     * 生成一个随机的UUID64对象
     */
    public static UUID64 randomUUID() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        UUID64 result = new UUID64(randomBytes);
        result.data = randomBytes;

        return result;
    }

    /**
     * 取得该UUID64所表示的数组
     */
    public byte[] encode() {
        return data;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }

    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + Arrays.hashCode(data);
        result = PRIME * result + ((value == null) ? 0 : value.hashCode());

        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final UUID64 other = (UUID64) obj;
        if (!Arrays.equals(data, other.data)) return false;
        if (value == null) {
            if (other.value != null) return false;
        } else if (!value.equals(other.value)) return false;
        return true;
    }

    /**
     * 将一个int(4字节)转换为一个64进制字符表示
     */
    private static char[] intToChars64(int i) {
        char[] chars = new char[6];

        chars[0] = CHAR_64[i >>> 30 & 0x3F];
        chars[1] = CHAR_64[i >>> 24 & 0x3F];
        chars[2] = CHAR_64[i >>> 18 & 0x3F];
        chars[3] = CHAR_64[i >>> 12 & 0x3F];
        chars[4] = CHAR_64[i >>> 6 & 0x3F];
        chars[5] = CHAR_64[i & 0x3F];

        return chars;
    }

    /**
     * 将一个64进制字符转为1个int
     * @param chars
     * @return
     */
    private static int chars64ToInt(char[] chars) {
        int i = 0;
        byte[] bytes = new byte[6];

        for (int l = 0; l < 6; l++) {
            bytes[l] = char64ToDigit(chars[l]);
        }
        // /** bytes[0]只需要读两位，所以位与0x3，其他bytes都位与0x3F */
        i = ((bytes[0] & 0x3) << 30) + (bytes[1] << 24) + (bytes[2] << 18) + (bytes[3] << 12) + (bytes[4] << 6)
                + (bytes[5]);
        return i;
    }

    /**
     * 取得64进制字符对应的序号
     */
    private static byte char64ToDigit(char ch) {
        byte b = 0;
        int i = (int) ch;
        if (i >= 48 && i <= 57) // 字符 '0'-'9'之间对应0-9
        {
            b = (byte) (i - 48);
        } else if (i >= 65 && i <= 90) // 字符'A'-'Z'对应10-35
        {
            b = (byte) (i - 55);
        } else if (i >= 97 && i <= 122) // 字符'a'-'z'对应36-61
        {
            b = (byte) (i - 61);
        } else if (i == 64) // 字符'@'对应62
        {
            b = 62;
        } else if (i == 45) // 字符'-'对应63
        {
            b = 63;
        }

        return b;
    }

    public static synchronized String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("\\-", "");
    }

    public static void main(String[] args) {

        System.out.println(getUUID());
    }

}
