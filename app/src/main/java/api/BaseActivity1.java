package api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.io.Serializable;

import lilin.com.myapplication.R;

public abstract class BaseActivity1 extends AppCompatActivity {
    /**
     * 当前Activity名字
     */
    protected static String ACTIVITY_NAME;
    protected Bundle savedInstanceState;
    public Context context;
//    public CashLoanApp application;

    /**
     * 屏幕宽高, 状态栏高
     */
//    public int screenWidth, screenHeight, statusBarHeightX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
//        LogUtils.i(ACTIVITY_NAME = getClass().getSimpleName());
        context = this;
//        application = (CashLoanApp) getApplication();
//        AppManager.getInstance().addActivity(this);
        setContentView(contentView());
//        setWindowStatusBarColor(this,getResources().getColor(R.color.A1));
        initView();
        initListener();
        initData();
    }

    public  void setWindowStatusBarColor(Activity activity, int color) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
//                window.setStatusBarColor(Color.parseColor("#"+color));
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        isActivityFinish = true;
//        AppManager.getInstance().killActivity(this);
        finish();
    }

    public Toast toast;

    public void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public void showToast(BaseActivity1 activity, String text) {
        if (toast == null) {
            toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 显示带图片的Toast * @param text 需要显示的文字 * @param imgId 需要显示的图片
     */
    public void showImageToast(String text, int imgId) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setText(text);
        LinearLayout toastView = (LinearLayout) toast.getView();
        toastView.setGravity(Gravity.CENTER);
        ImageView toastImg = new ImageView(context);
        toastImg.setLayoutParams(new LinearLayout.LayoutParams(dp2px(25), dp2px(25)));
        toastImg.setScaleType(ImageView.ScaleType.FIT_XY);
        toastImg.setImageResource(imgId);
        toastView.addView(toastImg, 0);
        toast.show();
    }

    public void showToast(int textId) {
        showToast(getString(textId));
    }

    public BaseActivity1 getActivity() {
        return this;
    }

    public Intent getIntent(Class clazz) {
        return new Intent(context, clazz);
    }

    public void startIntent(Class clazz) {
        startActivity(getIntent(clazz));
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    protected void hideStatusBar() {
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;//隐藏状态栏, 定义全屏参数
        Window window = getWindow(); //获得当前窗体对象
        window.setFlags(flag, flag);//设置当前窗体为全屏显示
    }


    private long lastClickTime;

    /**
     * 实现连续点击两次才退出应用程序
     */
    public void exit() {
        if ((System.currentTimeMillis() - lastClickTime) > 2000) {
            showToast("再按一次退出" + context.getResources().getString(R.string.app_name));
            lastClickTime = System.currentTimeMillis();
        } else {
//            AppManager.getInstance().AppExit(this);
            System.exit(0);
        }
    }

    public void startActivity(Class<?> cls,  boolean flash) {
        Intent intent = new Intent(this, cls);
//        if (obj != null)
//            intent.putExtra("data", (Serializable) obj);
        startActivity(intent);
//        if (flash)
//            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
//        else
//            overridePendingTransition(android.R.anim.fade_in,
//                    android.R.anim.fade_out);
    }

    public void startActivity(Class<?> cls,Object obj,  boolean flash) {
        Intent intent = new Intent(this, cls);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivity(intent);
//        if (flash)
//            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
//        else
//            overridePendingTransition(android.R.anim.fade_in,
//                    android.R.anim.fade_out);
    }
    @Override
    public void finish() {
//        AppManager.getInstance().killActivity(this);
        super.finish();
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    public abstract int contentView();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

}