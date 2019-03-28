package com.app.core.common;

import java.util.UUID;

public class CommonUtil {

    public static String genUUID(){
       return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /*
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(uuid);
        }
    }
    */
}
