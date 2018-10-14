package me.web.files.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class Security {
    public static Map<String, String> createKeys(int keySize, String algorithm) throws NoSuchAlgorithmException {

        KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
        generator.initialize(keySize);
        KeyPair keyPair = generator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        Map<String, String> keys = new HashMap<String, String>();
        keys.put("public", Base64.encodeBase64URLSafeString(publicKey.getEncoded()));
        keys.put("private", Base64.encodeBase64URLSafeString(privateKey.getEncoded()));

        return keys;
    }

    public static Map<String, Key> RSAKeys(String publicKeyStr, String privateKeyStr, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        RSAPublicKey publicKey = (RSAPublicKey) factory.generatePublic(x509KeySpec);

        factory = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec pacs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        RSAPrivateKey privateKey = (RSAPrivateKey) factory.generatePrivate(pacs8KeySpec);

        Map<String, Key> keys = new HashMap<String, Key>();
        keys.put("public", publicKey);
        keys.put("private", privateKey);
        return keys;
    }

    public static String publicEncrypt(String data, RSAPublicKey key, String algorithm)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return Base64.encodeBase64URLSafeString(
                rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes("UTF-8"), key.getModulus().bitLength()));
    }

    public static String publicDecrypt(String data, RSAPublicKey key, String algorithm)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(
                rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), key.getModulus().bitLength()),
                "UTF-8");

    }

    public static String privateEncrypt(String data, RSAPrivateKey key, String algorithm)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64URLSafeString(
                rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes("UTF-8"), key.getModulus().bitLength()));

    }

    public static String privateDecrypt(String data, RSAPrivateKey key, String algorithm)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(
                rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), key.getModulus().bitLength()),
                "UTF-8");
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
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
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

}