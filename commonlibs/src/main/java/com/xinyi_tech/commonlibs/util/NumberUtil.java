package com.xinyi_tech.commonlibs.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by studyjun on 2016/5/5.
 */
public class NumberUtil {

    public static String keepSignificantDigits(double d,String pattern){
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(d);
    }


  public static String getTime(Date date, String sDate){
      SimpleDateFormat formatter=new SimpleDateFormat(sDate);
        return formatter.format(date);
    }



}
