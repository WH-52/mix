package com.springmix.wh.mixspringbootstarterautoconfiguration.key;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author wangfei
 * @version 1.0.0
 * @ClassName KeyPairUtils.java
 * @Description 秘钥工具类
 * @createTime 2022年02月28日 09:50:00
 */
public class KeyPairUtils {

    public static final String RSA_ALGORITHM = "RSA"; // ALGORITHM ['ælgərɪð(ə)m] 算法的意思
    public static final String AES_ALGORITHM = "AES"; // ALGORITHM ['ælgərɪð(ə)m] 算法的意思

    /**
     * String[0] = 公钥
     * String[1] = 私钥
     * 创建RSA公钥、私钥
     * @param keySize
     * @return
     */
    public static String[] createKeysRSA(int keySize) throws Exception{
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("生成非对称公私钥失败--"+e.getMessage());
        }
        // 初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        PublicKey publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        //数组封装秘钥
        return new String[]{publicKeyStr,privateKeyStr};
    }


    /**
     * 获取 对称秘钥SecretKey （经过base64编码）
     * @param keysize  对称秘钥长度
     * @return
     */
    public static String createKeysAES(int keysize) throws Exception{
        keysize = keysize == 0 ? 256 : keysize;
        try{
            KeyGenerator keyGenerator =  KeyGenerator.getInstance(AES_ALGORITHM);
            keyGenerator.init(keysize);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("生成对称秘钥SecretKey失败--"+e.getMessage());
        }
    }


    /**
     * 字符串转秘钥（AES
     * @param key
     * @return
     */
    public static SecretKey strKeyToSecretKey(String key)throws Exception{
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key),AES_ALGORITHM);
            return secretKeySpec;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("字符串转秘钥AES失败--"+e.getMessage());

        }

    }



    /**
     * 得到私钥
     * @param privateKey  密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 得到公钥
     * @param publicKey  密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }



}
