package com.springmix.wh.mixspringbootstarterautoconfiguration.signature;



import com.springmix.wh.mixspringbootstarterautoconfiguration.key.KeyPairUtils;

import java.security.Signature;
import java.util.Base64;

/**
 * @author wangfei
 * @version 1.0.0
 * @ClassName SignatureUtils.java
 * @Description 签名
 * @createTime 2022年02月28日 09:50:00
 */
public class SignatureUtils {


    public static final String RSA_ALGORITHM = "RSA";
    public static final String SHA256_WITH_RSA = "SHA256withRSA";

    /**
     *
     *签名模式：SHA256withRSA
     * @param privateKey 签名私钥
     * @param data 签名数据
     * @return
     */
    public static String signRSA(String privateKey,String data){
        try {
            Signature sig = Signature.getInstance(SHA256_WITH_RSA);
            sig.initSign(KeyPairUtils.getPrivateKey(privateKey));
            sig.update(data.getBytes("UTF-8"));
            byte[] sByte = sig.sign();
            return Base64.getEncoder().encodeToString(sByte);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     *签名模式：SHA256withRSA
     * @param publicKey 签名公钥
     * @param data 签名数据
     * @return
     */
    public static Boolean verifyRSA(String publicKey,String data,String signData){
        try {
            Signature sig = Signature.getInstance(SHA256_WITH_RSA);
            sig.initVerify(KeyPairUtils.getPublicKey(publicKey));
            sig.update(data.getBytes("UTF-8"));
            Boolean verify  = sig.verify(Base64.getDecoder().decode(signData));
            return verify;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }








}
