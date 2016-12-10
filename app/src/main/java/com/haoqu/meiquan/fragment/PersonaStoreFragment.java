package com.haoqu.meiquan.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.activity.GoosDetailsActivity;
import com.haoqu.meiquan.activity.ShangPinListActivity;
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

public class PersonaStoreFragment extends Fragment implements personaStoreFragmentListViewAdapter.Callback {
    private PullToRefreshListView psFragment_listView;
    private personaStoreFragmentListViewAdapter adapter;
    private View headView;
    private List<PSListEntity> psList;
    private ListView listView;
    private RelativeLayout llparent;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    /**
     * 联网获取个人商店的数据
     */
    private void GetPerSonaStoreList() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", TApplication.uid);
        params.put("token", TApplication.token);
        params.put("gnum", "3");

        VolleyRequestTool.RequestPost(getActivity(), Consts.baseUrl
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
                        GetErrorBackTool.gerErrorBack(getActivity());
                        psFragment_listView.onRefreshComplete();

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
                            ACache cache = ACache.get(getActivity());
                            cache.put(Consts.psDATA, result);
                            break;

                        default:
                            break;
                    }
                    break;

                // 令牌错误
                case -100:
                    LoginOut.logOut(getActivity());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Tools.closeProgressDialog();
            psFragment_listView.onRefreshComplete();

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Tools.showProgressDialog(getActivity());
        View view = inflater.inflate(R.layout.fragment_personstore, null);
        llparent = (RelativeLayout) view.findViewById(R.id.llparent);

        psFragment_listView = (PullToRefreshListView) view
                .findViewById(R.id.psFragment_listView);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        listView = psFragment_listView.getRefreshableView();

        headView = inflater.inflate(R.layout.psfragment_banner, null);
        headView.setLayoutParams(layoutParams);
		/* 设置监听 */
        setListtener();
        ViewGroup.LayoutParams params = psFragment_listView.getLayoutParams();
        /**
         * 获得屏幕的高度
         */
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height2 = outMetrics.heightPixels;


        params.height = height2;
        Log.i("params.height", String.valueOf(params.height));

        psList = new ArrayList<PSListEntity>();

        adapter = new personaStoreFragmentListViewAdapter(getActivity(), psList, this);
        listView.addHeaderView(headView);
        //给listview设置高度,解决listview不显示的问题
        psFragment_listView.setLayoutParams(params);
//        listView.setLayoutParams(params);
        psFragment_listView.setAdapter(adapter);

//        listView.setItemsCanFocus(true);
		/* 如果有网络的话去联网加载获取数据 */
        if (NetworkState.checkNetState(getActivity())) {
            Log.i("有网", "执行");
            GetPerSonaStoreList();
        } else {
			/*把得到的字符json字符串缓存起来*/
            Log.i("没网", "执行");
            ACache cache = ACache.get(getActivity());
            String result = cache.getAsString(Consts.psDATA);
            if (result == null) {
                Log.i("cacheresult", "null");
            } else {
                parseJson(result, 1);
            }
        }

        return view;
    }

    private void setListtener() {

        ViewTreeObserver vto1 = llparent.getViewTreeObserver();
        vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llparent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int lvheight = llparent.getHeight();
                Log.i("lvheight", String.valueOf(lvheight));
            }
        });


        psFragment_listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        // 设置PullRefreshListView上提加载时的加载提示
        psFragment_listView.getLoadingLayoutProxy(true, false).setPullLabel(
                "下拉刷新...");
        psFragment_listView.getLoadingLayoutProxy(true, false)
                .setRefreshingLabel("正在刷新...");
        psFragment_listView.getLoadingLayoutProxy(true, false)
                .setReleaseLabel("松开刷新...");

        psFragment_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetPerSonaStoreList();
            }
        });


		/* 用来屏蔽头部view的点击事件， */
        headView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				MyToast.Toast(getActivity()	, "点击了头部");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




    @Override
    public void click(View v) {

        switch (v.getId()){

            case R.id.psfragment_rl_zhonglei:

                Intent intent = new Intent(getActivity(), ShangPinListActivity.class);
                int z = (Integer) v.getTag();
                intent.putExtra(Consts.KEY_DATA,  psList.get(z).getId());
                intent.putExtra("name", psList.get(z).getName());
                startActivity(intent);

                break;
            case R.id.psfragment_rl_shangpin:

                Intent intent1 = new Intent(getActivity(), GoosDetailsActivity.class);
                int n = (Integer) v.getTag();
                intent1.putExtra("web", psList.get(n).getsID());
                intent1.putExtra("thumb", psList.get(n).getThumb());
                startActivity(intent1);


                break;

        }

    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getActivity(), "listview的item被点击了！，点击的位置是-->" + position,
//                Toast.LENGTH_SHORT).show();
//    }
}
