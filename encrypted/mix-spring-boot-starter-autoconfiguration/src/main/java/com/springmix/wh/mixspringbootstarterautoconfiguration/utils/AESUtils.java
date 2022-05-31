package com.springmix.wh.mixspringbootstarterautoconfiguration.utils;



import com.springmix.wh.mixspringbootstarterautoconfiguration.key.KeyPairUtils;

import javax.crypto.Cipher;
import java.util.Base64;

/**
 * @author wangfei
 * @version 1.0.0
 * @ClassName AESUtils.java
 * @Description AES—UTIL
 * @createTime 2022年02月28日 09:50:00
 */
public class AESUtils {

    public static final String UTF_8_CHARSET = "UTF-8";


    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";


    public static String encryption(String key,String data) throws Exception{
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, KeyPairUtils.strKeyToSecretKey(key,KeyPairUtils.AES_ALGORITHM));
            byte[] ct = cipher.doFinal(data.getBytes(UTF_8_CHARSET));
            return Base64.getEncoder().encodeToString(ct);
        }catch(Exception e){
            throw new Exception(KeyPairUtils.AES_ALGORITHM + "Encryption failed --"+e.getMessage());
        }
    }

    public static String decrypt(String key,String data)throws Exception{
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, KeyPairUtils.strKeyToSecretKey(key,KeyPairUtils.AES_ALGORITHM));
            byte[] ct = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(ct,UTF_8_CHARSET);
        }catch(Exception e){
            throw new Exception(KeyPairUtils.AES_ALGORITHM + "Decryption failed --"+e.getMessage());
        }
    }




}
