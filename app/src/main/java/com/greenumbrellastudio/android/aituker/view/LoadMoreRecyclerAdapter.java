package com.greenumbrellastudio.android.aituker.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenumbrellastudio.android.aituker.R;

/**
 * Created by songjiekun on 16/2/1.
 */
public class LoadMoreRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int TYPE_LOADMORE = 2;

    public LoadMoreRecyclerAdapter(){

        super();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_LOADMORE){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_loadmore_home,parent,false);
            LoadMoreViewHolder viewHolder = new LoadMoreViewHolder(view);
            return viewHolder;

        }
        else {

            return null;

        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //loadmore项
        if(holder instanceof LoadMoreViewHolder){

            LoadMoreViewHolder loadmoreHolder = (LoadMoreViewHolder)holder;
            loadmoreHolder.loadmoreTextView.setText("载入更多数据");

        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    /**
     * 展示loadmore的viewholder
     */
    class LoadMoreViewHolder extends RecyclerView.ViewHolder{

        public TextView loadmoreTextView;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);

            loadmoreTextView = (TextView)itemView.findViewById(R.id.loadmoreTextView);

        }
    }

}
