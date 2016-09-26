package project.charles.com.flowlayout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Author: Charles_lun
 * Email:clun@gdeng.cn
 * Time:2016/9/23 13:43
 * Package_name:project.charles.com.flowlayout
 * Description:
 */
public class ExpandableRelativeLayout2 extends RelativeLayout  {
    private long duration = 350;
    private Context mContext;
    int parentWidthMeasureSpec;
    int parentHeightMeasureSpec;
    int measureHeight,measureWidth;
    boolean isExpandable = true;

    public ExpandableRelativeLayout2(Context context) {
        this(context, null);
    }

    public ExpandableRelativeLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableRelativeLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;

    }

    private void collapse(final View view) {
        Log.e("mess","collapse------------");
        view.measure(parentWidthMeasureSpec,parentHeightMeasureSpec);
        measureWidth = getMeasuredWidth();
        measureHeight = getMeasuredHeight();
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
        view.setVisibility(View.VISIBLE);
        Log.e("mess","expand------------"+measureHeight);
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.getLayoutParams().height = measureHeight;
                } else {
                    view.getLayoutParams().height = (int) (measureHeight * interpolatedTime);
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
        Log.e("mess","onMeasure........");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("mess","onLayout........");
    }


    public void toggle(){
        if(isExpandable){
            collapse(this);
        }else{
            expand(this);
        }
    }


}
