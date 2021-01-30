package com.finartz.airlinesticketsystem.util;

public class Utils {

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
}
