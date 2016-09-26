package com.charles.expandablerelativelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

/**
 * Author: Charles_lun
 * Email:clun@gdeng.cn
 * Time:2016/9/23 13:43
 * Package_name:project.charles.com.flowlayout
 * Description:
 */
public class ExpandableRelativeLayout extends RelativeLayout {
    private final int DEFAULT_DURATION = 350;
    private final boolean DEFAULT_ISEXPANDABLE = true;
    private int duration;
    private Context mContext;
    int parentWidthMeasureSpec;
    int parentHeightMeasureSpec;
    int measureHeight, measureWidth;
    boolean isExpandable;

    public ExpandableRelativeLayout(Context context) {
        this(context, null);
    }

    public ExpandableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableRelativeLayout, defStyleAttr, 0);
        duration = a.getInteger(R.styleable.ExpandableRelativeLayout_duration, DEFAULT_DURATION);
        isExpandable = a.getBoolean(R.styleable.ExpandableRelativeLayout_expandable, DEFAULT_ISEXPANDABLE);
        a.recycle();
        this.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    private void collapse(final View view) {
        view.measure(parentWidthMeasureSpec, parentHeightMeasureSpec);
        measureWidth = getMeasuredWidth();
        measureHeight = getMeasuredHeight();
        Log.e("mess", "collapse------------" + measureHeight+",height:"+getHeight());
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.getLayoutParams().height = 0;
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = (int) ((1 - interpolatedTime) * measureHeight);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
        isExpandable = false;
    }

    private void expand(final View view) {
        view.measure(parentWidthMeasureSpec, parentHeightMeasureSpec);
        if (measureHeight == 0) {
            measureHeight = getMeasuredHeight();
        }
        Log.e("mess", "expand------------" + measureHeight+",height:"+getHeight());
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.getLayoutParams().height = measureHeight;
                } else {
                    view.getLayoutParams().height = (int) (measureHeight * interpolatedTime);
                    view.setVisibility(View.VISIBLE);
                }
                view.requestLayout();

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
        isExpandable = true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidthMeasureSpec = widthMeasureSpec;
        parentHeightMeasureSpec = heightMeasureSpec;
        Log.e("mess", "onMeasure........");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("mess", "onLayout........"+getMeasuredHeight());
    }


    public void toggle() {
        if (isExpandable) {
            collapse(this);
        } else {
            expand(this);
        }
    }


}
