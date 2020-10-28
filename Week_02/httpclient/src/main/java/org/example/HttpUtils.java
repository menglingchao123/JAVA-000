package org.example;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 孟令超
 * @version 1.0
 * @desription http工具
 * @date 2020/10/27
 * @since jdk1.8
 */
public class HttpUtils {

    /**
     * get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, String> params) {
        Response response = null;
        try {
            //获取okhttp客户端
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
            //构建request对象
            Request request = new Request.Builder().get().url(buildReqParam(params, url)).build();
            //包装request对象
            Call call = client.newCall(request);
            //发送请求
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException("send http request error", e);
        }
        return decodeResponse(response);
    }

    /**
     * 解析请求结果
     *
     * @param response
     * @return
     */
    private static String decodeResponse(Response response) {
        return Optional.ofNullable(response).map(Response::body).map(responseBody -> {
            try {
                return responseBody.string();
            } catch (IOException e) {
                throw new RuntimeException("decode response error", e);
            }
        }).get();
    }


    /**
     * 构建请求参数
     *
     * @param params
     * @param url
     * @return
     */
    private static String buildReqParam(Map<String, String> params, String url) {
        if (params != null && !params.isEmpty()) {
            url = url + "?";
            StringBuilder builder = new StringBuilder(url);
            params.forEach((k, v) -> builder.append(k).append("=").append(v).append("&"));
            url = builder.toString();
            url.substring(0, url.length() - 1);
        }
        return url;
    }
}
