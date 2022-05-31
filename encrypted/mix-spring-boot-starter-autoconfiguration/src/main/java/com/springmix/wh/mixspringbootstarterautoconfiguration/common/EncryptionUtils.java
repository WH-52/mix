package com.springmix.wh.mixspringbootstarterautoconfiguration.common;


import com.alibaba.fastjson.JSONObject;
import com.springmix.wh.mixspringbootstarterautoconfiguration.key.KeyPairUtils;
import com.springmix.wh.mixspringbootstarterautoconfiguration.signature.SignatureUtils;
import com.springmix.wh.mixspringbootstarterautoconfiguration.utils.AESUtils;
import com.springmix.wh.mixspringbootstarterautoconfiguration.utils.DESEDEUtils;
import com.springmix.wh.mixspringbootstarterautoconfiguration.utils.RSAUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author wangfei
 * @version 1.0.0
 * @ClassName EncryptionUtils.java
 * @Description 数据加密验签
 * @createTime 2022年02月28日 09:50:00
 */
public class EncryptionUtils {

    public static final String UTF_8_CHARSET = "UTF-8";

    public  static final int  AES_KEY_128 = 128;
    public  static final int  AES_KEY_192 = 192;
    public  static final int  AES_KEY_256 = 256;

    private String privateKey;

    private List<String> publicKey;

    private String desedeKey;


    /**
     * Data encryption and signing
     * @param data 加密数据
     * @param localPrivateKey 本地私钥
     * @param distancePublicKey 接收方公钥
     * @return Map<String,String> -->
     */
    public String encryption(String data,String localPrivateKey,String distancePublicKey)throws Exception{
        try {
            //生成对称加密秘钥
            String AESKey = KeyPairUtils.createKeysAES(256);
            //对加密数据进行对称加密
            String enData = AESUtils.encryption(AESKey,data);
            //使用非对称（RSA）对 对称秘钥进行加密
            String RSAKey = RSAUtils.publicKeyEncryption(distancePublicKey,AESKey);
            //提取秘钥与密文摘要信息
            String digest  = getSha256Str(RSAKey+enData);
            //使用本地私钥对消息摘要进行签名
            String single = SignatureUtils.signRSA(localPrivateKey,digest);
            //封装密文、秘钥、签名信息
            JSONObject obj = new JSONObject();
            obj.put("rsa_aes_key",RSAKey);
            obj.put("data",enData);
            obj.put("single",single);

            return obj.toJSONString();
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }


    /**
     * 数据加密、加签
     * @param data 加密数据
     * @param localPrivateKey 本地私钥
     * @param distancePublicKey 接收方公钥
     * @return Map<String,String> -->
     */
    private  String encryption(String data,String localPrivateKey,String distancePublicKey,int AESKeySize)throws Exception{
        try {
            //生成对称加密秘钥
            String AESKey = KeyPairUtils.createKeysAES(AESKeySize);
            //对加密数据进行对称加密
            String enData = AESUtils.encryption(AESKey,data);
            //使用非对称（RSA）对 对称秘钥进行加密
            String RSAKey = RSAUtils.publicKeyEncryption(distancePublicKey,AESKey);
            //提取秘钥与密文摘要信息
            String digest  = getSha256Str(RSAKey+enData);
            //使用本地私钥对消息摘要进行签名
            String single = SignatureUtils.signRSA(localPrivateKey,digest);
            //封装密文、秘钥、签名信息
            JSONObject obj = new JSONObject();
            obj.put("rsa_aes_key",RSAKey);
            obj.put("data",enData);
            obj.put("single",single);

            return obj.toJSONString();
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 数据加密、加签
     * @param data 加密数据
     * @param localPrivateKey 本地私钥
     * @param distancePublicKey 接收方公钥
     * @return Map<String,String> -->
     */
    public  String encryptionBy128(String data,String localPrivateKey,String distancePublicKey)throws Exception{
        return  encryption(data,localPrivateKey,distancePublicKey,AES_KEY_128);
    }

    /**
     * 数据加密、加签
     * @param data 加密数据
     * @param localPrivateKey 本地私钥
     * @param distancePublicKey 接收方公钥
     * @return Map<String,String> -->
     */
    public  String encryptionBy192(String data,String localPrivateKey,String distancePublicKey)throws Exception{
        return  encryption(data,localPrivateKey,distancePublicKey,AES_KEY_192);
    }

    /**
     * 数据加密、加签
     * @param data 加密数据
     * @param localPrivateKey 本地私钥
     * @param distancePublicKey 接收方公钥
     * @return Map<String,String> -->
     */
    public  String encryptionBy256(String data,String localPrivateKey,String distancePublicKey)throws Exception{
        return  encryption(data,localPrivateKey,distancePublicKey,AES_KEY_256);
    }

    /**
     * 数据加密、加签256位AES秘钥
     * @param data 加密数据
     * @param index 接收方公钥下标
     * @return Map<String,String> -->
     */
    public  String encryptionBy256(String data,int index)throws Exception{
        if(index < 0 || index >= publicKey.size()){
            throw new IndexOutOfBoundsException("index:" + index +",Size:"+publicKey.size());
        }
        return  encryption(data,privateKey,publicKey.get(index),AES_KEY_256);
    }


    /**
     * 数据加密、加签192位AES秘钥
     * @param data 加密数据
     * @param index 接收方公钥下标
     * @return Map<String,String> -->
     */
    public  String encryptionBy192(String data,int index)throws Exception{
        if(index < 0 || index >= publicKey.size()){
            throw new IndexOutOfBoundsException("index:" + index +",Size:"+publicKey.size());
        }
        return  encryption(data,privateKey,publicKey.get(index),AES_KEY_192);
    }

    /**
     * 数据加密、加签128位AES秘钥
     * @param data 加密数据
     * @param index 接收方公钥下标
     * @return Map<String,String> -->
     */
    public  String encryptionBy128(String data,int index)throws Exception{
        if(index < 0 || index >= publicKey.size()){
            throw new IndexOutOfBoundsException("index:" + index +",Size:"+publicKey.size());
        }
        return  encryption(data,privateKey,publicKey.get(index),AES_KEY_128);
    }



    public  String desedeEncryption(String data)throws Exception{
        String key = KeyPairUtils.createKeyDESEDE(desedeKey);
        String msg = DESEDEUtils.encryption(key,data);
        return  msg;
    }

    public  String desedeEncryption(String data,String key)throws Exception{
        key = KeyPairUtils.createKeyDESEDE(key);
        String msg = DESEDEUtils.encryption(key,data);
        return  msg;
    }

    public  String desedeDecrypt(String data)throws Exception{
        String key = KeyPairUtils.createKeyDESEDE(desedeKey);
        String msg = DESEDEUtils.decrypt(key,data);
        return  msg;
    }

    public  String desedeDecrypt(String data,String key)throws Exception{
        key = KeyPairUtils.createKeyDESEDE(key);
        String msg = DESEDEUtils.decrypt(key,data);
        return  msg;
    }


    /**
     * 数据解密验签
     * @param data 加密数据
     * @param index 发送方公钥下标
     * @return Map<String,String> -->
     */
    public  String decode(String data,int index)throws Exception{
        if(index < 0 || index >= publicKey.size()){
            throw new IndexOutOfBoundsException("index:" + index +",Size:"+publicKey.size());
        }
        return  decode(data,privateKey,publicKey.get(index));
    }




    public  String decode(String jsonObje,String localPrivateKey,String distancePublicKey)throws Exception{
        JSONObject obj = JSONObject.parseObject(jsonObje);
        String RSAKey = obj.getString("rsa_aes_key");
        String enJSONData = obj.getString("data");
        String single = obj.getString("single");
        //获取秘钥与密文摘要
        String digest  = getSha256Str(RSAKey+enJSONData);
        try{
            //验签
            if(SignatureUtils.verifyRSA(distancePublicKey,digest,single)){
                //解密对称秘钥
                String AESKey = RSAUtils.privateKeyDecrypt(localPrivateKey,RSAKey);
                //密文信息
                String jsonData = AESUtils.decrypt(AESKey,enJSONData);
                return jsonData;
            } else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Decryption and signature verification failed--"+e.getMessage());
        }
    }



    /**
     　　*SHA256加密
     　　* @param str 加密报文
     　　* @return
     　　*/
    public String getSha256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(UTF_8_CHARSET));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     　　* 将byte转为16进制
     　　* @param bytes
     　　* @return
     　　*/
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
            for (int i=0; i<bytes.length; i++){
                temp = Integer.toHexString(bytes[i] & 0xFF);
                if (temp.length()==1){

                    stringBuffer.append("0");
                }
                stringBuffer.append(temp);
            }
            return stringBuffer.toString();
        }






    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }


    public void setPublicKey(List<String> publicKey) {
        this.publicKey = publicKey;
    }


    public void setDesedeKey(String desedeKey) {
        this.desedeKey = desedeKey;
    }
}



