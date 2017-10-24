package com.hewaiming.administrator.swipetoloadlayout_demo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hewaiming.administrator.swipetoloadlayout_demo.R;
import com.hewaiming.administrator.swipetoloadlayout_demo.adapter.AlarmRecordAdapter;
import com.hewaiming.administrator.swipetoloadlayout_demo.adapter.HomeAdapter;
import com.hewaiming.administrator.swipetoloadlayout_demo.bean.AlarmRecord;
import com.hewaiming.administrator.swipetoloadlayout_demo.bean.City;
import com.hewaiming.administrator.swipetoloadlayout_demo.comm.Common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private int mType;
    private int mPageNum;
    private Handler mHandler;
    private List<City> mDatas = null;
    private List<City> myData = null;
    private List<AlarmRecord> alarmList = null;
    private HomeAdapter mAdapter = null;
    private FloatingActionButton fab_del, fab_add;
    private static final String URL = "http://125.64.59.11:8000/scgy/android/odbcPHP/AlarmRecordTable_test.php";
    private AlarmRecordAdapter mAdapter_AlarmRecord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(Looper.getMainLooper());
        //initData();
        //initView();
        getNetData();
        //  Common.JsonToList();

    }

    private void getNetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //  String netData= Common.netLink(URL);
                alarmList = new ArrayList<AlarmRecord>();
                alarmList = Common.parseJsonWithGson(Common.netLink(URL));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView(alarmList);
                    }
                }, 1);
            }
        }).start();
    }

    protected void initData() {
        mDatas = new ArrayList<City>();
        myData = new ArrayList<City>();
        for (int i = 'A'; i < 'a'; i++) {
            City mCity = new City();
            mCity.setId("" + i);
            mCity.setName("" + (char) i);
            mDatas.add(mCity);
            myData.add(mCity);
        }
    }

    private void initView(final List<AlarmRecord> mList) {
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //  RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        // RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        if (mType == TYPE_LINEAR) {
//            layoutManager = new LinearLayoutManager(getContext());
//        } else if (mType == TYPE_GRID) {
//            layoutManager = new GridLayoutManager(getContext(), 2);
//        } else if (mType == TYPE_STAGGERED_GRID) {
//            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        }
        recyclerView.setLayoutManager(layoutManager);
        mAdapter_AlarmRecord = new AlarmRecordAdapter(mList);
        //recyclerview  item点击事件
        mAdapter_AlarmRecord.setOnItemClickListener(new AlarmRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "点击:" + mList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        //recyclerview  item长按点击事件
        mAdapter_AlarmRecord.setOnItemLongClickListener(new AlarmRecordAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "长按点击:列表中位置" + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //增加动画
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));//增加分割线
        recyclerView.setAdapter(mAdapter_AlarmRecord);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });

        fab_del = (FloatingActionButton) findViewById(R.id.fab_del);
        fab_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter_AlarmRecord.getItemCount() > 0) {
                    mAdapter_AlarmRecord.removeDate(0);
                } else {
                    Toast.makeText(getApplicationContext(), "没有数据啦", Toast.LENGTH_SHORT).show();
                }

            }
        });
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter_AlarmRecord.addDate(0, new AlarmRecord("0", "BeiJing"));
            }
        });
    }

    private void initView() {
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //  RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        // RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        if (mType == TYPE_LINEAR) {
//            layoutManager = new LinearLayoutManager(getContext());
//        } else if (mType == TYPE_GRID) {
//            layoutManager = new GridLayoutManager(getContext(), 2);
//        } else if (mType == TYPE_STAGGERED_GRID) {
//            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        }
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new HomeAdapter(mDatas);

        //recyclerview  item点击事件
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "点击:" + mDatas.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        //recyclerview  item长按点击事件
        mAdapter.setOnItemLongClickListener(new HomeAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "长按点击:列表中位置" + position, Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView.setItemAnimator(new DefaultItemAnimator()); //增加动画
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));//增加分割线
        recyclerView.setAdapter(mAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });

        fab_del = (FloatingActionButton) findViewById(R.id.fab_del);
        fab_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getItemCount() > 0) {
                    mAdapter.removeDate(0);
                } else {
                    Toast.makeText(getApplicationContext(), "没有数据啦", Toast.LENGTH_SHORT).show();
                }

            }
        });
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addDate(0, new City("0", "BeiJing"));
            }
        });
    }

    @Override
    public void onLoadMore() {
        //mAdapter.append("china");  //无动画效果
      /*  City tmp=new City();
        tmp.setName("北京");
        tmp.setId((mAdapter.getItemCount()-1)+"");
        mAdapter.addDate(mAdapter.getItemCount(), tmp); //有动画效果*/
        // Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        AlarmRecord tmp = new AlarmRecord();
        tmp.setPotNo("8888");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        tmp.setDDate(df.format(new Date()));// new Date()为获取当前系统时间
        mAdapter_AlarmRecord.addDate(mAdapter_AlarmRecord.getItemCount(), tmp);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        // mAdapter.setList(myData);
        getNetData();
        swipeToLoadLayout.setRefreshing(false);
    }

}
