package com.greenumbrellastudio.android.aituker.home;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by songjiekun on 16/1/30.
 * 用于控制slider下方的白点
 */
public class HomePlaceHolderSliderAdapter extends PagerAdapter {

    /**
     * placeholder页面数量
     */
    private int mPageCount;

    public HomePlaceHolderSliderAdapter(int pageCount){

        mPageCount = pageCount;

    }

    @Override
    public Object instantiateItem(ViewGroup collection,int position){

        //空页面
        View view = new View(collection.getContext());

        //将页面加入view
        collection.addView(view);

        return  view;

    }

    @Override
    public int getCount() {
        return mPageCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view){

        collection.removeView((View) view);

    }
}
