package com.greenumbrellastudio.android.aituker.network;

import com.greenumbrellastudio.android.aituker.model.TopicListResult;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Created by songjiekun on 16/2/2.
 */
public interface LeanCloudEndpointInterface {

    @Headers({"X-LC-Id: BwFnvTPjlOeyYwOHXzBt3gxx-gzGzoHsz","X-LC-Key: wipPF2wIicLxCCv9HSSoRuAB","Content-Type: application/json" })
    @GET("/1.1/classes/Topic")
    Call<TopicListResult> getTopicList();

}
