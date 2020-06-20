package com.jackdyt.blog.utils;

import java.util.Random;


public class SaltUtils {

    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char ch = chars[new Random().nextInt(chars.length)];
            sb.append(ch);
        }
        return sb.toString();
    }
}
