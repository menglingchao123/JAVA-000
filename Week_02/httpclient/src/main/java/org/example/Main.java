package org.example;


import java.util.Collections;

public class Main {

    public static void main( String[] args ) {
        String url = "http://localhost:8808/test";
        String result = HttpUtils.doGet(url, Collections.EMPTY_MAP);
        System.out.println("获取请求结果================"+result);
    }
}
