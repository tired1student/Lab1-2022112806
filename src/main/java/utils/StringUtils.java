package main.java.utils;

import java.util.List;

public class StringUtils {
    public static String[] preprocess(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("[^a-z\\s]", " ");
        text = text.replaceAll("\\s+", " ");
        return text.trim().split(" ");
    }

    public static String join(List<String> list, String delimiter) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            sb.append(delimiter).append(list.get(i));
        }
        return sb.toString();
    }
}