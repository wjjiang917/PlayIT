package com.pindiboy.playit.util;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Jiangwenjin on 2017/3/20.
 * <p>
 * AES-128-CBC   Base64 encode
 */

public class EncryptUtil {
    private static String KEY = "Pindi_Boy@123456"; // length: 16
    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_ALGORITHM_PARAM = KEY;

    public static String encrypt(String text) {
        return new String(base64Encode(desTemplate(text.getBytes(), KEY.getBytes(), AES_ALGORITHM, AES_TRANSFORMATION, true)));
    }

    public static String decrypt(String text) {
        return new String(desTemplate(base64Decode(text.getBytes()), KEY.getBytes(), AES_ALGORITHM, AES_TRANSFORMATION, false));
    }

    /**
     * Base64编码
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    private static byte[] base64Encode(byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64解码
     *
     * @param input 要解码的字符串
     * @return Base64解码后的字符串
     */
    private static byte[] base64Decode(byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * DES加密模板
     *
     * @param data           数据
     * @param key            秘钥
     * @param algorithm      加密算法
     * @param transformation 转变
     * @param isEncrypt      {@code true}: 加密 {@code false}: 解密
     * @return 密文或者明文，适用于DES，3DES，AES
     */
    public static byte[] desTemplate(byte[] data, byte[] key, String algorithm, String transformation, boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            SecureRandom random = new SecureRandom();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(AES_ALGORITHM_PARAM.getBytes());
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, ivParameterSpec, random);
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
