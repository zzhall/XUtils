package com.giszone.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Administrator
 */
@SuppressLint("GetInstance")
public class AESUtils {

    /**
     * 随机生成秘钥
     */
    public static String generateKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            //要生成多少位，只需要修改这里即可128, 192或256
            kg.init(128);
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            return HexUtils.toHexString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "0123456789ABCDEF";
    }


    // 加密
    public static String encrypt(String src, String key) {
        if (TextUtils.isEmpty(src)) {
            return "";
        } else if (TextUtils.isEmpty(key)) {
            return src;
        }
        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));
            //此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return src;
    }

    // 解密
    public static String decrypt(String src, String key) {
        if (TextUtils.isEmpty(src)) {
            return "";
        } else if (TextUtils.isEmpty(key)) {
            return src;
        }
        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            //先用base64解密
            byte[] encrypted1 = Base64.decode(src, Base64.DEFAULT);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return src;
    }

}
