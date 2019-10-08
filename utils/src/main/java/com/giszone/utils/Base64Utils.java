package com.giszone.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class Base64Utils {


    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64
     * @return
     */
    public static byte[] decode(String base64) {
        if (base64 == null) {
            return null;
        }
        return Base64.decode(base64.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
    }


    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new String(Base64.encode(bytes, Base64.DEFAULT), StandardCharsets.UTF_8);
    }


    /**
     * <p>
     * 将文件编码为BASE64字符串
     * </p>
     * <p>
     * 大文件慎用，可能会导致内存溢出
     * </p>
     *
     * @param filePath 文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64FromFile(String filePath) throws Exception {
        byte[] bytes = FileUtils.readBytesFromFile(filePath);
        return encode(bytes);
    }


    /**
     * <p>
     * BASE64字符串转回文件
     * </p>
     *
     * @param filePath 文件绝对路径
     * @param base64   编码字符串
     * @throws Exception
     */
    public static void decodeBase64ToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        FileUtils.writeBytesToFile(bytes, filePath);
    }
}  