package com.greenumbrellastudio.android.aituker.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songjiekun on 16/2/2.
 */
public class TopicListResult {

    @SerializedName("results")
    List<Topic> topics;

    public List<Topic> getTopics(){

        return topics;

    }

    public TopicListResult(){

        topics = new ArrayList<Topic>();

    }

}
