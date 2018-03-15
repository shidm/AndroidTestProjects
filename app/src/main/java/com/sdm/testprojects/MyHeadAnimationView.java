package com.sdm.testprojects;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by shidongming on 18-3-14.
 */

public class MyHeadAnimationView extends View {

    float parentLeft, parentRight, parentTop, parentBottom;
    float fristLinesStartX, firstLinesStartY, firstLinesEndX, firstLinesEndY;
    float secondLinesStartX, secondLinesStartY, secondLinesEndX, secondLinesEndY;
    float paddingRectLeft, paddingRectRight, paddingRectTop, paddingRectBottom;
    float paddingRectMoveX, paddingRectMoveY;
    float firstLinesMoveX, firstLinesMoveY;
    float secondLinesMoveX, secondLinesMoveY;
    float centerY;

    int speed = 10;
    int count = 0;

    private static final int POSITION_ONE = 0;
    private static final int POSITION_TWO = 1;
    private static final int POSITION_THREE = 2;
    private static final int POSITION_FOUR = 3;

    private static boolean isNeedInit = true;

    private int thisStatus = POSITION_ONE;

    public MyHeadAnimationView(Context context) {
        super(context);
    }

    public MyHeadAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHeadAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyHeadAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(boolean isNeedInitailize) {
        if (isNeedInitailize) {
            Log.d("init: ", String.valueOf(getWidth()));
            parentLeft = 20;
            parentTop = 20;
            parentRight = getWidth() - 20;
            parentBottom = getHeight() - 20;

            paddingRectLeft = 40;
            paddingRectTop = 40;
            paddingRectRight = getWidth() / 2 - 20;
            paddingRectBottom = getHeight() / 2 - 20;

            fristLinesStartX = getWidth() / 2 + 20;
            firstLinesStartY = 40;
            firstLinesEndX = getWidth() - 40;
            firstLinesEndY = 40;

            centerY = (getHeight() / 2 - 60) / 2;

            secondLinesStartX = 40;
            secondLinesStartY = getWidth() / 2 + 20;
            secondLinesEndX = getWidth() - 40;
            secondLinesEndY = getWidth() / 2 + 20;

            isNeedInit = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        long startTime = System.currentTimeMillis();
        init(isNeedInit);
        myDraw(canvas);

        update(canvas);

        Log.d("onDraw ", String.valueOf(System.currentTimeMillis() - startTime));
        postInvalidate();
    }

    public void myDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);

        RectF rect = new RectF(parentLeft, parentTop, parentRight, parentBottom);
        canvas.drawRoundRect(rect, 30, 30, paint);

        canvas.save();
        RectF rect1 = new RectF(paddingRectLeft, paddingRectTop, paddingRectRight, paddingRectBottom);
        canvas.translate(paddingRectMoveX, paddingRectMoveY);
        canvas.drawRoundRect(rect1, 10, 10, paint);
        canvas.restore();

        canvas.save();
        float[] firstLinesXY = {
                fristLinesStartX, firstLinesStartY, firstLinesEndX, firstLinesEndY,
                fristLinesStartX, firstLinesStartY + centerY, firstLinesEndX, firstLinesStartY + centerY,
                fristLinesStartX, firstLinesStartY + centerY * 2, firstLinesEndX, firstLinesStartY + centerY * 2,
        };
        paint.setStrokeWidth(5);
        canvas.translate(firstLinesMoveX, firstLinesMoveY);
        canvas.drawLines(firstLinesXY, paint);
        canvas.restore();

        canvas.save();
        float[] secondLinesXY = {
                secondLinesStartX, secondLinesStartY, secondLinesEndX, secondLinesEndY,
                secondLinesStartX, secondLinesStartY + centerY, secondLinesEndX, secondLinesStartY + centerY,
                secondLinesStartX, secondLinesStartY + centerY * 2, secondLinesEndX, secondLinesStartY + centerY * 2,
        };
        paint.setStrokeWidth(5);
        canvas.translate(secondLinesMoveX, secondLinesMoveY);
        canvas.drawLines(secondLinesXY, paint);
        canvas.restore();
    }

    private void update(Canvas canvas) {
        switch (thisStatus) {
            case POSITION_ONE:
                if (paddingRectMoveX < (getWidth() / 2 - 20)) {

                    // 1,2线移动
                    paddingRectMoveX += speed;
                    //第一组线移动，并且改变长度
                    firstLinesMoveY += speed;
                    fristLinesStartX -= speed;

                    //第二组线移动，并且改变长度
                    secondLinesMoveY -= speed;
                    secondLinesEndX -= speed;
                } else {
                    if (count <= 5) {
                        count++;
                    } else {
                        count = 0;
                        thisStatus = POSITION_TWO;
                    }
                }
                break;
            case POSITION_TWO:
                if (paddingRectMoveY < (getHeight() / 2 - 20)) {

                    //1,2线改变长度
                    paddingRectMoveY += speed;
                    firstLinesEndX -= speed;
                    secondLinesEndX += speed;
                } else {
                    if (count <= 5) {
                        count++;
                    } else {
                        count = 0;
                        thisStatus = POSITION_THREE;
                    }
                }
                break;
            case POSITION_THREE:
                if (paddingRectMoveX > 0) {
                    // 1,2线位移
                    paddingRectMoveX -= speed;

                    //第一组线移动，并且改变长度
                    firstLinesMoveY -= speed;
                    firstLinesEndX += speed;

                    //第二组线移动，并且改变长度
                    secondLinesMoveY += speed;
                    secondLinesStartX += speed;
                } else {
                    if (count <= 5) {
                        count++;
                    } else {
                        count = 0;
                        thisStatus = POSITION_FOUR;
                    }
                }
                break;
            case POSITION_FOUR:
                if (paddingRectMoveY > 0) {
                    // 1,2线变长
                    paddingRectMoveY -= speed;

                    fristLinesStartX += speed;
                    secondLinesStartX -= speed;
                } else {
                    if (count <= 5) {
                        count++;
                    } else {
                        count = 0;
                        thisStatus = POSITION_ONE;
                    }
                }
                break;
            default:
                break;
        }
    }
}
