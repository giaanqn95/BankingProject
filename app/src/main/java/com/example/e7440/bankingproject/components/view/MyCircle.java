package com.example.e7440.bankingproject.components.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.e7440.bankingproject.R;

public class MyCircle extends android.support.v7.widget.AppCompatImageView {

    Paint paint;

    public MyCircle(Context context) {
        super(context);
    }

    public MyCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @SuppressLint("ResourceAsColor")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(R.color.red_400);

        canvas.drawCircle(50, 50, 100, paint);

    }
}
