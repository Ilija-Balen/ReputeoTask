package com.example.reputeo.taskReputeo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkUtil {

    private static final Pattern URL_PATTERN = Pattern.compile(
            "(https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+)");

    public static String linkify(String plainText) {
        Matcher matcher = URL_PATTERN.matcher(plainText);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String url = matcher.group();
            String anchor = "<a href=\"" + url + "\">" + url + "</a>";
            matcher.appendReplacement(sb, anchor);
        }

        matcher.appendTail(sb);
        return sb.toString().replace("\n", "<br>");
    }
}
