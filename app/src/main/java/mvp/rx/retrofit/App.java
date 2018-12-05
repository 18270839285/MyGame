package mvp.rx.retrofit;

import android.app.Application;
import android.content.Context;

/**
 * Description:
 * Data：2018/10/23-14:32
 * Author: lin
 */
public class App extends Application{
    public static App mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
