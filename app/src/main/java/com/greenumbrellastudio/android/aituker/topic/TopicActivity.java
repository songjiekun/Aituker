package com.greenumbrellastudio.android.aituker.topic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenumbrellastudio.android.aituker.R;
import com.greenumbrellastudio.android.aituker.imagebrowser.ImageBrowserActivity;
import com.greenumbrellastudio.android.aituker.view.BounceScrollView;

public class TopicActivity extends FragmentActivity implements BounceScrollView.OnScrollToEdageListener,BounceScrollView.OnScrollChangedListener, View.OnClickListener{

    private BounceScrollView mScrollView;
    private RecyclerView mGalleryRecyclerView;
    private TopicGalleryAdapter mTopicGalleryAdapter;
    private GridLayoutManager mGalleryLayoutManager;
    private ImageView mBackImageView;
    private View mTopicScrollBackView;
    private View mTopicDescriptionTextView;

    @Override
    public void finish(){

        super.finish();

        //activity滑出到右侧动画
        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        //配置views
        setupViews();

    }

    /**
     * 配置views
     */
    private void setupViews(){

        //获取topic描述
        mTopicDescriptionTextView=(TextView)findViewById(R.id.topicDescriptionTextView);

        //设置描述textview的高度
        //获取textview的 layout参数
        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams) mTopicDescriptionTextView.getLayoutParams();

        //获取topmargin
        int marginTop = params.topMargin;

        //获取整个屏幕的高度
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        final int height = metrics.heightPixels;

        //textview的高度计算
        params.height= height-marginTop;

        //设置描述textview的高度
        mTopicDescriptionTextView.setLayoutParams(params);



        //获取scroll back按钮
        mTopicScrollBackView=(View)findViewById(R.id.topicScrollBackView);

        //设置scroll back监听代理
        mTopicScrollBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //scroll back
                //gallery回退
                mGalleryRecyclerView.smoothScrollToPosition(0);

                //scroll回退

                //先获取屏幕高度
                WindowManager wm = (WindowManager)v.getContext().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                final int height = metrics.heightPixels;
                //获取layout参数
                final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mScrollView.getLayoutParams();

                //动画改变layout参数
                ValueAnimator animator = ValueAnimator.ofInt(height);
                animator.setDuration(500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {

                        params.setMargins(0, height-(int) animation.getAnimatedValue(), 0, 0);
                        mScrollView.setLayoutParams(params);

                    }

                });

                //开始动画
                animator.start();

            }
        });

        //获取回退按钮
        mBackImageView=(ImageView)findViewById(R.id.topicBackImage);

        //设置点击监听代理
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //回退
                onBackPressed();

            }
        });

        //获取scrollview
        mScrollView =(BounceScrollView)findViewById(R.id.scrollView);

        //设置OnScrollToEdageListener的监听代理
        mScrollView.setOnScrollToEdageListener(this);

        //设置OnScrollChangedListener的监听代理
        mScrollView.setOnScrollChangedListener(this);

        //去掉overscroll时边缘颜色glow
        int transparentColor = Color.parseColor("#00000000");//透明色
        changeGlowEffect(this,transparentColor);

        //获取gallery recycleview
        mGalleryRecyclerView = (RecyclerView)findViewById(R.id.galleryRecyclerView);
        mGalleryRecyclerView.setHasFixedSize(true);

        //设置layoutmanager
        mGalleryLayoutManager=new GridLayoutManager(this,2);
        mGalleryRecyclerView.setLayoutManager(mGalleryLayoutManager);

        //设置图片占据的span格子数
        mGalleryLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

            @Override
            public int getSpanSize(int position) {

                if (position == 0 || position == 0){

                    //第一幅图片占据整个宽度
                    return 2;


                }

                return 1;
            }

        });

        //设置gallery间距
        mGalleryRecyclerView.addItemDecoration(new SpacesItemDecoration(2));

        //设置adapter
        mTopicGalleryAdapter = new TopicGalleryAdapter(this);
        mTopicGalleryAdapter.setOnClickItemListener(this);
        mGalleryRecyclerView.setAdapter(mTopicGalleryAdapter);

    }

    @Override
    /**
     * scrollview正常拖动到调用
     */
    public void onScrollChanged(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY){

        //正常拖动时（非overscroll）scrollview和后面的recyclerview保持同步
        if (scrollY>=0){

            mGalleryLayoutManager.scrollToPositionWithOffset(0,-scrollY);

        }

    }

    /**
     * scrollview拖动到边缘时调用
     */
    @Override
    public void onScrollToEdage() {

        //将scrollview移动到底部不可见
        //先获取屏幕高度
        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int height = metrics.heightPixels;

        //获取layout参数
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mScrollView.getLayoutParams();

        //动画改变layout参数
        ValueAnimator animator = ValueAnimator.ofInt(height);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                params.setMargins(0, (int) animation.getAnimatedValue(), 0, 0);
                mScrollView.setLayoutParams(params);

            }

        });

        //开始动画
        animator.start();

        //params.setMargins(0,height,0,0);
        //mScrollView.setLayoutParams(params);


    }

    //helper函数

    /**
     * 改变拖动scrollview的边缘颜色
     * @param context
     * @param newColor
     */
    private void changeGlowEffect(Context context, int newColor) {
        //glow
        int glowDrawableId = context.getResources().getIdentifier("overscroll_glow", "drawable", "android");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            Drawable androidGlow = context.getResources().getDrawable(glowDrawableId,null);
            androidGlow.setColorFilter(newColor, PorterDuff.Mode.SRC_IN);

        }
        else {

            Drawable androidGlow = context.getResources().getDrawable(glowDrawableId);
            androidGlow.setColorFilter(newColor, PorterDuff.Mode.SRC_IN);

        }



        //edge
        int edgeDrawableId = context.getResources().getIdentifier("overscroll_edge", "drawable", "android");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            Drawable androidEdge = context.getResources().getDrawable(edgeDrawableId,null);
            androidEdge.setColorFilter(newColor, PorterDuff.Mode.SRC_IN);

        }
        else {

            Drawable androidEdge = context.getResources().getDrawable(edgeDrawableId);
            androidEdge.setColorFilter(newColor, PorterDuff.Mode.SRC_IN);

        }


    }

    /**
     * 点击gallery图片
     * @param v
     */
    @Override
    public void onClick(View v) {

        //获取位置信息
        int position = (int)v.getTag();

        //创建新intent
        Intent imageBrowserIntent = new Intent(this, ImageBrowserActivity.class);

        //传递的数据
        imageBrowserIntent.putExtra("Page_No",position);

        //调用新intent
        startActivity(imageBrowserIntent);

    }

    /**
     * recycleView的间距
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,RecyclerView parent, RecyclerView.State state) {

            //获取view的位置
            int position = parent.getChildAdapterPosition(view);

            if (position == 0){

                //第一个view不加边框
                outRect.left = 0;
                outRect.right = 0;
                outRect.bottom = 0;

            }
            else{

                if (position%2==0){

                    outRect.left = space/2;
                    outRect.right = 0;

                }
                else {

                    outRect.left = 0;
                    outRect.right = space/2;

                }

                outRect.bottom = space;

            }

            // Add top margin only for the first item to avoid double space between items
            if (position == 1 || position == 2) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

}
