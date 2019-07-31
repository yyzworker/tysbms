package com.tys.util.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author haoxu
 * @Date 2019/5/7 15:24
 **/
public class Md5Util {
    private static void checkNotNull(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("file not exists");
        }
    }

    public static String md5(File file) throws Exception{
        checkNotNull(file);
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[4096];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
            return bi.toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5FileInputStream(InputStream fis) throws Exception{
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[4096];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
            return bi.toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
