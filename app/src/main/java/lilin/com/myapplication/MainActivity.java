package lilin.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import api.API;
import api.BaseModel;
import api.OkHttpUtils;
import api.OkManager;
import api.RequestFinish;
import bean.LoginBean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RequestFinish {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        http://jz.zhibohome.net/api/common/register/info   {"token":"URJoaEJiIJr4W4QvCwcP"}
        OkHttpUtils.getInstance().resignInfo(MainActivity.this, this, "http://jz.zhibohome.net/api/common/register/info","URJoaEJiIJr4W4QvCwcP");
        OkHttpUtils.getInstance().login(MainActivity.this, this, "18270839285", GetMD5Code("123456"));
//        startActivity(new Intent(this,Main4Activity.class));
    }

    LoginBean loginBean;

    @Override
    public void onSuccess(BaseModel result, String params) {
        Log.e("onSuccess: ", "BaseModel.params"+params+" ");
//        login.setText(loginBean.getUser().getRe_realname() + " ");
    }

    @Override
    public void onError(String result) {
        Log.e("onSuccess", "onError: " + result);
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("this", "onDestroy: this = "+ MainActivity.this);
        OkManager.getInstance().cancelTag(MainActivity.this);
    }
}
