package com.example.demo.utils;

public class StringUtils {

    public static boolean hasMoreThanXCharacters(String input, int x) {
        try {
            return input.length() > x;
        }catch (Exception e){
            throw e;
        }

    }
}
