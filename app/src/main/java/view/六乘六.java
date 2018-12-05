package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:
 * Data：2018/10/25-16:42
 * Author: lin
 */
public class 六乘六 extends View {

    private Paint mPaint;
    private Paint tPaint;
    private float mWidth = 1080;
    private List<String> list = new ArrayList<>();
//    private float mHeight = 1080;


    public 六乘六(Context context) {
        super(context);
    }

    public 六乘六(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public 六乘六(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public 六乘六(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.e( "onMeasure: ", "widthMeasureSpec = "+widthMeasureSpec+"  heightMeasureSpec = "+heightMeasureSpec);
//        mWidth = widthMeasureSpec;
//        mWidth = (float)(Math.round(mWidth*100))/100;
//        mHeight = (float)(0.5 * heightMeasureSpec);
//        mHeight = (float)(Math.round(mHeight*100))/100;
        Log.e( "onMeasure: ", "mWidth = "+mWidth);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        tPaint = new Paint();
        tPaint.setTextSize(40);
        mPaint.setTextSize(5);
        mPaint.setColor(Color.BLACK);
        tPaint.setColor(Color.BLACK);
        for (int i = 0;i<7;i++){
            canvas.drawLine(mWidth*i/6,0,(mWidth*i/6),mWidth,mPaint);
        }
        for (int i = 0;i<7;i++){
            canvas.drawLine(0,mWidth*i/6,mWidth,mWidth*i/6,mPaint);
        }
        list.addAll(getText());
        int k = 0;
        for (int i = 0; i<6 ; i++){
            for (int j = 0;j<6;j++){
                if (i==j&&j==5){

                }else {
                    canvas.drawText(list.get(k),mWidth*i/6+66,mWidth*j/6+110,tPaint);
                    k ++;
                }
            }
        }
    }

    private List<String> getText(){
        Random random = new Random();
        return list;
    }
}
