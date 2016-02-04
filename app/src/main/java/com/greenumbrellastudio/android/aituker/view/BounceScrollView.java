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
    interface ScrollToEdage {

        void onScrollToEdage();

    }

    private ScrollToEdage mScrollToEdage;

    public void setScrollToEdage(ScrollToEdage scrollToEdage){

        mScrollToEdage = scrollToEdage;

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
        mOverScrollX = scrollX+deltaX;
        mOverScrollY = scrollY+deltaY;

        //当拖动到3分之2的距离时 并且手指移开了 就调用代理
        if (mOverScrollY<=-mMaxOverScrollDistance/3*2 && !isTouchEvent){

            if (mScrollToEdage == null) {

                //调用代理
                mScrollToEdage.onScrollToEdage();

            }

        }

        //产生overscroll效果
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverScrollDistance, isTouchEvent);
    }


}
