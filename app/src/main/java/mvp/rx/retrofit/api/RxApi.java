package mvp.rx.retrofit.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import mvp.rx.retrofit.App;
import mvp.rx.retrofit.utils.NetWorkUtil;
import mvp.rx.retrofit.base.LoggerInterceptorRefactoring;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:RxJava 的网络请求
 * Data：2018/10/23-14:36
 * Author: lin
 */
public class RxApi {


    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 7676;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 7676;

    /*************************缓存设置*********************/
/*
   1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存*/

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    private static final String BASE_URL = "http://testgold.51bangday.com/";//换成你上传用的服务器地址
    //    private static final String BASE_URL = "http://test.ilivehome.net/";//测试
    private static final int TIME_OUT = 30;//超时时长，单位：秒
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private static RxApi INSTANCE;

    static {
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //Debug开启Log
//        if (BuildConfig.DEBUG) {
//
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                @Override
//                public void log(String message) {
//                    Log.d("Wislie", "message:" + message);
//                }
//            });
////            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            okHttpClientBuilder.addInterceptor(loggingInterceptor);
//        }
        //缓存
        File cacheFile = new File(App.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100MB
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };
        /**
         * 云端响应头拦截器，用来配置缓存策略
         * Dangerous interceptor that rewrites the server's cache-control header.
         */
        Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.e("Request", request.url() + getParameters(request.body()));
                String cacheControl = request.cacheControl().toString();
                if (!NetWorkUtil.isNetworkConnected(App.getAppContext())) {
                    request = request.newBuilder()
                            .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetWorkUtil.isNetworkConnected(App.getAppContext())) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };


        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };


        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        mOkHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(DO_NOT_VERIFY)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .addInterceptor(mRewriteCacheControlInterceptor)
//                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(new LoggerInterceptorRefactoring())
                .addInterceptor(headerInterceptor)
                .retryOnConnectionFailure(true) //失败重连
                .cache(cache)
                .build();


        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkUtil.isNetworkConnected(App.getAppContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * 获取根服务地址
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }


    /**
     * 初始化 Retrofit
     */
    public static RxApi getInstance() {
        if (INSTANCE == null) {
            synchronized (RxApi.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RxApi();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 创建数据请求服务
     */
    public <T> T getService(Class<T> service) {
        return mRetrofit.create(service);
    }

    private static String getParameters(RequestBody requestBody) {
        try {
            final Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            //
            BufferedInputStream bufferedInputStream = new BufferedInputStream(buffer.inputStream());
            final StringBuffer resultBuffer = new StringBuffer();
            byte[] inputBytes = new byte[1024];
            while (true) {
                int count = bufferedInputStream.read(inputBytes);
                if (count <= 0) {
                    break;
                }
                resultBuffer.append(new String(Arrays.copyOf(inputBytes, count), Util.UTF_8));
            }
            final String parameter = URLDecoder.decode(resultBuffer.toString(),
                    Util.UTF_8.name());
            bufferedInputStream.close();


            return parameter;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

