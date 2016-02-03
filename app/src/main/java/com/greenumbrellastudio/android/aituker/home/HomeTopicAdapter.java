package com.greenumbrellastudio.android.aituker.home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenumbrellastudio.android.aituker.R;
import com.greenumbrellastudio.android.aituker.model.Topic;
import com.greenumbrellastudio.android.aituker.view.LoadMoreRecyclerAdapter;
import com.greenumbrellastudio.android.aituker.view.TopicPreviewImageView;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

//import java.util.logging.Handler;

/**
 * Created by songjiekun on 16/1/25.
 */
public class HomeTopicAdapter extends LoadMoreRecyclerAdapter implements ViewPager.OnPageChangeListener {

    /**
     * cell类型
     */
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    /**
     * context
     */
    private Context mContext;


    /**
     * topic列表
     */
    private List<Topic> mTopics;

    public void setTopics(List<Topic> topics){

        mTopics = topics;

    }

    /**
     * viewpager引用
     */
    private ViewPager mSliderViewPager;

    /**
     * viewpager placeholder引用
     */
    private ViewPager mPlaceHolderSliderViewPager;


    private android.os.Handler mHandler = new android.os.Handler();
    private Runnable mAutoSliderRunnable = new Runnable() {
        @Override
        public void run() {

            if (mSliderViewPager != null){

                //当前页面号码
                int currentPage = mSliderViewPager.getCurrentItem();

                //总页数
                int pageCount = mSliderViewPager.getAdapter().getCount();

                if (currentPage >1 && currentPage<pageCount-2){

                    mSliderViewPager.setCurrentItem(currentPage+1,true);

                }

            }

            mHandler.postDelayed(mAutoSliderRunnable,5000);

        }
    };

    /**
     * 停止幻灯片滑动
     */
    public void pauseAutoSlider(){

        mHandler.removeCallbacks(mAutoSliderRunnable);

    }

    /**
     * 重启幻灯片滑动
     */
    public void resumeAutoSlider(){

        mHandler.postDelayed(mAutoSliderRunnable,5000);

    }

    /**
     * 点击item接口
     */
    private View.OnClickListener mOnClickItemListener;

    public void setOnClickItemListener(View.OnClickListener onClickItemListener){

        mOnClickItemListener = onClickItemListener;

    }

    /**
     * 点击slider接口
     */
    private View.OnClickListener mOnClickSliderListener;

    public void setOnClickSliderListener(View.OnClickListener onClickSliderListener){

        mOnClickSliderListener = onClickSliderListener;

    }

    /**
     * 构造函数
     */
    public HomeTopicAdapter(Context context){

        super();

        mContext = context;

        mTopics = new ArrayList<Topic>();
        /**
        Topic topic = new Topic("世界十大奇观","","",1,"12月5日");
        mTopics.add(topic);

        topic = new Topic("自然之美","","",1,"12月4日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"12月3日");
        mTopics.add(topic);

        topic = new Topic("世界十大奇观","","",1,"12月1日");
        mTopics.add(topic);
         **/

        //在对应的fragment的resume中启动handler
        //mHandler.postDelayed(mAutoSliderRunnable,5000);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_ITEM){

            View view = LayoutInflater.from(mContext).inflate(R.layout.cell_home,parent,false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;

        }
        else if(viewType == TYPE_HEADER){

            View view = LayoutInflater.from(mContext).inflate(R.layout.cell_header_home,parent,false);
            HeaderViewHolder viewHolder = new HeaderViewHolder(view);
            return viewHolder;

        }
        else {

            //View view = LayoutInflater.from(mContext).inflate(R.layout.cell_loadmore_home,parent,false);
            //LoadMoreViewHolder viewHolder = new LoadMoreViewHolder(view);
            return super.onCreateViewHolder(parent,viewType);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //item项
        if(holder instanceof ItemViewHolder){

            final ItemViewHolder itemHolder = (ItemViewHolder)holder;

            //绑定topic数据
            final Topic topic = mTopics.get(position-1);//由于header存在 所以需要减掉1

            itemHolder.topicTitleTextView.setText(topic.getmTitle());
            itemHolder.topicDateTextView.setText(topic.getmPublishedDate());

            //作者头像图片载入
            Picasso.with(mContext).load(topic.getmProfileUrl()).fit().centerCrop()
                    .placeholder(R.drawable.profile).error(R.drawable.profile).into(itemHolder.topicProfileImageView);

            //屏幕大小
            WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);

            //显示log和缓存标记
            Picasso.with(mContext).setIndicatorsEnabled(true);
            Picasso.with(mContext).setLoggingEnabled(true);
            //preview图片设置
            Picasso.with(mContext).load(topic.getmPreviewUrl()).resize(metrics.widthPixels-20, 0)
                    .placeholder(R.drawable.pict).error(R.drawable.pict).into(itemHolder.topicPreviewImageView);

            /*
            itemHolder.topicPreviewImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    //保证addOnGlobalLayoutListener只执行一次 添加一次
                    if (Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN){

                        itemHolder.topicPreviewImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    }
                    else {

                        itemHolder.topicPreviewImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    }


                    Picasso.with(mContext).load(topic.getmPreviewUrl()).resize(itemHolder.topicPreviewImageView.getWidth(), 0)
                            .placeholder(R.drawable.pict).error(R.drawable.pict).into(itemHolder.topicPreviewImageView);

                }
            });
            */
        }
        //header项 slider
        else if(holder instanceof HeaderViewHolder){

            //绑定header数据
            HeaderViewHolder itemHolder = (HeaderViewHolder)holder;

            //设置viewpager引用
            mSliderViewPager = itemHolder.sliderViewpager;
            //设置 slider adapter
            HomeSliderAdapter sliderAdapter = new HomeSliderAdapter();
            sliderAdapter.setOnClickSliderListener(mOnClickSliderListener);//设置slider click listener接口
            mSliderViewPager.setAdapter(sliderAdapter);
            //设置listener
            mSliderViewPager.addOnPageChangeListener(this);

            //slider的单边冗余数量
            int sRN = mContext.getResources().getInteger(R.integer.sliderRedundancyCount);

            //设置slider的placeholder给indicator使用
            mPlaceHolderSliderViewPager = itemHolder.placeHolderSliderViewpager;
            //设置 slider placeholder 的 adapter;placeholder的数量是slider的数量减去6
            HomePlaceHolderSliderAdapter placeHolderSliderAdapter = new HomePlaceHolderSliderAdapter(sliderAdapter.getCount()-2*sRN);
            mPlaceHolderSliderViewPager.setAdapter(placeHolderSliderAdapter);


            //设置indicator点
            CirclePageIndicator indicator = itemHolder.indicator;
            indicator.setViewPager(mPlaceHolderSliderViewPager);
            //indicator.setOnPageChangeListener(this);

            //将首次出现的页面index设置为
            mSliderViewPager.setCurrentItem(sRN,false);

            //设置adapter数据

        }
        //loadmore项
        else {

            super.onBindViewHolder(holder,position);

        }

    }

    @Override
    public int getItemViewType(int position){

        if (position == 0)
            return TYPE_HEADER;

        if (position == getItemCount()-1)
            return  TYPE_LOADMORE;

        return TYPE_ITEM;

    }

    @Override
    public int getItemCount() {

        //存在头部和loadmore 需要+2
        return mTopics.size()+2;

    }

    //region viewpager接口方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    /**
     * slider页面变化时调用
     * @param state slider页面状态
     */
    @Override
    public void onPageScrollStateChanged(int state) {

        if (state == ViewPager.SCROLL_STATE_IDLE){

            //slider的单边冗余数量
            int sRN = mContext.getResources().getInteger(R.integer.sliderRedundancyCount);

            //当前页面号码
            int currentPage = mSliderViewPager.getCurrentItem();

            //总页数
            int pageCount = mSliderViewPager.getAdapter().getCount();

            //头三页或后三页  就移动
            if (currentPage <= sRN-1){

                mSliderViewPager.setCurrentItem(pageCount-2*sRN+currentPage,false);

                //根据slider的页号 设置placeholder的页号
                mPlaceHolderSliderViewPager.setCurrentItem(pageCount-2*sRN+currentPage-sRN,false);

            }
            else if (currentPage >=pageCount-sRN ){

                mSliderViewPager.setCurrentItem(2*sRN-pageCount+currentPage,false);

                //根据slider的页号 设置placeholder的页号
                mPlaceHolderSliderViewPager.setCurrentItem(-(pageCount-2*sRN)+currentPage-sRN,false);

            }
            //根据slider的页号 设置placeholder的页号
            else {

                mPlaceHolderSliderViewPager.setCurrentItem(currentPage-sRN,false);

            }


        }

    }
    //endregion


    //region viewholder类


    /**
     * 展示topic的viewholder
     */
    class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView topicTitleTextView;
        public TextView topicDateTextView;
        public TopicPreviewImageView topicPreviewImageView;
        public ImageView topicProfileImageView;
        public RelativeLayout topicCell;

        public ItemViewHolder(View itemView) {
            super(itemView);

            topicTitleTextView = (TextView)itemView.findViewById(R.id.topicTitleTextView);
            topicDateTextView = (TextView)itemView.findViewById(R.id.topicDateTextView);
            topicPreviewImageView = (TopicPreviewImageView)itemView.findViewById(R.id.topicPreviewImageView);
            topicProfileImageView = (ImageView)itemView.findViewById(R.id.topicProfileImageView);
            topicCell =(RelativeLayout)itemView.findViewById(R.id.topicCell);

            //设置click listener接口
            itemView.setOnClickListener(mOnClickItemListener);


        }

    }

    /**
     * 展示slider的viewholder
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder{

        public ViewPager sliderViewpager;
        public ViewPager placeHolderSliderViewpager;
        public CirclePageIndicator indicator;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            sliderViewpager = (ViewPager)itemView.findViewById(R.id.sliderViewpager);
            placeHolderSliderViewpager =(ViewPager)itemView.findViewById(R.id.placeHolderSliderViewpager);
            indicator = (CirclePageIndicator)itemView.findViewById(R.id.sliderPageIndicator);

            /**
             * 设置viewpager的高度
             * viewpager放在RecyclerView中 必须设置exact高度
             */
            WindowManager wm = (WindowManager)itemView.getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);

            sliderViewpager.getLayoutParams().height=metrics.widthPixels/4*3;

        }
    }

    //endregion

}
