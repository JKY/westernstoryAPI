package com.westernstory.api.util;

import com.westernstory.api.config.Config;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WsUtil {

    private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    /**
     * 判断字符串是否为空
     * @param obj obj
     * @return boolean
     */
    public static boolean isEmpty(Object obj) {
        return obj == null || obj.toString().isEmpty();
    }
    /**
     * 是否数字
     * @param str str
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * 是否email
     * @param str str
     * @return boolean
     */
    public static boolean isEmail(String str) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 获取IP地址
     * @param request request
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        // ipAddress = request.getRemoteAddr();
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取设备agent
     * @param request request
     * @return str
     */
//    public static String getDeviceAgent(HttpServletRequest request) {
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//        String osName = userAgent.getOperatingSystem().getName();
//        String osType = userAgent.getOperatingSystem().getDeviceType().getName();
//        String osmf = userAgent.getOperatingSystem().getManufacturer().getName();
//
//        return osName + "|" + osType + "|" + osmf;
//    }

    /**
     * 是否SQL注入
     * @param str str
     * @return boolean
     */
    public static boolean isInjectSql(String str) {
        if (sqlPattern.matcher(str).find()) {
            return true;
        }
        return false;
    }

    /**
     * 转成专用格式，用于mybatis排序
     * @param orderBy orderBy
     * @return list
     */
    @SuppressWarnings("unchecked")
    public static List<String> orderByList(String orderBy) {
        List<String> list = null;
        if (orderBy != null) {
            try {
                list = new ArrayList<String>();
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(orderBy);
                JSONArray array = (JSONArray) obj;

                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObj = (JSONObject) array.get(i);
                    Iterator<String> iter = jsonObj.keySet().iterator();
                    String key = iter.next();
                    String value = (String) jsonObj.get(key);
                    if (!isInjectSql(key) && !isInjectSql(value)) {
                        list.add(key + " " + value);
                    }
                }
                if (list.size() == 0) {
                    list = null;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String simpleToken(String userId, String password) {
        return Md5.toMD5(userId + "#" + password + "#" + Config.WEB_KEY);
    }
    /**
     * 日期转字符串
     * @param date date
     * @param format format
     * @return str
     * @throws org.json.simple.parser.ParseException
     */
    public static String formatDate(Date date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format); // "yyyy-MM-dd HH:mm:ss"
        return sdf.format(date);
    }

    /**
     * 字符串转日期
     * @param strDate strDate
     * @param format format
     * @return Date
     * @throws java.text.ParseException
     */
    public static Date parseDate(String strDate, String format) throws java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(strDate);
    }

    /**
     * 判断时间 是否在自然天之内（00:00:00 - 11:59:59）
     * @param time time
     * @return boolean
     * @throws java.text.ParseException
     */
    public static Boolean isInNatureDay(Long time) {
        Boolean is = false;
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, date, 0, 0, 0);
        Long from = calendar.getTime().getTime();
        Long to = from + 3600 * 24 * 1000;

        if (time > from && time < to) {
            is = true;
        }
        return is;
    }

    /**
     * 获取浏览器 系统、类型、版本
     * @param request request
     * @return map
     */
//    public static String getUserAgent(HttpServletRequest request){
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//        String operationSystem = userAgent.getOperatingSystem().getName();
//        String browserName = userAgent.getBrowser().getName();
//        String browserVersion = userAgent.getBrowserVersion().getMajorVersion();
//
//        return operationSystem + "|" + browserName + "|" + browserVersion;
//    }

    /**
     * 获取ServiceException用户能看懂的信息
     * @param e exception
     * @return string
     */
    public static String getServiceExceptionMessage(Exception e) {
        if(e instanceof ServiceException) {
            return e.getMessage();
        } else {
            return "服务器异常";
        }
    }
}