package com.springmix.wh.mixspringbootstarterautoconfiguration.utils;

import com.springmix.wh.mixspringbootstarterautoconfiguration.key.KeyPairUtils;

import javax.crypto.Cipher;
import java.util.Base64;

/**
 * package com.springmix.wh.mixspringbootstarterautoconfiguration.utils
 *
 * @author wangfei
 * @version 1.0.0
 * @ClassName DESEDEUtils.java
 * @Description UTIL
 * @createTime 2022年05月31日 14:38:00
 */
public class DESEDEUtils {

    private static final String DEFAULT_CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";


    public static String encryption(String key,String data) throws Exception{
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, KeyPairUtils.strKeyToSecretKey(key,KeyPairUtils.DESEDE_ALGORITHM));
            byte[] ct = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(ct);
        }catch(Exception e){
            throw new Exception(KeyPairUtils.DESEDE_ALGORITHM + "Encryption failed --"+e.getMessage());
        }
    }

    public static String decrypt(String key,String data)throws Exception{
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, KeyPairUtils.strKeyToSecretKey(key,KeyPairUtils.DESEDE_ALGORITHM));
            byte[] ct = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(ct,"UTF-8");
        }catch(Exception e){
            throw new Exception(KeyPairUtils.DESEDE_ALGORITHM + "Decryption failed --"+e.getMessage());
        }
    }

}
