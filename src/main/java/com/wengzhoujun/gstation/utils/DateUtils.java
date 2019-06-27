package com.wengzhoujun.gstation.utils;

import javax.sound.midi.SoundbankResource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2019/6/27.
 *
 * @author WengZhoujun
 */
public class DateUtils {

    public static DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date strToDate(String dateStr) {
        try {
            return yyyyMMddHHmmss.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
