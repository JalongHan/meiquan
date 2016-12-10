package com.haoqu.meiquan.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.activity.FriendSharePageActivity;
import com.haoqu.meiquan.adapter.FriendsListAdapter;
import com.haoqu.meiquan.entity.FriendsList;
import com.haoqu.meiquan.entity.FriendsListEntity;
import com.haoqu.meiquan.tools.ACache;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendsListFragment extends Fragment {

    private ListView flFragment_listView;
    private FriendsListAdapter adapter;
    private ArrayList<FriendsList> friendslist;
    private TextView frieds_num;
    private View view;
    private Button bt_invite_friends;
    private RelativeLayout rl_top;
    private View spaceView;
    private CircleImageView iv_headicon;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ShowHeadIcon();
    }

    private void ShowHeadIcon() {
        if(TApplication.avatar.length()>0) {
            TApplication.imageLoader.displayImage(TApplication.avatar, iv_headicon, TApplication.options);
        }
    }


    private void getDate() {
        Log.i("执行到了", "getDate");
        /* volley发请求去 */
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", TApplication.uid);
        params.put("token", TApplication.token);

        VolleyRequestTool.RequestPost(getActivity(), Consts.baseUrl
                        + Consts.APPGetFriendsList + Consts.ogid,
                Consts.APPGetFriendsList, params, new VolleyListenerInterface(
                        this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("result", result);
                        try {
                            jsonParse(result, 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Log.i("finally", "finally");
                            Tools.closeProgressDialog();
                        }

                    }


                    @Override
                    public void onMyError(VolleyError error) {
                        // volley出现错误的时候执行onMyError的方法
                        Log.i("onMyError", "请求错误执行onmyerror");
                        GetErrorBackTool.gerErrorBack(getActivity());
                        ACache cache = ACache.get(getActivity());
                        String result = cache.getAsString(Consts.flDATA);

                        if (result == null) {
                            Log.i("cacheresult", "null");
                        } else {

                            jsonParse(result, 1);
                        }


                    }
                });

    }

    /**
     * 将得到的字符串解析显示
     *
     * @param result
     * @param in     0的时候保存result 1的时候不保存
     */
    private void jsonParse(String result, int in) {
        FriendsListEntity friendsListe = JSON.parseObject(
                result, FriendsListEntity.class);
        Log.i("onmyerror", result);
        switch (Integer.valueOf(friendsListe.getError())) {
            // 成功
            case 0:
                frieds_num.setText("好友人数:  " + friendsListe.getFnum() + "人");
                friendslist = (ArrayList<FriendsList>) friendsListe.getList();

                if (friendslist != null&& friendslist.size() > 0) {
                    // list不为空

                    adapter = new FriendsListAdapter(getActivity(), friendslist);

                    flFragment_listView.addHeaderView(spaceView);
                    flFragment_listView.setAdapter(adapter);

                }
                switch (in) {
                    case 0:
                /* 把得到的字符json字符串缓存起来 */
                        Log.i("fragmentpsList", "把得到的字符json字符串缓存起来");
                        ACache cache = ACache.get(getActivity());
                        cache.put(Consts.flDATA, result);
                        break;

                    default:
                        break;
                }
                break;
            // 令牌错误
            case -100:
                LoginOut.logOut(getActivity());
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Tools.showProgressDialog(getActivity());
        view = inflater.inflate(R.layout.fragment_friendslist, null);
        initViews(inflater);
        //当版本小于4.4的时候设置头不显示
        try {
            if (VERSION.SDK_INT < VERSION_CODES.KITKAT) {
                Log.i("friends判断版本执行了", "判断版本执行了");
                rl_top.setVisibility(View.GONE);
            } else {
                rl_top.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

		/* 获取数据 */

        getDate();

		/* 设置监听 */
        setListtener();

        return view;

    }

    private void initViews(LayoutInflater inflate) {
        //头像图片
        iv_headicon = (CircleImageView)view.findViewById(R.id.flFragment_iv_headIcon);
        ShowHeadIcon();
        flFragment_listView = (ListView) view
                .findViewById(R.id.flFragment_listView);
        frieds_num = (TextView) view
                .findViewById(R.id.flFragment_tv_friendsNumber);
        bt_invite_friends = (Button) view
                .findViewById(R.id.flFragment_bt_inviteFriends);
        rl_top = (RelativeLayout) view.findViewById(R.id.flFragment_rl_top);

        spaceView = inflate.inflate(R.layout.spaceview, null);

        // flFragment_listView.setMode(Mode.PULL_FROM_END);
        // 设置PullRefreshListView上提加载时的加载提示
        // flFragment_listView.getLoadingLayoutProxy(false,
        // true).setPullLabel("上拉加载...");
        // flFragment_listView.getLoadingLayoutProxy(false,
        // true).setRefreshingLabel("正在加载...");
        // flFragment_listView.getLoadingLayoutProxy(false,
        // true).setReleaseLabel("松开加载更多...");
    }

    private void setListtener() {

        bt_invite_friends.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击跳转到分享朋友页面
                getActivity().startActivity(new Intent(getActivity(), FriendSharePageActivity.class));
            }
        });

    }


    protected void getMoreDate() {
        Log.i("执行到了", "getDate");
		/* volley发请求去 */

        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", TApplication.uid);
        params.put("token", TApplication.token);

        VolleyRequestTool.RequestPost(
                getActivity(), Consts.baseUrl + Consts.APPGetFriendsList + Consts.ogid,
                Consts.APPGetFriendsList,
                params,
                new VolleyListenerInterface(
                        this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener)
                {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("result", result);
                        try {
                            FriendsListEntity friendsListe = JSON.parseObject(
                                    result, FriendsListEntity.class);

                            switch (Integer.valueOf(friendsListe.getError())) {
                                // 成功
                                case 0:
                                    ArrayList<FriendsList> morefriendslist = (ArrayList<FriendsList>) friendsListe
                                            .getList();
                                    if (friendslist != null
                                            && friendslist.size() > 0) {
                                        // list不为空
                                        friendslist.addAll(morefriendslist);
                                        adapter.notifyDataSetChanged();

                                    }
                                    break;
                                // 令牌错误
                                case -100:
                                    LoginOut.logOut(getActivity());
                                    break;

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                        }

                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        // volley出现错误的时候执行onMyError的方法
                        GetErrorBackTool.gerErrorBack(getActivity());

                    }
                });

    }

}
