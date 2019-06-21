package com.wengzhoujun.gstation.utils;

import java.security.MessageDigest;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
public class PasswordUtil {
    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public PasswordUtil() {
    }

    public static String encode(String salt, String rawPass) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return byteArrayToHexString(md.digest(mergePasswordAndSalt(salt, rawPass).getBytes("utf-8")));
    }

    public static String salt(String phone) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA");
        String result = byteArrayToHexString(md.digest(mergePasswordAndSalt((String)null, phone).getBytes("utf-8")));
        return result.substring(0, 10);
    }

    public static boolean isPasswordValid(String encPass, String rawPass, String salt) throws Exception {
        String pass1 = "" + encPass;
        String pass2 = encode(salt, rawPass);
        return pass1.equals(pass2);
    }

    private static String mergePasswordAndSalt(String salt, String password) {
        if (password == null) {
            password = "";
        }

        return salt != null && !"".equals(salt) ? password + "{" + salt.toString() + "}" : password;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = 256 + b;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

}
