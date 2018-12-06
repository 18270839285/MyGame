package api;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created ： LiLin
 * Date ： 2017/10/10
 * Function ：OkManager
 */
public class OkManager {
    private OkHttpClient client;
    private volatile static OkManager okManager;

    protected static Gson mGson;
    private Handler handler;
    private final String TAG = OkManager.class.getSimpleName();

    OkHttpClient.Builder httpBuilder;
    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    //提交字符串
    private static final String MEDIA_TYPE_MULTIPART = "multipart/form-data";

    private OkManager() {
        handler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
        //不需要设置请求超时是调用
//        client = new OkHttpClient();
        //需要设置请求超时调用下面两行
        httpBuilder = new OkHttpClient.Builder();
        client = httpBuilder.readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS) //设置超时
                .build();

    }

    /**
     * 采用单例获取对象
     *
     * @return
     */
    public static OkManager getInstance() {
        if (okManager == null) {
            synchronized (OkManager.class) {
                if (okManager == null) {
                    okManager = new OkManager();
                }
            }
        }
        return okManager;
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    public void postRequest(final Object object, final RequestConfig config, String url, Map<String, String> params, final RequestFinish callBack) {
        Log.e("postRequest", "url = " + url);
        FormBody.Builder form_builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                form_builder.add(entry.getKey(), entry.getValue());
            }
        }
        form_builder.add("app_from", "7");
        RequestBody request_body = form_builder.build();
        Request request = new Request.Builder().url(url).post(request_body).tag(object).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (call.isCanceled()) //关闭界面的取消请求执行这里
                    onError(callBack, "取消了这次请求");
                else if (e.getClass().equals(SocketTimeoutException.class))
                    onError(callBack, "请求超时");
                else if (e.getClass().equals(ConnectException.class))
                    onError(callBack, "网络错误");
                else
                    onError(callBack, "服务器异常");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccess(config, response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * post请求添加请求头
     *
     * @param url
     * @param params
     * @param callBack
     */
    public void postHeadRequest(final RequestConfig config, String url, Map<String, String> params, final RequestFinish callBack) {
        FormBody.Builder form_builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                form_builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody request_body = form_builder.build();
        Request request = new Request.Builder().url(url).addHeader(API.HEADER, API.HEADER_VALUE).post(request_body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getClass().equals(SocketTimeoutException.class))
                    onError(callBack, "请求超时");
                else
                    onError(callBack, "无法请求服务器");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccess(config, response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * get请求
     *
     * @param url
     * @param callBack
     */
    public void getRequest(final RequestConfig config, String url, Map<String, String> params, final RequestFinish callBack) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url += entry.getKey() + "=" + entry.getValue();
            }
        }
        Log.e("getRequest", "url = " + url);
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (call.isCanceled())
                    onError(callBack, "取消了这次请求");
                else if (e.getClass().equals(SocketTimeoutException.class))
                    onError(callBack, "请求超时");
                else if (e.getClass().equals(ConnectException.class))
                    onError(callBack, "网络错误");
                else
                    onError(callBack, "服务器异常");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccess(config, response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * post请求可上传图片
     *
     * @param url
     * @param params
     * @param callBack
     */
    public void postRequestPic(final RequestConfig config, String url, Map<String, Object> params, final RequestFinish callBack) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue() instanceof File) {
                        File f = (File) entry.getValue();
                        builder.addFormDataPart(entry.getKey(), f.getName(),
                                RequestBody.create(MediaType.parse("image/*"), f));
                    } else {
                        builder.addFormDataPart(entry.getKey(), entry
                                .getValue().toString());
                    }
                }
            }
        }
        RequestBody request_body = builder.build();
        Request request = new Request.Builder().url(url).post(request_body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getClass().equals(SocketTimeoutException.class))
                    onError(callBack, "请求超时");
                else
                    onError(callBack, "无法请求服务器");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccess(config, response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * post请求可上传图片
     *
     * @param url
     * @param params
     * @param callBack
     */
    public void postRequestImg(final RequestConfig config, String url, Map<String, String> params, Map<String, Object> params2, final RequestFinish callBack) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry
                        .getValue().toString());
//                builder.addFormDataPart(entry.getKey(), entry.getValue().getName(),
//                        RequestBody.create(MediaType.parse("image/*"), entry.getValue().toString()));
            }
        }
        if (params2 != null && !params2.isEmpty()) {
            for (Map.Entry<String, Object> entry : params2.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry
                        .getValue().toString());
            }
        }
        RequestBody request_body = builder.build();
        Request request = new Request.Builder().url(url).post(request_body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getClass().equals(SocketTimeoutException.class))
                    onError(callBack, "请求超时");
                else
                    onError(callBack, "无法请求服务器");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccess(config, response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * 获取的是字符串
     *
     * @param jsonString
     * @param callBack
     */
    public void onSuccess(final RequestConfig config, final String jsonString, final RequestFinish callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    BaseModel baseMode = null;
                    try {
                        Log.e("onSuccess", config.getRequestCode() + "  json = " + jsonString);
                        baseMode = parseData(config, jsonString);
                        if (baseMode.getCode().equals(API.HTTP_OK))
                            callBack.onSuccess(baseMode, config.getRequestCode());
                        else
                            callBack.onError(baseMode.getMessage());
                        //登录失效  重新登录操作
//                        if (e.getMessage().equals("token失效:-2")) {
//                            Intent intent = new Intent(App.getAppContext(), LoginActivity.class);
//                            intent.putExtra("isFromToken", true);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            GlobalApp.getAppContext().startActivity(intent);
//                        }
                    } catch (JsonSyntaxException e) {
                        callBack.onError("数据格式错误");
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (baseMode.getCode().equals(API.HTTP_OK))
//                            callBack.onError(e.getMessage());
                            e.printStackTrace();
                        else
                            callBack.onError(baseMode.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 请求错误
     *
     * @param callBack
     */
    public void onError(final RequestFinish callBack, final String string) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onError(string);//(config,jsonString)
//                    switch (string){
//                        case API.BANKCODEERROR:
//                            break;
//                        default:
//                            callBack.onError(string);
//                            break;
//                    }
                }
            }
        });
    }

    public BaseModel parseData(RequestConfig config, String data) throws JsonSyntaxException {
        BaseModel base = mGson.fromJson(data, BaseModel.class);
        if (base.getCode().equals(API.HTTP_OK)) {
            Type t = null;
            if (config.getCls() != null) {
                if (config.getCls().getName().equals(ArrayList.class.getName())) {
                    t = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, BaseModel.class,
                            com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, config.getCls(),
                                    config.getElement()));
                } else
                    t = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, BaseModel.class,
                            config.getCls());
                return mGson.fromJson(data, t);
            } else {
                if (config.getElement() != null) {
                    t = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, BaseModel.class,
                            com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class,
                                    config.getElement()));
                    return mGson.fromJson(data, t);
                }
            }
        }
        return base;
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancelTag(Object tag) {
        if (tag == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        for (Call call : client.dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            call.cancel();
        }
    }

}
