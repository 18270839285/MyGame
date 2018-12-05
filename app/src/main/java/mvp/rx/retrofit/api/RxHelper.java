package mvp.rx.retrofit.api;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mvp.rx.retrofit.base.BaseResponseEntity;

/**
 * Description:请求数据的统一封装
 * Data：2018/10/23-14:57
 * Author: lin
 */
public class RxHelper {
    public static <T> ObservableTransformer<BaseResponseEntity<T>, T> handleObservableResult() {

        return new ObservableTransformer<BaseResponseEntity<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponseEntity<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponseEntity<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull BaseResponseEntity<T> result) throws Exception {
//                        convertToJson(result);
                        if (result != null && result.getCode().equals("0")) {
                            return createData(result.getData());
                        } else {
                            String message = null;
                            if (result != null && !TextUtils.isEmpty(result.getMessage())) {
                                message = result.getMessage();
                            } else {
                                message = result.getCode();
                            }
                            return Observable.error(new Exception(message + ":" + result.getCode()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {

            @Override
            public void subscribe(ObservableEmitter<T> emitter) {
                try {
                    if (data != null) {
                        emitter.onNext(data); //发送数据
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });

    }

    public static <T> FlowableTransformer<BaseResponseEntity<T>, T> handleFlowableResult() {

        return new FlowableTransformer<BaseResponseEntity<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseResponseEntity<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponseEntity<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(@NonNull BaseResponseEntity<T> result) throws Exception {
//                        if(result.getCode() == 1){ //这个根据实际情况改
//                            return createData(result.getData());
//                        }else{
//                            return Observable.error(new Exception(result.getMessage())); //这个根据实际情况改
//                        }
                        return null;
                    }
                });
            }
        };
    }

    private static void convertToJson(BaseResponseEntity result) {
        Gson gson = new Gson();
        String json = gson.toJson(result);
        Log.e("response", json);
    }
}
