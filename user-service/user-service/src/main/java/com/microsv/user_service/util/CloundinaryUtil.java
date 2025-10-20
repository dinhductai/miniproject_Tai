package com.microsv.user_service.util;

public class CloundinaryUtil {
    public static String extractPublicId(String url) {
        if(url != null){
            //xóa loại ảnh ví dụ .png
            Integer lastDotIndex = url.lastIndexOf(".");
            if(lastDotIndex != -1){
                url = url.substring(0, lastDotIndex);
            }
            //loại bỏ phần trước,chỉ giữ lại public id
            Integer firstSlastIndex = url.lastIndexOf("/");
            if(firstSlastIndex != -1){
                url = url.substring(firstSlastIndex+1);
            }
            return url;
        }else return null;
    }
}
