package com.haoqu.meiquan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.adapter.personaStoreFragmentListViewAdapter;
import com.haoqu.meiquan.entity.GetPersonStoreEntity;
import com.haoqu.meiquan.entity.PSListEntity;
import com.haoqu.meiquan.entity.PersonStoreList;
import com.haoqu.meiquan.tools.ACache;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.tools.NetworkState;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShangjiaListActivity extends Activity implements View.OnClickListener, personaStoreFragmentListViewAdapter.Callback {

    private ImageView iv_back;
    private PullToRefreshListView sj_listView;
    private List<PSListEntity> psList;
    private personaStoreFragmentListViewAdapter adapter;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjia_list);

        initView();

        setListener();

        TOShowListView();

    }

    /**
     * 先显示 listview如何没网就加载以前的
     */
    private void TOShowListView() {

        Intent intent = getIntent();
        sid = intent.getStringExtra(Consts.KEY_DATA);
        Log.i("sid",sid);

        psList = new ArrayList<PSListEntity>();
        adapter = new personaStoreFragmentListViewAdapter(this, psList, this);
        sj_listView.setAdapter(adapter);

//        listView.setItemsCanFocus(true);
        /* 如果有网络的话去联网加载获取数据 */
        if (NetworkState.checkNetState(this)) {
            Log.i("有网", "执行");
            GetPerSonaStoreList();
        } else {
			/*把得到的字符json字符串缓存起来*/
            Log.i("没网", "执行");
            ACache cache = ACache.get(this);
            String result = cache.getAsString(Consts.psDATA);
            if (result == null) {
                Log.i("cacheresult", "null");
            } else {
                parseJson(result, 1);
            }
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //后退按钮
        iv_back = (ImageView) findViewById(R.id.sjActivity_iv_back);
        //listview
        sj_listView = (PullToRefreshListView) findViewById(R.id.sjActivity_listView);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        iv_back.setOnClickListener(this);

        //设置模式和监听
        sj_listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        // 设置PullRefreshListView上提加载时的加载提示
        sj_listView.getLoadingLayoutProxy(true, false).setPullLabel(
                "下拉刷新...");
        sj_listView.getLoadingLayoutProxy(true, false)
                .setRefreshingLabel("正在刷新...");
        sj_listView.getLoadingLayoutProxy(true, false)
                .setReleaseLabel("松开刷新...");

        sj_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetPerSonaStoreList();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点了回退按钮
            case R.id.sjActivity_iv_back:
                finish();
                break;

        }
    }


    /**
     * 联网获取个人商店的数据
     */
    private void GetPerSonaStoreList() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", TApplication.uid);
        params.put("token", TApplication.token);
        params.put("sid",sid);
        params.put("gnum", "3");

        VolleyRequestTool.RequestPost(this, Consts.baseUrl
                        + Consts.APPGetPersonStoreList + Consts.ogid,
                Consts.APPGetPersonStoreList, params,
                new VolleyListenerInterface(this,
                        VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("GetPerSonaStoreList.re", result);
                        byte[] str = TApplication.getHttpQueue().getCache().get(Consts.baseUrl
                                + Consts.APPGetPersonStoreList + Consts.ogid).data;
                        Log.i("str", new String(str));
                        parseJson(result, 0);

                    }


                    @Override
                    public void onMyError(VolleyError error) {
                        // volley出现错误的时候执行onMyError的方法
                        GetErrorBackTool.gerErrorBack(ShangjiaListActivity.this);
                        sj_listView.onRefreshComplete();

                    }
                });

    }

    /**
     * 解析json数据并显示到listview上 0时保存字符串，1时什么也不做
     *
     * @param result json字符串
     * @param in     0代表要保存json　1时什么也不做
     */

    private void parseJson(String result, int in) {
        try {

            GetPersonStoreEntity getPStore = JSON.parseObject(result,
                    GetPersonStoreEntity.class);
            Log.i("parseJson", getPStore.toString());
            String error = getPStore.getError();
            switch (Integer.valueOf(error)) {
                // 通过
                case 0:

                    if (getPStore.getList() != null && getPStore.getList().size() > 0) {
                        // psList = getPStore.getList();
                        List<PersonStoreList> personStoreLists = getPStore.getList();
                        List<PSListEntity> addlist = Tools.ListParse(personStoreLists);
                        psList.clear();
                        psList.addAll(addlist);
                        adapter.notifyDataSetChanged();
                        Log.i("fragmentpsList", psList.toString());
                    }
                    switch (in) {
                        case 0:
                    /* 把得到的字符json字符串缓存起来 */
                            Log.i("fragmentpsList", "把得到的字符json字符串缓存起来");
                            ACache cache = ACache.get(this);
                            cache.put(Consts.psDATA, result);
                            break;

                        default:
                            break;
                    }
                    break;

                // 令牌错误
                case -100:
                    LoginOut.logOut(this);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            // Log.i("Exception", e.getMessage().toString());
            e.printStackTrace();
        } finally {
            Tools.closeProgressDialog();
            sj_listView.onRefreshComplete();

        }
    }


    @Override
    public void click(View v) {

        switch (v.getId()) {

            case R.id.psfragment_rl_zhonglei:

                Intent intent = new Intent(this, ShangPinListActivity.class);
                int z = (Integer) v.getTag();
                intent.putExtra(Consts.KEY_DATA, psList.get(z).getId());
                intent.putExtra("name", psList.get(z).getName());
                startActivity(intent);

                break;
            case R.id.psfragment_rl_shangpin:

                Intent intent1 = new Intent(this, GoosDetailsActivity.class);
                int n = (Integer) v.getTag();
                intent1.putExtra("web", psList.get(n).getsID());
                intent1.putExtra("thumb", psList.get(n).getThumb());
                startActivity(intent1);


                break;

        }
    }
}