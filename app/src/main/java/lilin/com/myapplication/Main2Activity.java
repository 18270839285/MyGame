package lilin.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import api.OkManager;

/**
 * 游戏首页-选择页
 */
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    Button easy, common, difficult;
    Button shudu1, shudu2, shudu3, shudu4;

    public static boolean isFinish1 = false;
    public static boolean isFinish2 = false;
    public static boolean isFinish3 = false;
    public static boolean isFinish4 = false;
    public static boolean isFinish5 = false;
    public static boolean isFinish6 = false;
    public static boolean isFinish7 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initData();
        initListener();
    }

    private void initData() {
        easy = findViewById(R.id.easy);
        common = findViewById(R.id.common);
        difficult = findViewById(R.id.difficult);
        shudu1 = findViewById(R.id.shudu1);
        shudu2 = findViewById(R.id.shudu2);
        shudu3 = findViewById(R.id.shudu3);
        shudu4 = findViewById(R.id.shudu4);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.easy:
                intent = new Intent(Main2Activity.this, SixToSixActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.common:
                intent = new Intent(Main2Activity.this, SixToSixActivity.class);
                intent.putExtra("type", 4);
                startActivity(intent);
                break;
            case R.id.difficult:
                intent = new Intent(Main2Activity.this, SixToSixActivity.class);
                intent.putExtra("type", 5);
                startActivity(intent);
                break;
            case R.id.shudu1:
                intent = new Intent(Main2Activity.this, ShuDuActivity.class);
                intent.putExtra("type", 4);
                startActivity(intent);
                break;
            case R.id.shudu2:
                intent = new Intent(Main2Activity.this, ShuDuActivity.class);
                intent.putExtra("type", 5);
                startActivity(intent);
                break;
            case R.id.shudu3:
                intent = new Intent(Main2Activity.this, ShuDuActivity.class);
                intent.putExtra("type", 6);
                startActivity(intent);
                break;
            case R.id.shudu4:
                intent = new Intent(Main2Activity.this, ShuDuActivity.class);
                intent.putExtra("type", 7);
                startActivity(intent);
                break;
        }
    }

    private void initListener() {
        easy.setOnClickListener(this);
        common.setOnClickListener(this);
        difficult.setOnClickListener(this);
        shudu1.setOnClickListener(this);
        shudu2.setOnClickListener(this);
        shudu3.setOnClickListener(this);
        shudu4.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFinish();
    }

    private void setFinish(){
        if (isFinish1)
            easy.setText("简单已完成");
        else
            easy.setText("简单");
        if (isFinish2)
            common.setText("普通已完成");
        else
            common.setText("普通");
        if (isFinish3)
            difficult.setText("困难已完成");
        else
            difficult.setText("困难");
        if (isFinish4)
            shudu1.setText("数独简单已完成");
        else
            shudu1.setText("数独简单");
        if (isFinish5)
            shudu2.setText("数独一般已完成");
        else
            shudu2.setText("数独一般");
        if (isFinish6)
            shudu3.setText("数独困难已完成");
        else
            shudu3.setText("数独困难");
        if (isFinish7)
            shudu4.setText("数独梦幻难已完成");
        else
            shudu4.setText("数独梦幻难");
    }
}
