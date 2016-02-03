package com.greenumbrellastudio.android.aituker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.greenumbrellastudio.android.aituker.home.HomeFragment;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //配置tab
        setupTabs();

    }

    /**
     * 配置tab
     */
    private void setupTabs(){

        mTabHost =(FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("首页").setIndicator(getTabView(mTabHost.getContext(), android.R.drawable.star_on)), HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("收藏").setIndicator(getTabView(mTabHost.getContext(), android.R.drawable.star_on)),FavoriteFragment.class,null);
        mTabHost.addTab(mTabHost.newTabSpec("个人设置").setIndicator(getTabView(mTabHost.getContext(), android.R.drawable.star_off)),HomeFragment.class,null);

        //设置tab的监听代理
        mTabHost.setOnTabChangedListener(this);

    }

    /**
     * 获取tab对应的view
     * @param context 环境变量
     * @param icon 图标
     * @return tab对应的view
     */
    private View getTabView(Context context,int icon){

        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout,null);
        ImageView iconView=(ImageView)view.findViewById(R.id.tabimage);
        iconView.setImageResource(icon);
        iconView.getLayoutParams().height=50;
        iconView.getLayoutParams().width=50;
        return view;

    }

    /**
     * tab变化时调用
     * @param tabId
     */
    @Override
    public void onTabChanged(String tabId) {

        //背景全部为没有选中
        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++){

            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(android.R.drawable.screen_background_light);

        }

        //选中的背景为暗色
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundResource(android.R.drawable.bottom_bar);

    }
}
