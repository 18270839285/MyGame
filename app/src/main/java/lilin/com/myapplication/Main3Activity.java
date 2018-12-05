package lilin.com.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import java.io.InputStream;

public class Main3Activity extends AppCompatActivity {
    private static int UP_TO_DOWN = 2;
    private static int RIGHT_TO_LEFT = 1;
    private static int NORMAL = 0;
    RecyclerView recyclerView;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        initAnimation(NORMAL);
    }
    private void initView(){
        image = findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.buildDrawingCache();
//                Bitmap bmap = image.getDrawingCache();
//                Bitmap mBitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
//                image.setImageBitmap(zoomBitmap(bmap,2*x,y));
            }
        });

    }
    AnimationDrawable animation1;
    AnimationDrawable animation2;
    private void initAnimation(int type){
//        if (type==UP_TO_DOWN){
            image.setImageResource(R.drawable.animlis);
        AnimationDrawable animation1 = (AnimationDrawable) image.getDrawable();
            animation1.start();
//        }else {
//            image.setImageResource(R.drawable.animation_up);
//            animation2 = (AnimationDrawable) image.getDrawable();
//            animation2.start();
//        }
    }

    /**
     * 按宽/高缩放图片到指定大小并进行裁剪得到中间部分图片 <br>
     * 方 法 名：zoomBitmap <br>
     * 创 建 人：楼翔宇 <br>
     * 创建时间：2018-4-7 下午12:02:52 <br>
     * 修 改 人： <br>
     * 修改日期： <br>
     *
     * @param bitmap 源bitmap
     * @param vw     缩放后指定的宽度
     * @param vh     缩放后指定的高度
     * @return 缩放后的中间部分图片 Bitmap
     */
    public  Bitmap zoomBitmap(Bitmap bitmap, float vw, float vh) {
        Log.e("Bitmap", "zoomBitmap: bitmap = "+bitmap );
        float width = bitmap.getWidth();//获得图片宽高
        float height = bitmap.getHeight();

        float scaleWidht, scaleHeight, x, y;//图片缩放倍数以及x，y轴平移位置
        Bitmap newbmp = null; //新的图片
        Matrix matrix = new Matrix();//变换矩阵
        if ((width/height)<=vw/width){//当宽高比大于所需要尺寸的宽高比时以宽的倍数为缩放倍数
            scaleWidht = vw / width;
            scaleHeight = scaleWidht;
            y = ((height*scaleHeight - vh) / 2)/scaleHeight;// 获取bitmap源文件中y做表需要偏移的像数大小
            x = 0;
        }else {
            scaleWidht = vh / height;
            scaleHeight = scaleWidht;
            x = ((width*scaleWidht -vw ) / 2)/scaleWidht;// 获取bitmap源文件中x做表需要偏移的像数大小
            y = 0;
        }
        matrix.postScale(scaleWidht / 1f, scaleHeight / 1f);
        try {
            if (width - x > 0 && height - y > 0&&bitmap!=null)//获得新的图片 （原图，x轴起始位置，y轴起始位置，x轴结束位置，Y轴结束位置，缩放矩阵，是否过滤原图）为防止报错取绝对值
                newbmp = Bitmap.createBitmap(bitmap, (int) Math.abs(x), (int) Math.abs(y), (int) Math.abs(width - x),
                        (int) Math.abs(height - y), matrix, false);// createBitmap()方法中定义的参数x+width要小于或等于bitmap.getWidth()，y+height要小于或等于bitmap.getHeight()
        } catch (Exception e) {//如果报错则返回原图，不至于为空白
            e.printStackTrace();
            return bitmap;
        }
        return newbmp;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX()<x){
//                    animation2.stop();
                    animation1.start();
                }
                if (event.getY()<y){
                    animation1.stop();
//                    animation2.start();
                }
                break;
            case MotionEvent.ACTION_UP:
                animation1.stop();
//                animation2.stop();
                break;
            case MotionEvent.ACTION_CANCEL:
                animation1.stop();
//                animation2.stop();
                break;

        }
        return super.onTouchEvent(event);
    }
}
