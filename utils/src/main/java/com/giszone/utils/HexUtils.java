package com.giszone.utils;

public class HexUtils {

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 字符数组转16位字符串
     *
     * @param bytes
     * @return
     */
    public static String toHexString(byte[] bytes) {
        char[] r = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            r[i * 2] = HEX[bytes[i] >>> 4 & 0x0f];
            r[i * 2 + 1] = HEX[bytes[i] & 0x0f];
        }
        return new String(r);
    }

}
