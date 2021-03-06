package com.demo.takeoutapp.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyHttpUtil {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ip = "http://81.70.179.4:8089";
    //private String ip = "http://192.168.123.196:8089";

    private int overtime = 10;

    private static class SingletonClassInstance {
        private static final MyHttpUtil np = new MyHttpUtil();
    }

    public MyHttpUtil() {

    }

    public static synchronized MyHttpUtil getInstance() {
        return SingletonClassInstance.np;
    }

    public void GET(String afterurl, HttpCallBack h) {
        //ttp://81.70.179.4:8089/test/test
        String url = ip + afterurl;
        doGET(url, h);
    }

    public void doGET(final String urls, final HttpCallBack h) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(overtime, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(overtime, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(overtime, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        Request request = new Request.Builder()
                .url(urls)
                .get()
                .build();
        Call call = client.newCall(request);
        //异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                h.Error(call, e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                response.body().close();
                try {
                    System.out.println("GET返回数据:" + res);
                    h.Success(call, res);
                } catch (Exception e) {
                    h.Error(call, null);
                    e.printStackTrace();
                }
            }
        });
    }

    public void POST(String afterurl, String json, HttpCallBack h) {
        String url = ip + afterurl;
        doPost(url, json, h);
    }

    public void doPost(String url, String json, final HttpCallBack httpCallBack) {
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(overtime, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(overtime, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(overtime, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallBack.Error(call, e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response == null) {
                    httpCallBack.Error(call, null);
                    return;
                }
                if (response.body() == null) {
                    httpCallBack.Error(call, null);
                    return;
                }
                String str = response.body().string();
                try {
                    System.out.println("POST返回数据:" + str);
                    httpCallBack.Success(call, str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
