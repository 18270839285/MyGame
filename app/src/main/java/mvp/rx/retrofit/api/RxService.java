package mvp.rx.retrofit.api;

import io.reactivex.Observable;
import mvp.rx.retrofit.base.BaseResponseEntity;
import mvp.rx.retrofit.model.Version;
import mvp.rx.retrofit.model.Versions;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Description:
 * Data：2018/10/23-14:35
 * Author: lin
 */
public interface RxService {
    //登陆
    @POST("api/app/getVersions")
    Observable<BaseResponseEntity<Versions>> getVersion(
            @Body Version entity);

}
