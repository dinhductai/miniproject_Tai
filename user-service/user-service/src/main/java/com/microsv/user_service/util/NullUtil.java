package com.microsv.user_service.util;

import com.microsv.user_service.exception.custom.IllegalArgumentExceptionCustom;

import java.util.List;

public class NullUtil {
    //nếu value bằng null thì trả default value
    public static <T> T nullCheckDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }


//    public static String getOrDefault(String value, String defaultValue) {
//        return (value != null && !value.isEmpty()) ? value : defaultValue;
//    }

//    public static boolean oneItemOfListNull(List<Object> list) {
//        for(Object item : list) {
//            if(item == null) {
//                throw()
//            }
//        }
//    }
    //trả chuỗi rỗng
    public static String safeTrim(String str) {
        return str == null ? "" : str.trim();
    }


    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

}
