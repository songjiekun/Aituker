package com.greenumbrellastudio.android.aituker.topic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.greenumbrellastudio.android.aituker.R;

/**
 * Created by songjiekun on 16/2/16.
 * Gallery的adapter
 */
public class TopicGalleryAdapter extends RecyclerView.Adapter {

    /**
     * context
     */
    private Context mContext;

    /**
     * 构造函数
     */
    public TopicGalleryAdapter(Context context){

        mContext = context;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cell_gallery_topic,parent,false);
        GalleryViewHolder viewHolder = new GalleryViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final GalleryViewHolder itemHolder = (GalleryViewHolder)holder;
        if (position==2 || position==4){

            itemHolder.galleryImageView.setImageResource(R.drawable.pict);

        }
        else if (position==11 || position==12 || position==13 || position==16){

            itemHolder.galleryImageView.setImageResource(R.drawable.profile);

        }
        else {

            itemHolder.galleryImageView.setImageResource(R.drawable.shoe);

        }


    }

    @Override
    public int getItemCount() {
        return 40;
    }

    /**
     * gallery viewholder class
     */
    class GalleryViewHolder extends RecyclerView.ViewHolder{

        public ImageView galleryImageView;

        public GalleryViewHolder(View itemView) {
            super(itemView);

            galleryImageView =(ImageView)itemView.findViewById(R.id.galleryImageView);


        }
    }
}
