package com.greenumbrellastudio.android.aituker.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.greenumbrellastudio.android.aituker.R;
import com.greenumbrellastudio.android.aituker.model.Topic;
import com.greenumbrellastudio.android.aituker.model.TopicListResult;
import com.greenumbrellastudio.android.aituker.network.LeanCloudEndpointInterface;
import com.greenumbrellastudio.android.aituker.topic.TopicActivity;
import com.greenumbrellastudio.android.aituker.view.LoadMoreRecyclerView;
import com.greenumbrellastudio.android.aituker.view.LoadMoreRecyclerView.OnloadMoreListener;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private LoadMoreRecyclerView mRecyclerView;
    private HomeTopicAdapter mHomeTopicAdapter;
    private LinearLayoutManager mRecyclerLayoutManager;
    private SwipeRefreshLayout mPullToRefresh;

    /**
     * topic列表
     */
    private List<Topic> mTopics = new ArrayList<Topic>();


    /**
     * 载入更多topic用到的参数
     */

    private int previousTotle = 0;
    private boolean loading = false;
    private int visiableThreshold = 1;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //恢复状态
        if (savedInstanceState != null){

            mTopics = savedInstanceState.getParcelableArrayList("topics");

        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //配置view
        configureViews(rootView);

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        savedInstanceState.putParcelableArrayList("topics", new ArrayList<Topic>(mTopics));

    }


    @Override
    public void onPause(){

        super.onPause();

        //取消slider滑动
        mHomeTopicAdapter.pauseAutoSlider();

    }

    @Override
    public void onResume(){

        super.onResume();

        //恢复slider滑动
        mHomeTopicAdapter.resumeAutoSlider();

    }



    //region helper函数
    /**
     * 配置view
     * @param rootView 根view
     */
    private void configureViews(View rootView){

        /** 配置recyclerview**/
        mRecyclerView = (LoadMoreRecyclerView)rootView.findViewById(R.id.mainRecyclerView);
        mRecyclerView.setItemViewCacheSize(2);

        //设置layoutmanager
        mRecyclerLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mRecyclerLayoutManager);

        //设置adapter
        mHomeTopicAdapter = new HomeTopicAdapter(getContext());
        mHomeTopicAdapter.setTopics(mTopics);
        mHomeTopicAdapter.setOnClickSliderListener(this);
        mHomeTopicAdapter.setOnClickItemListener(this);//设置adapter的listener 必须要放在setAdapter之前
        mRecyclerView.setAdapter(mHomeTopicAdapter);


        mRecyclerView.setOnLoadMoreListener(new OnloadMoreListener() {
            @Override
            public void loadMore() {

                // TODO: 16/2/1

            }
        });

        /**
        //设置loadmore
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            //当拖动到最下面时 载入更多
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (layoutManager != null) {

                    int firstVisibleItem, visibleItemCount, totalItemCount;

                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {

                        if (totalItemCount > previousTotle) {

                            loading = false;
                            previousTotle = totalItemCount;

                        }

                    }

                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visiableThreshold)) {

                        loading = true;



                    }

                }

            }

        });
        **/


        //mHomeTopicAdapter.getSliderViewPager().addOnPageChangeListener(this);//设置viewpager的listener

        /** 配置pull to refresh**/
        mPullToRefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.pullToRefresh);
        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            //实现刷新功能
            @Override
            public void onRefresh() {

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient();
                client.interceptors().add(interceptor);



                //创建retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://api.leancloud.cn")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LeanCloudEndpointInterface leanApiService = retrofit.create(LeanCloudEndpointInterface.class);

                Call<TopicListResult> call = leanApiService.getTopicList();
                call.enqueue(new Callback<TopicListResult>() {
                    @Override
                    public void onResponse(Response<TopicListResult> response, Retrofit retrofit) {

                        int statusCode = response.code();
                        TopicListResult topicListResult = response.body();

                        List<Topic> topics = topicListResult.getTopics();

                        //引用
                        mTopics = topics;

                        //更新topic列表显示
                        mHomeTopicAdapter.setTopics(mTopics);
                        mHomeTopicAdapter.notifyDataSetChanged();

                        //停止刷新动画
                        mPullToRefresh.setRefreshing(false);

                    }

                    @Override
                    public void onFailure(Throwable t) {

                        //停止刷新动画
                        mPullToRefresh.setRefreshing(false);

                        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });


    }


    //endregion


    //region click接口方法
    @Override
    public void onClick(View v) {

        //如果点击的是topic
        if (v instanceof CardView){

            CardView view = (CardView)v;

            //获取位置信息
            int position = mRecyclerView.getChildAdapterPosition(view);

            //获取相关数据
            Topic topic = mTopics.get(position);

            //调用新intent
            Intent topicIntent = new Intent(getContext(), TopicActivity.class);
            startActivity(topicIntent);


        }
        //如果点击的是slider
        else if (v instanceof FrameLayout){

                //获取位置信息
                int position = (int)v.getTag();

                Toast.makeText(getContext(), String.valueOf(position)+"slider", Toast.LENGTH_SHORT).show();

        }


    }


    //endregion



}
