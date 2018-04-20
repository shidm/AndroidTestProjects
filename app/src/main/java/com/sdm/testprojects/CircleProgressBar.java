package com.sdm.testprojects;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sdm.testprojects.R;

import static android.graphics.Color.BLUE;

/**
 * Created by shidongming on 18-3-9.
 */

public class CircleProgressBar extends View {

    private int offset = 0;
    private int mOffset = 45;
    private boolean isProduce = false;

    private int roundColor, roundBackground;
    private float roundWidth;

    private float headX,headY,endX,endY;

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        roundColor = typedArray.getColor(R.styleable.CircleProgressBar_roundColor, Color.BLUE);
        roundBackground = typedArray.getColor(R.styleable.CircleProgressBar_roundBackground, Color.WHITE);
        roundWidth = typedArray.getDimension(R.styleable.CircleProgressBar_roundWidth, 9);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        float center = getWidth()/2; //获取圆心的x坐标
        float radius = center - roundWidth/2; //圆环的半径
        paint.setColor(roundBackground); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
//        paint.setDither(true);
        canvas.drawCircle(center, center, radius, paint); //画出圆环

        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundColor);
        RectF oval = new RectF(center - radius, center - radius,
                center + radius, center + radius);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, offset, mOffset, false, paint);

        //绘制进度环开头的小圆点
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(roundColor);
        //使用三角函数时，需要把角度转为弧度
        headX = center + (int)(radius * Math.cos((double)offset/180 * Math.PI));
        endX = center + (int)(radius * Math.cos((double) (offset + mOffset)/180 * Math.PI));
        //Log.e("degree", "degree: " + rotateDegree + "cos: " + Math.cos((double)rotateDegree/180 * Math.PI));
        headY = center + (int)(radius * Math.sin((double)offset/180 * Math.PI));
        endY = center + (int)(radius * Math.sin((double) (offset + mOffset)/180 * Math.PI));
        canvas.drawCircle(headX, headY, roundWidth/ 2, paint);
        canvas.drawCircle(endX, endY, roundWidth/ 2, paint);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (offset < 360) {
                    offset += 5;
                } else {
                    offset = 5;
                }

//                if (mOffset == 360) {
//                    isProduce = true;
//                } else if (mOffset == 0){
//                    isProduce = false;
//                }
//
//                if (mOffset > 0 && isProduce) {
//                    mOffset -=5;
//                }else if (mOffset < 360 && !isProduce) {
//                    mOffset += 5;
//                }
                postInvalidate();
            }
        },10);
    }
}
