package com.xinyi_tech.commonlibs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by studyjun on 2016/5/3.
 */
public class ErrorHelper {

    public static String getStaceTrace(Throwable ex){
        if (ex==null)
            return "";
        Map<String,Object> infos =new HashMap<>();
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        pw.close();
        return writer.toString();
    }
}
