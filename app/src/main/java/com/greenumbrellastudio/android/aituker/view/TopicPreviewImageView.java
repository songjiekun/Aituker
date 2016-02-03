package com.greenumbrellastudio.android.aituker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by songjiekun on 16/1/25.
 */
public class TopicPreviewImageView extends ImageView {


    //region constructor
    public TopicPreviewImageView(Context context) {
        super(context);
    }

    public TopicPreviewImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopicPreviewImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopicPreviewImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    //endregion



    //根据图片大小 设置图片宽高比
    @Override
    protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec){

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        Drawable drawable = getDrawable();

        if(drawable!=null){

            //宽度
            Integer width = MeasureSpec.getSize(widthMeasureSpec);

            //图片高宽比
            Integer imageWidth = drawable.getIntrinsicWidth();
            Integer imageHeight = drawable.getIntrinsicHeight();
            float ratio = (float)imageHeight/imageWidth;

            //高度
            Integer height = (int)(width*ratio);

            setMeasuredDimension(width,height);

        }
        else {

            //宽度
            Integer width = MeasureSpec.getSize(widthMeasureSpec);

            //高度
            Integer height = width/2;

            setMeasuredDimension(width,height);

        }

    }

}
