package group5.swp391.onlinelearning.utils;

import java.security.MessageDigest;

import org.apache.tomcat.util.codec.binary.Base64;

public class SHA1 {

    public static String toSHA1(String strToEncode) {
        String salt = "jdba@!*(uwqweksbfujHDHWEISDNEWB@#$KSDFJBDikdfowse.?sadfusfkjewmsdSFIWEKsd}jnw{erjisdfnsdfkjnsdf";
        String result = null;
        strToEncode = strToEncode + salt;

        try {
            byte[] bytes = strToEncode.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            result = Base64.encodeBase64String(md.digest(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
