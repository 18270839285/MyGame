package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Description:
 * Data：2018/10/29-9:58
 * Author: lin
 */
public class WHTextView extends AppCompatTextView {

    Paint paint;
    Paint paint2;

    public WHTextView(Context context) {
        super(context);
    }

    public WHTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WHTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint2 = new Paint();
//        paint.setTextSize(6);
        paint.setStrokeWidth(6);
        paint.setColor(Color.BLUE);
//        paint2.setTextSize(2);
        paint.setStrokeWidth(2);
        paint2.setColor(Color.BLACK);
        if (isChange2) {
            canvas.drawLine(width -1 , 0, width-1 , width , paint2);
            canvas.drawLine(0, width , width, width, paint2);
        }
        if (isChange1)
            canvas.drawLine(width - 1, 0, width - 1, width, paint);
        if (isChange)
            canvas.drawLine(0, width - 1 , width, width - 1, paint);
    }

    boolean isChange = false;//是否画下边线
    boolean isChange1 = false;//是否画右边线
    boolean isChange2 = false;
    int width;

    public void isDown(int width, boolean isChange) {
        this.isChange = isChange;
        this.width = width;
        invalidate();
    }

    public void isRight(int width, boolean isChange1) {
        this.isChange1 = isChange1;
        this.width = width;
        invalidate();
    }

    public void isDraw(int width) {
        isChange2 = true;
        this.width = width;
        invalidate();
    }

}
