package api;

/**
 * Created ： LiLin
 * Date ： 2017/10/10
 * Function ：HttpOk
 */
public interface RequestFinish {

    void onSuccess(BaseModel result, String params);

    void onError(String result);
}
