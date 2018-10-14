package me.web.files.test;

import java.io.PrintStream;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import me.web.files.util.Security;

public class T {
    public static PrintStream out = System.out;

    public static void main(String[] args) {
        try {
            String algorithm = "RSA";
            String text = "你好阿, 我的名字是 123##4.1";
            Map<String, String> keys = Security.createKeys(1024, "RSA");

            out.println(keys.toString());

            Map<String, Key> rsaKeys = Security.RSAKeys(keys.get("public"), keys.get("private"), "RSA");

            String encode = Security.publicEncrypt(text, (RSAPublicKey) rsaKeys.get("public"), "RSA");
            System.out.println(encode);

            String decode = Security.privateDecrypt(encode, (RSAPrivateKey) rsaKeys.get("private"), algorithm);
            out.println(decode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}