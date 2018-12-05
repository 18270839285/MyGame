package api;

import java.util.HashMap;
import java.util.Map;

import bean.AccountDataBean;
import bean.LoginBean;
import mvp.rx.retrofit.App;

/**
 * Description:
 * Data：2018/12/3-11:16
 * Author: lin
 */
public class OkHttpUtils {
    private volatile static OkHttpUtils httpUtils;
    private OkManager okManager = OkManager.getInstance();

    /**
     * 采用单例获取对象
     *
     * @return
     */
    public static OkHttpUtils getInstance() {
        if (httpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new OkHttpUtils();
                }
            }
        }
        return httpUtils;
    }

    /**
     * 登陆
     *
     * @param protocol
     * @param name
     * @param password
     */
    public void login(Object object, RequestFinish protocol,  String name, String password) {
        RequestConfig config = new RequestConfig();
        config.setCls(LoginBean.class);
        config.setRequestCode(API.LOGIN);
        Map<String, String> data = new HashMap<String, String>();
        data.put("telephone", name);
        data.put("password", password);
        data.put("from", "android");
        okManager.postRequest(object, config, API.BASE_URL+ API.LOGIN, data, protocol);
    }

    public void resignInfo(Object object, RequestFinish protocol, String url, String token) {
        RequestConfig config = new RequestConfig();
        config.setCls(AccountDataBean.class);
        config.setRequestCode(API.REGISTER);
        Map<String, String> data = new HashMap<String, String>();
        data.put("token", token);
        okManager.postRequest(object, config, url, data, protocol);
    }

}
