package tech.maplefall.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SafeUtils {
    public final static String md5(String str){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // 优化异常处理
        }
        messageDigest.update(str.getBytes());
        return Base64.getEncoder().encodeToString(messageDigest.digest()); // 修改编码方式
    }

    public final static String sha1(String str){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(str.getBytes());
        return Base64.getEncoder().encodeToString(messageDigest.digest());
    }

    // encode方法保持不变
    public final static String encode(String str){
        return md5(sha1(md5(str)));
    }
}
