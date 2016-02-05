package com.greenumbrellastudio.android.aituker.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/**
 * Created by songjiekun on 16/2/1.
 */
public class LoadMoreRecyclerView extends RecyclerView {

    /**
     * 定义监听loadmore接口
     */
    public interface OnloadMoreListener {

        void loadMore();

    }

    /**
     * OnloadMoreListener接口
     */
    private OnloadMoreListener mOnloadMoreListener;

    public void setOnLoadMoreListener(OnloadMoreListener loadMoreListener){

        mOnloadMoreListener = loadMoreListener;

    }

    /**
     * 载入更多topic用到的参数
     */

    private int previousTotle = 0;
    public boolean loading = false;
    private int visiableThreshold = 1;

    public LoadMoreRecyclerView(Context context) {
        super(context);


    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onFinishInflate(){

        super.onFinishInflate();

        setLoadMore();

    }

    /**
     * 设置loadmore
     */
    private void setLoadMore(){

        addOnScrollListener(new OnScrollListener() {

            //当拖动到最下面时 载入更多
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();

                if (layoutManager!=null){

                    int firstVisibleItem,visibleItemCount,totalItemCount;

                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if (loading){

                        if (totalItemCount>previousTotle){

                            loading =false;
                            previousTotle = totalItemCount;

                        }

                    }

                    if (!loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem+visiableThreshold)){

                        loading = true;

                        //调用loadmore listener
                        mOnloadMoreListener.loadMore();

                    }

                }

            }

        });

    }

}
