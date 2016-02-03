package com.greenumbrellastudio.android.aituker.home;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenumbrellastudio.android.aituker.R;
import com.greenumbrellastudio.android.aituker.model.Topic;
import com.greenumbrellastudio.android.aituker.view.TopicPreviewImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songjiekun on 16/1/26.
 */
public class HomeSliderAdapter extends PagerAdapter{

    /**
     * topic列表
     */
    private List<Topic> mTopics;

    /**
     * 点击slider接口
     */
    private View.OnClickListener mOnClickSliderListener;

    public void setOnClickSliderListener(View.OnClickListener onClickSliderListener){

        mOnClickSliderListener = onClickSliderListener;

    }

    public HomeSliderAdapter(){

        super();

        mTopics = new ArrayList<Topic>();

        Topic topic = new Topic("世界十大奇观","","",1,"11月28日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"11月27日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"11月26日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"12月5日");
        mTopics.add(topic);

        topic = new Topic("自然之美","","",1,"12月4日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"12月3日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"12月2日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"12月1日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"11月31日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"11月30日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"11月29日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"11月28日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"11月27日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"11月26日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"12月5日");
        mTopics.add(topic);

        topic = new Topic("自然之美","","",1,"12月4日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"12月3日");
        mTopics.add(topic);

    }

    @Override
    public Object instantiateItem(ViewGroup collection,int position){

        //获取数据
        Topic topic = mTopics.get(position);

        //获取页面
        View view = LayoutInflater.from(collection.getContext()).inflate(R.layout.cell_slider_home,collection,false);

        //设置点击处理代理
        view.setTag(position);//传递位置信息
        view.setOnClickListener(mOnClickSliderListener);

        //设置页面
        TopicPreviewImageView sliderImageView = (TopicPreviewImageView) view.findViewById(R.id.sliderImageView);

        TextView textView = (TextView) view.findViewById(R.id.sliderTextView);
        textView.setText(topic.getmPublishedDate());
        // TODO: 16/1/26 设置slider的内容

        //将页面加入view
        collection.addView(view);

        return  view;

    }

    @Override
    public int getCount() {
        return mTopics.size();
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
