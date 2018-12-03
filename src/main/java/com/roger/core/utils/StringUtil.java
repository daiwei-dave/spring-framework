package com.roger.core.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    /**
     * 将驼峰标识转换为下划线
     *
     * @param text
     * @return camel
     */
    public static String camelToUnderline(String text) {
        if (text == null || "".equals(text.trim())) {
            return "";
        }
        StringBuffer result = new StringBuffer(text.length() + 1);
        result.append(text.substring(0, 1));
        for (int i = 1; i < text.length(); i++) {
            if (!Character.isLowerCase(text.charAt(i))) {
                result.append('_');
            }
            result.append(text.substring(i, i + 1));
        }
        return result.toString().toLowerCase();
    }

    /**
     * 将下划线标识转换为驼峰
     *
     * @param text
     * @return underline
     */
    public static String underlineToCamel(String text) {
        if (text == null || "".equals(text.trim())) {
            return "";
        }
        int length = text.length();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if (c == '_') {
                if (++i < length) {
                    result.append(Character.toUpperCase(text.charAt(i)));
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static List<String> fetchAllSubStr(List<String> subStrList, String sourceStr, String strStart, String endStr) {
        if (CollectionUtils.isEmpty(subStrList)) {
            subStrList = new ArrayList<>();
        }
        String subStr = subString(sourceStr,
                strStart.replaceAll("\\\\", ""),
                endStr.replaceAll("\\\\", ""));
        if (!StringUtils.isEmpty(subStr)) {
            subStrList.add(subStr);
        }
        if (sourceStr.indexOf(strStart.replaceAll("\\\\", "")) >= 0) {
            sourceStr = sourceStr.replaceFirst(strStart, "@@@");
            sourceStr = sourceStr.replaceFirst(endStr, "@@@");
            fetchAllSubStr(subStrList, sourceStr, strStart, endStr);
        }
        return subStrList;
    }

    /**
     * 截取两个特定字符之间的子字符串
     *
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0 || strEndIndex < 0) {
            return null;
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }
}
