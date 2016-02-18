package com.greenumbrellastudio.android.aituker.imagebrowser;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenumbrellastudio.android.aituker.R;
import com.greenumbrellastudio.android.aituker.view.gallery.TouchImageView;

/**
 * Created by songjiekun on 16/2/18.
 */
public class ImageBrowserAdapter extends PagerAdapter {

    @Override
    public Object instantiateItem(ViewGroup collection,int position){

        //获取数据
        //Topic topic = mTopics.get(position);

        //获取页面
        View view = LayoutInflater.from(collection.getContext()).inflate(R.layout.cell_image_browser,collection,false);

        //设置页面
        TouchImageView imageBrowserImageView = (TouchImageView) view.findViewById(R.id.imageBrowserImageView);

        if (position==1 || position ==5){

            imageBrowserImageView.setImageResource(R.drawable.pict);

        }
        else {

            imageBrowserImageView.setImageResource(R.drawable.shoe);

        }

        //将页面加入view
        collection.addView(view);

        return  view;

    }

    @Override
    public int getCount() {
        return 6;
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
