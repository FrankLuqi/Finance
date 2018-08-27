package com.example.finance.UI;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2018.8.24.
 */

public class MyScrollview extends ScrollView{

    private static final String TAG = "MyScrollview";

    //要操作的布局
    private View innerView = null;
    //用y来保存移动过程中的y轴坐标中间变量
    private float y;
    //保存下滑之前布局的位置，方便回滚
    private Rect normal = new Rect();
    //判断当前回滚是否结束，回滚结束前不能再下拉
    private boolean animationFinish = true;

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //当scrollview中所有元素加载完之后调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取自定义滚动条内的内容
        int childCount = getChildCount();
        if (childCount>0)
            innerView = getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (innerView == null)
        {
            return super.onTouchEvent(ev);
        }
        else
        {
            commonTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 自定义touch事件处理
     * @param ev
     */

    private void commonTouchEvent(MotionEvent ev) {
        if (animationFinish)
        {
            int action = ev.getAction();
            switch(action)
            {
                case MotionEvent.ACTION_DOWN:
                    y = ev.getY();
    //                Log.d(TAG, "y: "+y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //此处需要理解MotionEvent.ACTION_MOVE究竟是怎样的调用过程
                    //如果实在不能理解可以将preY和nowY和detailY打印出来
                    float preY = y == 0 ? ev.getY() : y;
                    float nowY = ev.getY();
                    int detailY = (int) (preY - nowY);
                    y = nowY;
    //                Log.d(TAG, "preY:"+preY+" nowY:"+nowY+" y"+y+" detailY: "+detailY);
                    if (isNeedMove())
                    {
                        if (normal.isEmpty())
                        {
                            normal.set(innerView.getLeft(),innerView.getTop(),innerView.getRight(),innerView.getBottom());
                        }
                        innerView.layout(innerView.getLeft(),innerView.getTop()-detailY/2,innerView.getRight(),innerView.getBottom()-detailY/2);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    y = 0;
    //                Log.d(TAG, "y"+y);
                    if (isNeedAnimation())
                    {
                        animation();
                    }
                    break;
            }
        }
    }


    private void animation() {
        TranslateAnimation ta = new TranslateAnimation(0,0,0,normal.top-innerView.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                innerView.clearAnimation();
                innerView.layout(normal.left,normal.top,normal.right,normal.bottom);
                normal.setEmpty();
                animationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        innerView.startAnimation(ta);
    }

    /**
     * 判断是否需要调用回滚动画
     * @return
     */
    private boolean isNeedAnimation() {
        if (normal.isEmpty())
            return false;
        else
            return true;
    }

    /**
     * 判断是否需要进行滚动
     * @return
     */

    private boolean isNeedMove()
    {
        //组件测量高度减屏幕高度得到组件超出屏幕的高度
        int offset = innerView.getMeasuredHeight()-innerView.getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0||scrollY==offset)
        {
            return true;
        }
        return false;
    }

}
