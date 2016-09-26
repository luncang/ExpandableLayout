package project.charles.com.flowlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: Charles_lun
 * Email:clun@gdeng.cn
 * Time:2016/9/22 16:00
 * Package_name:project.charles.com.flowlayout
 * Description:
 */
public class MyFlowLayout extends ViewGroup {
    private int verticalSpacing = 20;

    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int childStartLayoutX = paddingLeft;
        int childStartLayoutY = paddingTop;
        int widthUsed = paddingLeft + paddingRight;
        int childMaxHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int childNeededWidth, childNeedHeight;
                int left, right, top, bottom;
                int childMeasuredWidth = child.getMeasuredWidth();
                int childMeasuredHeight = child.getMeasuredHeight();
                LayoutParams childLayoutParams = child.getLayoutParams();
                MarginLayoutParams childMarginLayoutParams = (MarginLayoutParams) childLayoutParams;
                int childLeftMargin = childMarginLayoutParams.leftMargin;
                int childRightMargin = childMarginLayoutParams.rightMargin;
                int chidlTopMargin = childMarginLayoutParams.topMargin;
                int childBottomMargin = childMarginLayoutParams.bottomMargin;

                childNeededWidth = childMeasuredWidth + childLeftMargin + childRightMargin;
                childNeedHeight = childMeasuredHeight + chidlTopMargin + childBottomMargin;

                if (widthUsed + childNeededWidth < r - l) {
                    if (childNeedHeight > childMaxHeight) {
                        childMaxHeight = childNeedHeight;
                    }
                    left = childStartLayoutX + childLeftMargin;
                    right = left + childMeasuredWidth;
                    top = childStartLayoutY + chidlTopMargin;
                    bottom = top + childMeasuredHeight;
                    widthUsed += childNeededWidth;
                    childStartLayoutX += childNeededWidth;
                } else {
                    childStartLayoutX = paddingLeft;
                    childStartLayoutY += childMaxHeight + verticalSpacing;
                    widthUsed = paddingLeft + paddingRight;
                    left = childStartLayoutX + childLeftMargin;
                    right = left + childMeasuredWidth;
                    top = childStartLayoutY + chidlTopMargin;
                    bottom = top + childMeasuredHeight;

                    widthUsed += childNeededWidth;
                    childStartLayoutX += childNeededWidth;
                    childMaxHeight = childNeedHeight;
                }
                child.layout(left, top, right, bottom);
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureAll(widthMeasureSpec, heightMeasureSpec);
    }

    private void measureAll(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int widthUsed = paddingLeft + paddingRight;
        int heightUsed = paddingBottom + paddingTop;
        //本行子view最大高度
        int childMaxHeightOfThisLine = 0;
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int childUsedWidth = 0;
                int childUsedHeight = 0;
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                childUsedWidth += child.getMeasuredWidth();
                childUsedHeight += child.getMeasuredHeight();
                LayoutParams childLayoutParams = child.getLayoutParams();
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childLayoutParams;
                childUsedWidth += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                childUsedHeight += marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;

                //不需要换行
                if (widthUsed + childUsedWidth < widthSize) {
                    widthUsed += childUsedWidth;
                    //是否要更新改行的高
                    if (childUsedHeight > childMaxHeightOfThisLine) {
                        childMaxHeightOfThisLine = childUsedHeight;
                    }
                } else {//换行
                    //增加view控件的高
                    heightUsed += childMaxHeightOfThisLine + verticalSpacing;
                    //重新计算已使用的宽
                    widthUsed = paddingLeft + paddingRight + childUsedWidth;
                    //换行后第一个控件的高即为行高
                    childMaxHeightOfThisLine = childUsedHeight;
                }

            }
        }
        //view的行高=最后一行之前的高度+最后一行最高的控件的高度
        heightUsed += childMaxHeightOfThisLine;
        //最后设置dimension
        setMeasuredDimension(widthMeasureSpec, heightUsed);
    }

}
