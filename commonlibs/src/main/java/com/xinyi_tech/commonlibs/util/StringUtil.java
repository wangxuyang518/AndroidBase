package com.xinyi_tech.commonlibs.util;

import android.content.Context;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtil {
    @SuppressWarnings("unused")
    private static final String TAG = "StringUtils";

    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // private final static SimpleDateFormat dateFormater = new

    // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // private final static SimpleDateFormat dateFormater2 = new

    // SimpleDateFormat("yyyy-MM-dd");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return dateFormat;
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return dateFormat;
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date toDate2(String sdate) {
        try {
            return dateFormater2.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天

        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    public static String getPullRefreshTime(Context context) {
        return DateUtils.formatDateTime(context, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @return boolean
     */
    public static boolean isToday(Date date) {
        Calendar d = Calendar.getInstance();
        d.setTime(date);
        Calendar today = Calendar.getInstance();
        if (d.get(Calendar.YEAR)==today.get(Calendar.YEAR)&&d.get(Calendar.DAY_OF_YEAR)==today.get(Calendar.DAY_OF_YEAR)){
            return true;
        } else
            return false;

    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");

    public static String decode1(String s) {
        Matcher m = reUnicode.matcher(s);
        StringBuffer sb = new StringBuffer(s.length());
        while (m.find()) {
            m.appendReplacement(sb,
                    Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String decode2(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '\\' && chars[i + 1] == 'u') {
                char cc = 0;
                for (int j = 0; j < 4; j++) {
                    char ch = Character.toLowerCase(chars[i + 2 + j]);
                    if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
                        cc |= (Character.digit(ch, 16) << (3 - j) * 4);
                    } else {
                        cc = 0;
                        break;
                    }
                }
                if (cc > 0) {
                    i += 5;
                    sb.append(cc);
                    continue;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String decode3(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx

                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    public static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象

            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要

            mdInst.update(btInput);
            // 获得密文

            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式

            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化剩余时间
     *
     * @param time 秒总数
     * @return
     */
    public static String timeCutCount(long time) {
        int MM = (int) time / 60; // 合计分钟

        int hh = (int) time / 3600; // 合计小时

        int dd = (int) hh / 24; // 合计天

        int yu_hh = (int) hh % 24; // 余小时

        int yu_MM = (int) MM % 60; // 余分钟

        int yu_ss = (int) time % 60; // 余秒

        return dd + "天" + yu_hh + "时" + yu_MM + "分" + yu_ss + "秒";
    }

    /**
     * 转换时间
     *
     * @param timestampString
     * @return
     */
    public static String timeStamp2Date(String timestampString) {
        Long timestamp = toLong(timestampString) * 1000;
        Date date = new Date(timestamp);
        String dateString = dateFormater.get().format(date);
        return dateString;
    }

    public static String timeStamp2Day(String timestampString) {
        Long timestamp = toLong(timestampString) * 1000;
        Date date = new Date(timestamp);
        String dateString = dateFormater2.get().format(date);
        return dateString;
    }

    public static String date2String(Date date) {
        try {
            return dateFormater2.get().format(date);
        } catch (Exception e) {
            return "00:00:00";
        }

    }

    public static String date2String(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);

    }

    public static String date2StringDDHHmm(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 写入文件到sd卡
     *
     * @param content
     */
    public static void writeFileToSD(String name, String content) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("baocai", "SD card is not avaiable/writeable right now.");
            return;
        }
        try {
            String pathName = "/sdcard/baocai/data";
            String fileName = name;
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if (!path.exists()) {
                Log.d("baocai", "Create the path:" + pathName);
                path.mkdir();
            }
            if (!file.exists()) {
                Log.d("baocai", "Create the file:" + fileName);
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            String s = content;
            byte[] buf = s.getBytes();
            stream.write(buf);
            stream.close();

        } catch (Exception e) {
            Log.e("baocai", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }

    /**
     * 文件转文本
     *
     * @param path
     * @param name
     * @return
     */
    public static String readFileToString(String path, String name) {
        File file = new File(path, name);
        StringBuffer sb = new StringBuffer();
        byte[] buffer = new byte[1024];
        try {
            InputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = "";

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String toPrice(double num) {
        Double d = new Double(num);
        if (num == d.intValue()) {
            return d.intValue() + "";
        }
        return num + "";
    }

    public static String toPriceDouble(double num) {
        Double d = new Double(num);
        if (num == d.intValue()) {
            return d.intValue() + "";
        }
        return num + "";
    }

    /**
     * 保留两位有效数字
     *
     * @param num
     * @return
     */
    public static String toPrice2(double num) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        return df.format(num);
    }


    /**
     * 字符串md5
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 获取订单号最后一位数
     *
     * @param content
     * @return
     */
    public static String getLastNum(String content) {
        String s = "\\d+";
        Pattern pattern = Pattern.compile(s);
        Matcher ma = pattern.matcher(content);
        String num = "";
        while (ma.find()) {
            num = ma.group();
        }
        return num;
    }

    /**
     * 是否电话号码
     *
     * @param phoneNo
     * @return
     */
    public static boolean isPhoneNo(String phoneNo) {
        if (phoneNo.length() != 11)
            return false;
        return phoneNo.startsWith("1");
    }

    /**
     * 随机生成六位数，不过这六位数在100000+
     *
     * @return
     */
    public static String get6NumberInRandom() {
        int number = new Random().nextInt(899999) + 100000;
        return number + "";
    }

    public static String getWeekName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        String day = null;
        switch (weekDay) {
            case Calendar.MONDAY:
                day = "星期一";
                break;
            case Calendar.TUESDAY:
                day = "星期二";
                break;
            case Calendar.WEDNESDAY:
                day = "星期三";
                break;
            case Calendar.THURSDAY:
                day = "星期四";
                break;
            case Calendar.FRIDAY:
                day = "星期五";
                break;
            case Calendar.SATURDAY:
                day = "星期六";
                break;
            case Calendar.SUNDAY:
                day = "星期日";
                break;

        }
        return day;

    }

    /**
     * list转String
     * 会调用toString方法进行拼接，
     * @param list
     * @param separator 用于拼接的连接符号
     * @return
     */
    public static String list2String(List<Object> list,String separator){
        StringBuffer sb = new StringBuffer();
        Iterator<Object> it = list.iterator();
        while (it.hasNext()){
            Object o= it.next();
            if (it.hasNext()){
                sb.append(o+separator);
            } else {
                sb.append(o+"");
            }
        }
        return sb.toString();
    }

//	public static String formatNum(int view) {
//		String unit ="";
//		String result="";
//		if (view/100000000>0){
//			result=view/100000000+"亿";
//		}
//		return 0;
//	}
}