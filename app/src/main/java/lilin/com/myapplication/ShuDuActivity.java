package lilin.com.myapplication;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mvp.rx.retrofit.utils.ToastUtil;
import view.WHTextView;

/**
 * 数独游戏
 */
public class ShuDuActivity extends AppCompatActivity {

    private List<Integer> list = new ArrayList<>();//所有数据
    private List<Integer> newList = new ArrayList<>();//填充数据
    private List<Integer> posList = new ArrayList<>();//空格位置数据
    private GridView gridView;
    private int width;
    private CheckBox start;
    private Chronometer timer;
    private int pos = -1;
    private MyAdapter myAdapter;

    private final int LINE_NUM = 9;

    int type;

    boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shu_du);
        type = getIntent().getExtras().getInt("type");
        gridView = findViewById(R.id.gridView);
        start = findViewById(R.id.start);
        timer = findViewById(R.id.time);
        width = getWindowManager().getDefaultDisplay().getWidth();
        start.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
                if (isChecked) {
                    start.setText("重置");
                    setStart();
                } else {
                    setStop();
                    start.setText("开始");
                    list.clear();
                    newList.clear();
                    posList.clear();
                    pos = -1;
                    n = null;
                    n = new int[LINE_NUM][LINE_NUM];
                    init();
                    myAdapter.notifyDataSetChanged();
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
        init();
        setListener();
    }

    private void setStart() {
        gridView.setEnabled(true);
        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 60);
        timer.setFormat("0" + String.valueOf(hour) + ":%s");
        timer.start();
    }

    private void setStop() {
        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        timer.stop();
    }

    private void init() {
        int[][] shuDu = generateShuDu();
        // 输出结果
        for (int i = 0; i < LINE_NUM; i++) {
            for (int j = 0; j < LINE_NUM; j++) {
                list.add(shuDu[i][j]);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if ((i + 1) % LINE_NUM == 0) {
                for (int j = 0; j < type; j++) {
                    getRandom(i, LINE_NUM - 1);
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (posList.contains(i)) {
                newList.add(0);
            } else {
                newList.add(list.get(i));
            }
        }
    }

    private void getRandom(int i, int num) {
        Random rand = new Random();
        int randNum = rand.nextInt(num + 1) + i - num;
        if (!posList.contains(randNum)) {
            posList.add(randNum);
        } else {
            getRandom(i, num);
        }
    }

    private void setListener() {
        myAdapter = new MyAdapter();
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isCheck)
                    return;
                int m = position % LINE_NUM;
                List<Integer> integerList = new ArrayList<>();
                for (int i = position - m; i < position + LINE_NUM - m; i++) {
                    if (newList.get(i) != 0)
                        integerList.add(newList.get(i));
                }
                int n = position / LINE_NUM;
                for (int i = position - LINE_NUM * n; i < position + LINE_NUM * (LINE_NUM - n); ) {
                    if (!integerList.contains(newList.get(i)))
                        integerList.add(newList.get(i));
                    i = i + LINE_NUM;
                }
                if (posList.contains(position)) {
                    createDialog(integerList);
                    pos = position;
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 存储数字的数组
     */
    private int[][] n = new int[LINE_NUM][LINE_NUM];
    /**
     * 生成随机数字的源数组，随机数字从该数组中产生
     */
    private static int[] num = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    public int[][] generateShuDu() {
        // 生成数字
        for (int i = 0; i < LINE_NUM; i++) {
            // 尝试填充的数字次数
            int time = 0;
            // 填充数字
            for (int j = 0; j < LINE_NUM; j++) {
                // 产生数字
                n[i][j] = generateNum(time);
                // 如果返回值为0，则代表卡住，退回处理
                // 退回处理的原则是：如果不是第一列，则先倒退到前一列，否则倒退到前一行的最后一列
                if (n[i][j] == 0) {
                    // 不是第一列，则倒退一列
                    if (j > 0) {
                        j -= 2;
                        continue;
                    } else {// 是第一列，则倒退到上一行的最后一列
                        i--;
                        j = 8;
                        continue;
                    }
                }
                // 填充成功
                if (isCorret(i, j)) {
                    // 初始化time，为下一次填充做准备
                    time = 0;
                } else { // 继续填充
                    // 次数增加1
                    time++;
                    // 继续填充当前格
                    j--;
                }
            }
        }
        return n;
    }

    /**
     * 是否满足行、列和3X3区域不重复的要求
     *
     * @param row 行号
     * @param col 列号
     * @return true代表符合要求
     */
    private boolean isCorret(int row, int col) {
        return (checkRow(row) & checkLine(col) & checkNine(row, col));
    }

    /**
     * 检查行是否符合要求
     *
     * @param row 检查的行号
     * @return true代表符合要求
     */
    private boolean checkRow(int row) {
        for (int j = 0; j < LINE_NUM - 1; j++) {
            if (n[row][j] == 0) {
                continue;
            }
            for (int k = j + 1; k < LINE_NUM; k++) {
                if (n[row][j] == n[row][k]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查列是否符合要求
     *
     * @param col 检查的列号
     * @return true代表符合要求
     */
    private boolean checkLine(int col) {
        for (int j = 0; j < LINE_NUM - 1; j++) {
            if (n[j][col] == 0) {
                continue;
            }
            for (int k = j + 1; k < LINE_NUM; k++) {
                if (n[j][col] == n[k][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查3X3区域是否符合要求
     *
     * @param row 检查的行号
     * @param col 检查的列号
     * @return true代表符合要求
     */
    private boolean checkNine(int row, int col) {
        // 获得左上角的坐标
        int j = row / 3 * 3;
        int k = col / 3 * 3;
        // 循环比较
        for (int i = 0; i < LINE_NUM - 1; i++) {
            if (n[j + i / 3][k + i % 3] == 0) {
                continue;
            }
            for (int m = i + 1; m < LINE_NUM; m++) {
                if (n[j + i / 3][k + i % 3] == n[j + m / 3][k + m % 3]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 产生1-9之间的随机数字 规则：生成的随机数字放置在数组8-time下标的位置，随着time的增加，已经尝试过的数字将不会在取到
     * 说明：即第一次次是从所有数字中随机，第二次时从前八个数字中随机，依次类推， 这样既保证随机，也不会再重复取已经不符合要求的数字，提高程序的效率
     * 这个规则是本算法的核心
     *
     * @param time
     * 填充的次数，0代表第一次填充
     * @return
     */
    private Random r = new Random();

    private int generateNum(int time) {
        // 第一次尝试时，初始化随机数字源数组
        if (time == 0) {
            for (int i = 0; i < LINE_NUM; i++) {
                num[i] = i + 1;
            }
        }
        // 第10次填充，表明该位置已经卡住，则返回0，由主程序处理退回
        if (time == LINE_NUM) {
            return 0;
        }
        // 不是第一次填充
        // 生成随机数字，该数字是数组的下标，取数组num中该下标对应的数字为随机数字
//		int ranNum = (int) (Math.random() * (9 - time));//j2se
        int ranNum = r.nextInt(LINE_NUM - time);//j2me
        // 把数字放置在数组倒数第time个位置，
        int temp = num[8 - time];
        num[8 - time] = num[ranNum];
        num[ranNum] = temp;
        // 返回数字
        return num[8 - time];
    }

    /**
     * 展示九宫格的适配器
     */
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
                convertView = LayoutInflater.from(ShuDuActivity.this).inflate(R.layout.layout_item, parent, false);
                holder.textView = convertView.findViewById(R.id.list_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (newList.get(position) == 0) {
                holder.textView.setText(" ");
                holder.textView.setBackgroundColor(Color.GRAY);
            } else {
                holder.textView.setText(newList.get(position) + "");
                if (posList.contains(position))
                    holder.textView.setBackgroundColor(Color.GRAY);
                else
                    holder.textView.setBackgroundColor(Color.parseColor("#567890"));
            }

            if (position == pos) {
                holder.textView.setBackgroundColor(Color.WHITE);
            }
            holder.textView.isDraw(width / LINE_NUM);
            if ((position + 1) % 3 == 0) {
                holder.textView.isRight(width / LINE_NUM, true);
            } else {
                holder.textView.isRight(width / LINE_NUM, false);
            }
            if (position % 27 >= 18) {
                holder.textView.isDown(width / LINE_NUM, true);
            } else {
                holder.textView.isDown(width / LINE_NUM, false);
            }

            if (isEquals()) {
                ToastUtil.showLong("恭喜您成功完成！");
                gridView.setEnabled(false);
                finish();
                switch (type){
                    case 4:
                        Main2Activity.isFinish4 = true;
                        break;
                    case 5:
                        Main2Activity.isFinish5 = true;
                        break;
                    case 6:
                        Main2Activity.isFinish6 = true;
                        break;
                    case 7:
                        Main2Activity.isFinish7 = true;
                        break;
                }

            }

            return convertView;
        }
    }

    public class ViewHolder {
        WHTextView textView;
    }

    /**
     * 判断前后两个列表是否一致
     *
     * @return
     */
    private boolean isEquals() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != newList.get(i))
                return false;
        }
        return true;
    }

    /**
     * 底部填充弹框
     *
     * @param integerList
     */
    private void createDialog(final List<Integer> integerList) {
        final List<Integer> data = new ArrayList<>();
        for (int i = 0; i < LINE_NUM; i++) {
            data.add(i + 1);
        }
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog);
        window.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        GridView gridView1 = window.findViewById(R.id.item_grid);
        gridView1.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
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
                    convertView = LayoutInflater.from(ShuDuActivity.this).inflate(R.layout.layout_item, parent, false);
                    holder.textView = convertView.findViewById(R.id.list_item);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.textView.setText(data.get(position) + "");
                if (integerList.contains(data.get(position)))
                    holder.textView.setBackgroundColor(Color.GRAY);
                else
                    holder.textView.setBackgroundColor(Color.WHITE);
                return convertView;
            }
        });

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (integerList.contains(data.get(position))) {

                } else {
                    newList.add(pos, data.get(position));
                    newList.remove(pos + 1);
                    pos = -1;
                    myAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        n = null;
    }
}
