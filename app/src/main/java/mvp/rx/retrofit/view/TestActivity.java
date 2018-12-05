package mvp.rx.retrofit.view;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lilin.com.myapplication.R;
import mvp.rx.retrofit.base.BaseActivity;
import mvp.rx.retrofit.base.BasePresenter;
import mvp.rx.retrofit.model.Version;
import mvp.rx.retrofit.model.Versions;
import mvp.rx.retrofit.presenter.VersionPresenter;

public class TestActivity extends BaseActivity<VersionView,VersionPresenter> implements VersionView{
    Button btn;
    TextView textView;
    @Override
    protected void onStart() {
        super.onStart();
        btn = (Button) findViewById(R.id.test);
        textView = (TextView) findViewById(R.id.test_msg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getVersion("4");
            }
        });
    }

    @Override
    public void getVersion(Versions version) {
        Log.e("getVersion: ", "url = ");
    }

    @Override
    public void requestFailed(String message) {
        Log.e("requestFailed: ","message = "+message );
    }

    @Override
    protected int getNormalLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected int getErrorLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected VersionPresenter createPresenter() {
        return new VersionPresenter(this,this);
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
