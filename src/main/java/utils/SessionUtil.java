package utils;

import ninja.Cookie;
import ninja.session.Session;

/**
 * @className: SessionUtil
 * @description:
 * @author: Aim
 * @date: 2023/4/3
 **/
public class SessionUtil {

    private static Session session = getSession();
    private static Cookie cookie = null;

    public static void setSessionValue(String key, String value) {
        session.put(key, value);
    }

    public static String getSessionValue(String key) {
        return session.get(key);
    }

    public static void delSessionValue(String key) {
        session.remove(key);
    }

    public static Session getSession() {
        if (session == null) {
            getSession();
            return getSession();
        }
        return session;
    }

    /**
     * 判断某字符串是否为空或长度为0或由空白符构成
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }
}
