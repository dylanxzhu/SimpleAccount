package com.yuewawa.simpleaccount.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yuewawa on 2016-06-15.
 */
public class MD5Util {

    private static final String ALGORITHM = "MD5";

    public static String encryptByMD5(String data) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        digest.update(data.getBytes());
        byte[] encryptData = digest.digest();
        String result = "";
        String hex;
        for (int i=0; i<encryptData.length; i++) {
            hex = Integer.toHexString(0xFF & encryptData[i]);
            if (hex.length()==1) {
                result = result + "0" + hex;
            }
            else {
                result = result + hex;
            }
        }
        return result;
    }
}
