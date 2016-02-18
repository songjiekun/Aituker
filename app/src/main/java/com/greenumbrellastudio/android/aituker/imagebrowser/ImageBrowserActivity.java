package com.greenumbrellastudio.android.aituker.imagebrowser;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.greenumbrellastudio.android.aituker.R;
import com.greenumbrellastudio.android.aituker.view.gallery.ExtendedViewPager;

public class ImageBrowserActivity extends FragmentActivity {

    private ExtendedViewPager mImageBrowserViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browser);

        //配置views
        setupViews();

    }

    /**
     * 配置views
     */
    private void setupViews(){

        mImageBrowserViewpager=(ExtendedViewPager)findViewById(R.id.imageBrowserViewpager);

        //设置 imagebrowseradapter
        ImageBrowserAdapter sliderAdapter = new ImageBrowserAdapter();
        mImageBrowserViewpager.setAdapter(sliderAdapter);

        //获取传递参数
        Bundle extras = getIntent().getExtras();

        if (extras!=null){

            int pageNumber = extras.getInt("Page_No");
            //设置页面位置
            mImageBrowserViewpager.setCurrentItem(pageNumber,false);

        }


    }

}
