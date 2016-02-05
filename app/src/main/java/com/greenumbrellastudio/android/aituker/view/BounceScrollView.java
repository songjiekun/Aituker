package com.greenumbrellastudio.android.aituker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;

/**
 * Created by songjiekun on 16/2/4.
 */
public class BounceScrollView extends ScrollView {

    //拖动到边缘后调用的接口
    public interface OnScrollToEdageListener {

        void onScrollToEdage();

    }

    private OnScrollToEdageListener mOnScrollToEdageListener;

    public void setOnScrollToEdageListener(OnScrollToEdageListener onScrollToEdageListener){

        mOnScrollToEdageListener = onScrollToEdageListener;

    }

    private Context mContext;
    private int mMaxOverScrollDistance = 100;
    /**
     * 横向超拖动距离
     */
    private int mOverScrollX;
    /**
     * 纵向超拖动距离
     */
    private int mOverScrollY;

    public void setMaxOverScrollDistance(int maxDistance){

        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;

        mMaxOverScrollDistance = (int)(density*maxDistance);

    }

    public BounceScrollView(Context context) {
        super(context);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /*
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (clampedY){

            int i = 1+1;

            if (clampedY && mOverScrollY<=-mMaxOverScrollDistance/3*2){

                if (mScrollToEdage == null && clampedY) {

                    //调用代理
                    mScrollToEdage.onScrollToEdage();

                }

            }

        }




    }
*/

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
    {

        //拖动距离除以2 是的拖动有阻碍感
        mOverScrollX = scrollX+deltaX/2;
        mOverScrollY = scrollY+deltaY/2;

        //当拖动到3分之2的距离时 并且手指移开了 就调用代理
        if (mOverScrollY<=-mMaxOverScrollDistance/3*2 && !isTouchEvent){

            if (mOnScrollToEdageListener != null) {

                //调用代理
                mOnScrollToEdageListener.onScrollToEdage();

            }

        }

        //产生overscroll效果
        return super.overScrollBy(deltaX/2, deltaY/2, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverScrollDistance, isTouchEvent);
    }


}
