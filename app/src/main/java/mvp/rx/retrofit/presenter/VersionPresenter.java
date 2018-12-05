package mvp.rx.retrofit.presenter;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import mvp.rx.retrofit.api.RxHelper;
import mvp.rx.retrofit.api.RxObserver;
import mvp.rx.retrofit.base.BaseActivity;
import mvp.rx.retrofit.base.BasePresenter;
import mvp.rx.retrofit.base.BaseRequestEntity;
import mvp.rx.retrofit.base.BaseResponseEntity;
import mvp.rx.retrofit.model.Version;
import mvp.rx.retrofit.model.Versions;
import mvp.rx.retrofit.view.VersionView;

/**
 * Description:
 * Data：2018/10/23-15:45
 * Author: lin
 */
public class VersionPresenter extends BasePresenter<VersionView> {

    private VersionView versionView;
    public VersionPresenter(Context context, VersionView view) {
        super(context, view);
        versionView = view;
    }

    public void getVersion(String app_from){
        RxObserver rxObserver = new RxObserver<Versions>() {
            @Override
            public void onHandleSuccess(Versions version) {
                versionView.getVersion(version);
            }

            @Override
            public void onHandleComplete() {

            }

            @Override
            public void onHandleFailed(String message) {

            }
        };
        Version version = new Version();
        version.setApp_from("4");
        BaseRequestEntity<Version> requestEntity =
                new BaseRequestEntity<Version>();
        requestEntity.setData(version);
        Observable<BaseResponseEntity<Versions>> observable = mRxSerivce.getVersion(version);
        Disposable disposable = observable
                .compose(RxHelper.<Versions>handleObservableResult())
                .subscribeWith(rxObserver);
        addSubscribe(disposable);
    }

}
