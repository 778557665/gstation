package com.wengzhoujun.gstation.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
public class UrlUtils {

    public static String getTopDomainWithoutSubDomain(String url) {
        try {
            Pattern pattern = Pattern.compile("(.*\\.)*(\\w*\\.\\w*)");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return matcher.group(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
