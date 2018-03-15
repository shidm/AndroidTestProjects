package com.sdm.testprojects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shidongming on 18-3-13.
 */

public class MyCricleErrorView extends View {
    public MyCricleErrorView(Context context) {
        super(context);
    }

    public MyCricleErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCricleErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyCricleErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = 5.0f;
        Paint p = new Paint();
        p.setDither(true);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);

        float x = getWidth() / 2;
        float y = x;
        float raduio = getWidth() / 2 - width / 2;

        canvas.drawCircle(x, y, raduio, p);

        float lineX = getWidth() / 3;
        float lineY = getWidth() / 3;

        p.setColor(getResources().getColor(R.color.grayAl));
        p.setStrokeWidth(9);
        canvas.drawLine(lineX, lineY, lineX * 2, lineY * 2, p);
        canvas.drawLine(lineX, lineY * 2, lineX * 2, lineY, p);
    }
}
