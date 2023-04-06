package core;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import ninja.Context;
import ninja.Cookie;
import ninja.utils.NinjaProperties;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DateUtils;
import utils.JacksonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @className: BaseController
 * @description: 控制器基类
 * @author: Aim
 * @date: 2023/4/3
 **/
public class BaseController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String SEPARATOR = "####";
    private static final int TWO_WEEKS_SECONDS = 1209600;
    private static final int TWO_WEEKS_MILLISECONDS = 1209600000;

    @Inject
    private NinjaProperties ninjaProperties;

    public String getCookie(String key, Context context) {
        Preconditions.checkNotNull(key, "Key is required for getCookie");
        Preconditions.checkNotNull(context, "Valid context is required for getCookie");

        Cookie cookie = context.getCookie(getCookieName());
        if (cookie != null && isNotBlank(cookie.getValue())) {
            String[] cookieValue = cookie.getValue().split(SEPARATOR);
            if (cookieValue.length == 3) {
                String sign = cookieValue[0];
                String username = cookieValue[1];
                long timestamp = Long.parseLong(cookieValue[2]);
                if (getSignature(username, timestamp).equals(sign) && ((timestamp + TWO_WEEKS_MILLISECONDS) > DateUtils.dateToUnixTimestamp())) {
                    return username;
                }
            }
        }

        return null;
    }

    public void setCookie(String key, Context context) {
        Preconditions.checkNotNull(key, "Key is required for setCookie");
        Preconditions.checkNotNull(context, "Context is required for setCookie");

        long timestamp = DateUtils.dateToUnixTimestamp();
        StringBuilder buffer = new StringBuilder(getSignature(key, timestamp));
        buffer.append(SEPARATOR).append(key).append(SEPARATOR).append(timestamp);

        Cookie cookie = Cookie.builder(getCookieName(), buffer.toString()).setSecure(true).setHttpOnly(true)
                .setMaxAge(ninjaProperties.getIntegerWithDefault(NinjaKey.AUTH_COOKIE_EXPIRES, TWO_WEEKS_SECONDS)).build();
        context.addCookie(cookie);
    }

    private String getSignature(String username, long timestamp) {
        Preconditions.checkNotNull(username, "Username is required for getSignature");
        Preconditions.checkNotNull(timestamp, "Timestamp is required for getSignature");
        return DigestUtils.sha512Hex(username + timestamp + ninjaProperties.get(NinjaKey.APPLICATION_SECRET));
    }

    private String getCookieName() {
        String prefix = ninjaProperties.get("application.cookie.prefix") + "-";
        return prefix + ninjaProperties.getWithDefault(NinjaKey.AUTH_COOKIE_NAME, NinjaKey.DEFAULT_AUTH_COOKIE_NAME);
    }

    /**
     * 获取请求参数转换成指定类
     *
     * @param context 请求
     * @param clazz   转换类
     * @param <T>
     * @return
     */
    protected <T> T getEntity(Context context, Class<T> clazz) {
        Map<String, Object> paramsMap = getParamsMap(context);
        String jsonParam = JacksonUtil.toJson(paramsMap);
        return JacksonUtil.readValue(jsonParam, clazz);
    }

    /**
     * 获取请求的所有参数
     */
    protected Map<String, Object> getParamsMap(Context context) {
        // 返回值Map
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            // 参数Map
            Map<String, ?> properties = context.getParameters();
            Set<String> keys = properties.keySet();
            String value = "";
            for (String strKey : keys) {
                Object strObj = properties.get(strKey);
                if (strObj instanceof String[]) {
                    String[] values = (String[]) strObj;
                    for (int i = 0; i < values.length; i++) { // 用于请求参数中有多个相同名称
                        value = values[i] + ",";
                    }
                    value = value.substring(0, value.length() - 1);
                } else {
                    value = strObj.toString();// 用于请求参数中请求参数名唯一
                }
                if (null != value && !("".equals(value))) {
                    returnMap.put(strKey.toString(), value.trim());
                }
            }
        } catch (Exception e) {
            log.error("getParamsMap错误信息：{}", e);
        }
        return returnMap;
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
