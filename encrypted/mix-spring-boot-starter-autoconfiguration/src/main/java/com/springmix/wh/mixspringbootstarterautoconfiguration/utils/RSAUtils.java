package com.springmix.wh.mixspringbootstarterautoconfiguration.utils;



import com.springmix.wh.mixspringbootstarterautoconfiguration.key.KeyPairUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


/**
 * @author wangfei
 * @version 1.0.0
 * @ClassName RSAUtils.java
 * @Description RSA—UTIL
 * @createTime 2022年02月28日 09:50:00
 */
public class RSAUtils {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";


    /**
     * 公钥加密
     * @param publicKey 公钥
     * @param data 数据
     * @return
     */
    public static String publicKeyEncryption(String publicKey,String data)throws Exception{
        try {
            RSAPublicKey rsaPublicKey = KeyPairUtils.getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rsaPublicKey.getModulus().bitLength()));
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("公钥加密失败--"+ e.getMessage());
        }
    }


    /**
     * 私钥解密
     * @param privateKey 私钥
     * @param data 数据
     * @return
     */
    public static String privateKeyDecrypt(String privateKey,String data)throws Exception{
        try {
            RSAPrivateKey rsaPrivateKey = KeyPairUtils.getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data), rsaPrivateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("私钥解密失败--"+ e.getMessage());
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize)  {
        int maxBlock = 0;  //最大块
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        return resultDatas;
    }


    public static void main(String[] args) {
        try{
            String[] key = KeyPairUtils.createKeysRSA(2048);

            String s =  "二 初始化向量\n" +
                    "当加密第一个明文分组时，由于不存在“前一个密文分组”，因此需要事先准备一个长度为一个分组的比特序列来代替“前一个密文分组”，这个比特序列称为初始化向量（Initialization Vector），通常缩写为IV，一般来说，每次加密时都会随机产生一个不同的比特序列来作为初始化向量。";
            String s1 = publicKeyEncryption(key[0],s);
            System.out.println(s1);
            String s2 = privateKeyDecrypt(key[1],s1);
            System.out.println(s2);
        }catch (Exception e) {

        }

    }




}
