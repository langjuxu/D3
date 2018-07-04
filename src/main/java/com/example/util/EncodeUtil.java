package com.example.util;

import org.apache.shiro.codec.Hex;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加密工具类
 *
 * @author qian
 * @date 2018/7/3
 */
public class EncodeUtil {

    public static String encodeMD5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] encodeBytes = md.digest(src.getBytes());

            return Hex.encodeToString(encodeBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeSHA(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(src.getBytes());
            return Hex.encodeToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    // RSA-非对称加密
    private static RSAPublicKey rsaPublicKey;
    private static RSAPrivateKey rsaPrivateKey;

    public static String encodeRSA(String src) {
        try {
            //初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            //私钥加密 公钥解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec
                    = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(src.getBytes());

            //私钥解密 公钥加密
//          X509EncodedKeySpec x509EncodedKeySpec =
//                  new X509EncodedKeySpec(rsaPublicKey.getEncoded());
//          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//          PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
//          Cipher cipher = Cipher.getInstance("RSA");
//          cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//          byte[] resultBytes = cipher.doFinal(src.getBytes());

            return Hex.encodeToString(resultBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String decodeRSA(String src) {
        try {
            //私钥加密 公钥解密
            X509EncodedKeySpec x509EncodedKeySpec =
                    new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(Hex.decode(src.toCharArray()));

            //私钥解密 公钥加密
//          PKCS8EncodedKeySpec pkcs8EncodedKeySpec
//              = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
//          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//          PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
//          Cipher cipher = Cipher.getInstance("RSA");
//          cipher.init(Cipher.DECRYPT_MODE, privateKey);
//          byte[] resultBytes = cipher.doFinal(Hex.decodeHex(src.toCharArray()));

            return new String(resultBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
