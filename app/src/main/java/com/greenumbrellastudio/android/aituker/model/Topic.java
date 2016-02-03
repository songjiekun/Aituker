package com.greenumbrellastudio.android.aituker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songjiekun on 16/1/25.
 */
public class Topic implements Parcelable{

    @SerializedName("title")
    private String mTitle;

    @SerializedName("publishedDate")
    private String mPublishedDate;

    @SerializedName("previewImage")
    private String mPreviewUrl;

    @SerializedName("publisherProfile")
    private String mProfileUrl;

    @SerializedName("id")
    private Integer mTopicID;

    public Topic(String title,String previewUrl,String profileUrl,Integer topciID,String publishedDate){

        mTitle=title;
        mPreviewUrl=previewUrl;
        mProfileUrl=profileUrl;
        mTopicID=topciID;
        mPublishedDate=publishedDate;

    }

    //region setter和getter

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public void setmPublishedDate(String mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public String getmPreviewUrl() {
        return mPreviewUrl;
    }

    public void setmPreviewUrl(String mPreviewUrl) {
        this.mPreviewUrl = mPreviewUrl;
    }

    public String getmProfileUrl() {
        return mProfileUrl;
    }

    public void setmProfileUrl(String mProfileUrl) {
        this.mProfileUrl = mProfileUrl;
    }

    public Integer getmTopicID() {
        return mTopicID;
    }


    //endregion




    //region 序列化
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mTitle);
        dest.writeString(mPreviewUrl);
        dest.writeString(mProfileUrl);
        dest.writeString(mPublishedDate);
        dest.writeInt(mTopicID);

    }

    private Topic(Parcel in){

        mTitle = in.readString();
        mPreviewUrl = in.readString();
        mProfileUrl = in.readString();
        mPublishedDate = in.readString();
        mTopicID = in.readInt();

    }

    public static final Parcelable.Creator<Topic> CREATOR
            = new Parcelable.Creator<Topic>(){


        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    //endregion




}
