package mvp.rx.retrofit.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import mvp.rx.retrofit.ActivityManager;
import mvp.rx.retrofit.utils.NetWorkUtil;
import mvp.rx.retrofit.utils.StatusBarUtil;

/**
 * Description:
 * Data：2018/10/23-14:34
 * Author: lin
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
//    public BaseController mBaseController;
    protected T mPresenter;
    private static final int VIBRATE_DURATION = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 把actvity放到application栈中管理
        ActivityManager.getInstance().onCreate(this);
        //设置状态栏
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);

        //注册EventBus
//        mBaseController.register(this);
        //创建Presenter
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }

        if (NetWorkUtil.isNetworkConnected(this)) {
            setContentView(getNormalLayoutId());
        } else {
            setContentView(getErrorLayoutId());
        }
        initViewsAndEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁EventBus
//        mBaseController.unregister(this);
        //解除presenter与view之间的关系
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        ActivityManager.getInstance().onDestroy(this);
    }

    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }

    //接收EventBus的消息
    @Subscribe
    public void onMessageEventMain(Message message) {
//        mBaseController.onMessageEventMain(this, message);
    }

    //正常的页面
    protected abstract int getNormalLayoutId();

    //没有网络后加载的页面
    protected abstract int getErrorLayoutId();

    protected abstract T createPresenter();

    //加载数据
    protected abstract void initViewsAndEvents();
}
