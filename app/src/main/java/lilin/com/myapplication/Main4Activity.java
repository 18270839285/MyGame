package lilin.com.myapplication;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.security.auth.login.LoginException;

import api.BaseActivity1;
import api.BaseModel;
import api.OkHttpUtils;
import api.RequestFinish;
import bean.AccountDataBean;
import view.WHTextView;


public class Main4Activity extends BaseActivity1 implements RequestFinish{
    private MyAdapter myAdapter;
    private RecyclerView recycleView;
    private TextView add;
    private TextView delete;
    private ProgressDialogFragment dialog;
    @Override
    public void onSuccess(BaseModel result, String params) {
        Log.e("BaseModel", "onSuccess: params = "+params );
        AccountDataBean info = (AccountDataBean)result.getData();
        infoBeans.add(info.getRegister_info());
        myAdapter.setNewData(infoBeans);
    }

    @Override
    public void onError(String result) {

    }

    List<AccountDataBean.RegisterInfoBean> infoBeans = new ArrayList<>();
    @Override
    public int contentView() {
        return R.layout.activity_main4;
    }

    @Override
    public void initView() {
        dialog = new ProgressDialogFragment();
        dialog.show(getSupportFragmentManager());
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(R.layout.activity_test,infoBeans);
        myAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleY", 1, 0.8f, 1),
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 0.8f, 1)
                };
            }
        });
        myAdapter.isFirstOnly(false);
        recycleView.setAdapter(myAdapter);
        getTime();
    }

    public void getTime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;//取得资源对象
                try {
                    url = new URL("http://www.baidu.com");
                    URLConnection baidu = url.openConnection();//生成连接对象
                    baidu.connect(); //发出连接
                    long time13 =  baidu.getDate(); //取得网站日期时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    String  currentTime = sdf.format(new Date(time13));
                    System.out.println("当前时间："+currentTime);
//                    setSystemDate(currentTime);         //  设置系统时间
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void initListener() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.addData(infoBeans);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.remove(infoBeans.size()-1);
            }
        });
    }

    @Override
    public void initData() {
        OkHttpUtils.getInstance().resignInfo(this, this, "http://jz.zhibohome.net/api/common/register/info","URJoaEJiIJr4W4QvCwcP");
    }

    public class MyAdapter extends BaseQuickAdapter<AccountDataBean.RegisterInfoBean,BaseViewHolder>{
        public MyAdapter(int layoutResId, @Nullable List<AccountDataBean.RegisterInfoBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AccountDataBean.RegisterInfoBean item) {
            Log.e(TAG, "convert: "+item.getTelephone() );
            helper.setText(R.id.test,item.getTelephone());
            helper.setText(R.id.test_msg,item.getInfo_url());
        }

    }
}
