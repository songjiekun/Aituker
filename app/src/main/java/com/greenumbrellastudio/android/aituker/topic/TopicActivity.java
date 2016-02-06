package com.greenumbrellastudio.android.aituker.topic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.greenumbrellastudio.android.aituker.R;
import com.greenumbrellastudio.android.aituker.view.BounceScrollView;

public class TopicActivity extends FragmentActivity implements BounceScrollView.OnScrollToEdageListener{

    private BounceScrollView mScrollView;

    @Override
    public void finish(){

        super.finish();

        //activity滑出到右侧动画
        overridePendingTransition(R.anim.hold,R.anim.exit_to_right );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        //配置views
        setupViews();

    }

    /**
     * 配置tab
     */
    private void setupViews(){

        mScrollView =(BounceScrollView)findViewById(R.id.scrollView);

        //设置OnScrollToEdageListener的监听代理
        mScrollView.setOnScrollToEdageListener(this);

        //去掉overscroll时边缘颜色glow
        int transparentColor = Color.parseColor("#00000000");//透明色
        changeGlowEffect(this,transparentColor);

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

                params.setMargins(0,(int)animation.getAnimatedValue(),0,0);
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

}
