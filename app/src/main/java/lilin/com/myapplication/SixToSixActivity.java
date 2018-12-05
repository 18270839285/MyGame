package lilin.com.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mvp.rx.retrofit.utils.ToastUtil;
import view.WHTextView;

public class SixToSixActivity extends AppCompatActivity {

    private GridView six;
    private List<Integer> data;
    private List<String> list = new ArrayList<>();
    private MyAdapter myAdapter;
    private int pos = 4;
    private int type;
    private int num;
    private Chronometer timer;
    private CheckBox start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_to_six);
        type = getIntent().getExtras().getInt("type");
        num = type * type - 1;
        Log.e("onCreate: ", "type = " + type + "  num = " + num);
        six = findViewById(R.id.six);
        start = findViewById(R.id.start);
//        reset = findViewById(R.id.reset);
        timer = findViewById(R.id.time);
        six.setNumColumns(type);

        initData();
        initAdapter();
        changeView();
    }

    boolean isCheck = false;
    private void initData() {
        data = new ArrayList<>();
        myAdapter = new MyAdapter();
        for (int i = 0; i < num; i++) {
            getRandom();
            if (i == pos) {
                list.add(" ");
            }
            list.add(data.get(i) + "");
        }

        start.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
                if (isChecked){
                    start.setText("重置");
                    setStart();
                }else {
                    setStop();
                    start.setText("开始");
                    pos = new Random().nextInt(num);
                    list.clear();
                    data.clear();
                    for (int i = 0; i < num; i++) {
                        getRandom();
                        if (i == pos)
                            list.add(" ");
                        list.add(data.get(i) + "");
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setStart() {
        six.setEnabled(true);
        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 60);
        timer.setFormat("0" + String.valueOf(hour) + ":%s");
        timer.start();
    }

    private void setStop(){
        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        timer.stop();
    }

    public int getRandom() {
        int r = (int) (Math.random() * num);
        for (int v : data) {
            if (v == r) {
                return getRandom();
            }
        }
        data.add(r);
        return r;
    }

    private void initAdapter() {
        six.setAdapter(myAdapter);
    }

    private void changeView() {
        six.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isCheck)
                     return;
                if (position == (pos + 1) || position == (pos - 1) || position == (pos + type) || position == (pos - type)) {
                    String m = list.get(position);
                    list.add(position, " ");
                    list.remove(position + 1);
                    list.add(pos, m);
                    list.remove(pos + 1);
                    pos = position;
                } else {
                    Log.e("changeView", "onItemClick: " + "点击无效");
                }
                if (isFinish()) {
                    timer.stop();
                    ToastUtil.showLong("恭喜您成功完成！");
                    six.setEnabled(false);
                    if (type == 3)
                        Main2Activity.isFinish1 = true;
                    if (type == 4)
                        Main2Activity.isFinish2 = true;
                    if (type == 5)
                        Main2Activity.isFinish3 = true;
                }
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean isFinish() {
        for (int i = 0; i < num; i++) {
            if (!list.get(i).equals("" + i))
                return false;
        }
        return true;
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(SixToSixActivity.this).inflate(R.layout.layout_item, parent, false);
                holder.textView = convertView.findViewById(R.id.list_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(list.get(position) + "");
            if (list.get(position).equals(" ")) {
                holder.textView.setBackgroundColor(Color.WHITE);
            } else {
                holder.textView.setBackgroundColor(Color.GRAY);
            }
            return convertView;
        }
    }

    public class ViewHolder {
        WHTextView textView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
