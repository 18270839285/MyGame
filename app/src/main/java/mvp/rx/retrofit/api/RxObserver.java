package mvp.rx.retrofit.api;

import android.util.Log;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import mvp.rx.retrofit.App;
import mvp.rx.retrofit.utils.NetWorkUtil;
import mvp.rx.retrofit.utils.ToastUtil;

/**
 * Description:
 * Data：2018/10/23-14:36
 * Author: lin
 */
public abstract class RxObserver<T> extends DisposableObserver<T> {

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {
        Log.e("RxObserver", "onNext");
        if (!isDisposed()) {
            onHandleSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("RxObserver", "onError:" + e.getMessage());
        _onError(e);
        onHandleFailed(e.getMessage());
    }

    @Override
    public void onComplete() {
        Log.e("RxObserver", "onComplete");
        onHandleComplete();
    }

    void _onError(Throwable e) {
        Log.e("_onError: ", e.toString());
        Log.e("_onError: ", e.getCause()+"");
        e.printStackTrace();
        if (!NetWorkUtil.isNetworkConnected(App.getAppContext())) {
            ToastUtil.showShort("网络不可用");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtil.showShort("请求超时,请稍后再试...");
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            ToastUtil.showShort("网络连接异常,请稍后再试...");
        } else if (e instanceof HttpException) {
            ToastUtil.showShort("服务器异常,请稍后再试...");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
//            ToastUtil.showShort("解析错误,请稍后再试...");
        } else {
            ToastUtil.showShort(e.getMessage());
        }

    }

    public abstract void onHandleSuccess(T t);

    public abstract void onHandleComplete();

    public abstract void onHandleFailed(String message);
}
