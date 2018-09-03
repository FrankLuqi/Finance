package com.example.finance.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.finance.R;

/**
 * Created by Administrator on 2018.8.31.
 */

public class RoundProgress extends View{

    private Paint paint = new Paint();

    private int roundColor;
    private int roundProgressColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private int max = 100;

    private int progress = 50;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取在布局中设置的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        //圆环的颜色
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.RED);
        //圆环进度的颜色
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.GREEN);
        //中间进度百分比文字字符串的颜色
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        //中间进度百分比的字符串的字体大小
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, 15);
        //圆环的宽度
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, 5);
        //回收TypedArray 释放内存
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //一、绘制圆环
        paint.setColor(roundColor);
        //设置无锯齿
        paint.setAntiAlias(true);
        //设置空心
        paint.setStyle(Paint.Style.STROKE);
        //设置圆环宽度
        paint.setStrokeWidth(roundWidth);
        int center = getWidth()/2;
        int radis = (int)(center-roundWidth/2);
        canvas.drawCircle(center,center,radis,paint);

        //二、绘制文本
        float textWidth = paint.measureText(progress+"%");
        paint.setColor(textColor);
        //设置线宽
        paint.setStrokeWidth(0);
        paint.setTextSize(textSize);
        canvas.drawText(progress+"%",center-textWidth/2,center+textSize/2,paint);


        //三、绘制圆弧
        /**
         * 参数解释
         * ovak：包含所绘制的圆环的矩形的轮廓
         * 0：开始的角度
         * 360*progress/max：扫过的角度
         * false ： 是否包含圆心
         */
        RectF oval = new RectF(center-radis,center-radis,center+radis,center+radis);
        paint.setColor(roundProgressColor);
        paint.setStrokeWidth(roundWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval,0,360*progress/max,false,paint);
    }


    public void setProgress(int progress)
    {
        if (0<=progress&&progress<=100)
            this.progress = progress;
        else if (progress>100)
            this.progress = progress;
        //重画
        postInvalidate();
    }
}
