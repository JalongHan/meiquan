package com.haoqu.meiquan.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.adapter.GetMyResultsAdapter;
import com.haoqu.meiquan.entity.GetMyMoneyEntity;
import com.haoqu.meiquan.entity.GetMyResultsEntity;
import com.haoqu.meiquan.entity.MyResults;
import com.haoqu.meiquan.entity.TiXianEntity;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.MyToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyResultsActivity extends BaseActivity implements OnClickListener {

    private PullToRefreshListView listView;
    private GetMyResultsAdapter adapter;
    private List<MyResults> myResult;
    private ImageView iv_back;
    private TextView tv_balance;
    private Button bt_tixian;
    private EditText et_money;
    // 页数
    private int page;
    private Button ib_tixianCommit;
    private TextView tv_dialog_tips;
    private GetMyResultsEntity getmyresult;
    private TextView tv_nickname;
    private ImageView iv_headIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_results);
        page = 1;
        initViews();
        setListener();
        // getDate();

    }

    /* dialog关掉时重新执行此方法，刷新 */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        Log.i("onResume", "onResume");
        super.onResume();
        getDate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        TApplication.queue.cancelAll("getMyResults");
        TApplication.queue.cancelAll("getMoreResults");
        TApplication.queue.cancelAll(Consts.APPTakeMoney);
    }

    private void getDate() {
        Tools.showProgressDialog(this);

        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", TApplication.uid);
        params.put("token", TApplication.token);
        params.put("page", String.valueOf(page));

        VolleyRequestTool.RequestPost(getApplicationContext(), Consts.baseUrl
                        + Consts.APPGetMyResults + Consts.ogid, Consts.APPGetMyResults,
                params, new VolleyListenerInterface(this,
                        VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("result", result);
                        try {
                            getmyresult = JSON.parseObject(
                                    result, GetMyResultsEntity.class);
                            if (getmyresult.getMoney() != null) {
                                // 设置余额
                                tv_balance.setText("¥ "+getmyresult.getMoney());
                            }
                            //设置呢称
//                            if(getmyresult.getNickname() != null){
//                                tv_nickname.setText(getmyresult.getNickname());
//                            }

                            switch (Integer.valueOf(getmyresult.getError())) {
                                // 找回成功
                                case 0:
                                    if ("0".equals(getmyresult.getTotal())) {
                                        Tools.closeProgressDialog();
                                        return;
                                    }
                                    myResult = getmyresult.getList();

                                    Log.i("myResult", myResult.get(0).toString());

                                    adapter = new GetMyResultsAdapter(
                                            getApplicationContext(), myResult);
                                    listView.setAdapter(adapter);
                                    page++;
                                    break;
                                // 短信验证码错误
                                case -100:
                                    // 令牌错误，执行登出应用
                                    LoginOut.logOut(MyResultsActivity.this);
                                    break;

                            }
                        } catch (Exception e) {

                            tv_balance.setText("0.00");
//                            tv_nickname.setText("海边一条鱼");
                            e.printStackTrace();
                        } finally {
                            Tools.closeProgressDialog();
                        }

                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        // volley出现错误的时候执行onMyError的方法
                        GetErrorBackTool.gerErrorBack(getApplicationContext());

                    }
                });

    }

    private void initViews() {
        //头像
        iv_headIcon = (ImageView) findViewById(R.id.mrActivity_iv_headIcon);

        if(TApplication.avatar.length()>0){
            TApplication.imageLoader.displayImage(TApplication.avatar,iv_headIcon,TApplication.options);
        }
        // 余额
        tv_balance = (TextView) findViewById(R.id.mrActivity_tv_balance);
        // 后退按钮
        iv_back = (ImageView) findViewById(R.id.mrActivity_iv_back);
        // 提现按钮
        bt_tixian = (Button) findViewById(R.id.mrActivity_bt_tixian);
        // 呢称
        tv_nickname = (TextView) findViewById(R.id.mrActivity_tv_nickname);
        if(TApplication.nickname.length()>0){
            tv_nickname.setText(TApplication.nickname);
        }else if(TApplication.mobile.length()>0){
            tv_nickname.setText(TApplication.mobile);
        }
        // listview
        listView = (PullToRefreshListView) findViewById(R.id.mrActivity_listView);
        listView.setMode(Mode.PULL_FROM_END);
        // 设置PullRefreshListView上提加载时的加载提示
        listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载...");
        listView.getLoadingLayoutProxy(false, true)
                .setReleaseLabel("松开加载更多...");
    }

    // 监听
    @SuppressWarnings("unchecked")
    private void setListener() {
        iv_back.setOnClickListener(this);
        bt_tixian.setOnClickListener(this);

        // listview的刷新监听
        listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                /* 获得更多数据 */

                getMoreDate();

                // listView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 按了后退按钮
            case R.id.mrActivity_iv_back:
                finish();
                break;
            // 按了提现按钮
            case R.id.mrActivity_bt_tixian:
                // 判断是否有银行卡
                getIsHasBankCard();

                break;
            // 按了确认提现按钮
            // case R.id.mrActivity_tixian_commit:
            // String tixianmoney = et_money.getText().toString();
            // if(Integer.valueOf(tixianmoney)<100 ||
            // Integer.valueOf(tixianmoney)>100000){
            // Toast.makeText(getApplicationContext(), "提现的金额超过范围",
            // Toast.LENGTH_SHORT);
            // return;
            // }
            // Tixian(et_money.getText().toString());
            // break;
            default:
                break;
        }
    }

    /**
     * 弹出提现dialog
     */
    private void tiXianDiaolog() {

        View view = View.inflate(this, R.layout.tixian_dialog, null);
        // 提现金额文本框
        et_money = (EditText) view.findViewById(R.id.mrActivity_tixian_sum);
        // 确定提现按钮
        ib_tixianCommit = (Button) view.findViewById(R.id.mrActivity_tixian_commit);
        //dialog中显示余额
        tv_dialog_tips = (TextView) view.findViewById(R.id.mrActivity_dialog_tips);
        //设置dialog中显示余额
        if (getmyresult.getMoney() != null) {
            tv_dialog_tips.setText("我的余额:" + getmyresult.getMoney() + "元");
        }

        Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);

        dialog.show();
        ib_tixianCommit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // 按了确认提现按钮
                    case R.id.mrActivity_tixian_commit:
                        String tixianmoney = et_money.getText().toString();
                        if (tixianmoney.isEmpty()) {
                            MyToast.Toast(getApplicationContext(), "还未填写金额");
                            return;
                        }
                        if (Float.valueOf(tixianmoney) < 100
                                || Float.valueOf(tixianmoney) > 100000) {
                            MyToast.Toast(getApplicationContext(), "提现的金额超过范围");
                            return;
                        }

                        Tixian(tixianmoney);

                        dialog.dismiss();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    /**
     * 判断是否有银行卡
     */
    private void getIsHasBankCard() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", TApplication.uid);
        params.put("token", TApplication.token);

        VolleyRequestTool.RequestPost(MyResultsActivity.this, Consts.baseUrl
                        + Consts.APPMyMoney + Consts.ogid, Consts.APPMyMoney, params,
                new VolleyListenerInterface(this,
                        VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("result", result);
                        try {
                            GetMyMoneyEntity mymoney = JSON.parseObject(result,
                                    GetMyMoneyEntity.class);
                            if (mymoney != null) {

                                switch (Integer.valueOf(mymoney.getError())) {
                                    case 0:
                                        switch (Integer.valueOf(mymoney.getIsbind())) {
                                            case 0:
                                                myBankDialog();
                                                break;

                                            case 1:
                                                tiXianDiaolog();
                                                break;
                                            default:
                                                break;
                                        }

                                        break;

                                    case -100:
                                        LoginOut.logOut(MyResultsActivity.this);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        GetErrorBackTool.gerErrorBack(MyResultsActivity.this);
                    }
                });

    }

    /**
     * 没有绑定银行卡提现，引导用户去绑定银行卡页面
     */
    protected void myBankDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您还未绑定银行卡，是否去绑定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyResultsActivity.this, MyBankActivity.class));
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    /**
     * 执行提现提交
     */
    private void Tixian(final String money) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", TApplication.uid);
        params.put("token", TApplication.token);
        params.put("money", money);

        VolleyRequestTool.RequestPost(getApplicationContext(), Consts.baseUrl
                        + Consts.APPTakeMoney + Consts.ogid, Consts.APPTakeMoney,
                params, new VolleyListenerInterface(this,
                        VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("Tixian", result);
                        try {

                            TiXianEntity bankInfo = JSON.parseObject(result,
                                    TiXianEntity.class);
                            switch (Integer.valueOf(bankInfo.getError())) {
                                // 通过
                                case 0:
                                    MyToast.Toast(getApplicationContext(), "提现成功");
                                    break;

                                // （不在100~100000之间）
                                case -3:
                                    MyToast.Toast(getApplicationContext(), "不在100~100000之间");
                                    break;
                                // （申请的金额大于余额）
                                case -4:
                                    MyToast.Toast(getApplicationContext(), "申请的金额大于余额");
                                    break;
                                // （申请金额有误，通过后台计算得到的结果非法）
                                case -5:
                                    MyToast.Toast(getApplicationContext(), "数据非法，请联系客服");
                                    break;
                                // 令牌错误
                                case -100:
                                    LoginOut.logOut(MyResultsActivity.this);
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Tools.closeProgressDialog();
                        }

                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        // volley出现错误的时候执行onMyError的方法
                        GetErrorBackTool.gerErrorBack(getApplicationContext());

                    }
                });

    }

    /**
     * 加载更多数据
     */
    protected void getMoreDate() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", TApplication.uid);
        params.put("token", TApplication.token);
        params.put("page", String.valueOf(page));

        VolleyRequestTool.RequestPost(getApplicationContext(), Consts.baseUrl
                        + Consts.APPGetMyResults + Consts.ogid, Consts.APPGetMyResults,
                params, new VolleyListenerInterface(this,
                        VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("result", result);
                        try {
                            GetMyResultsEntity getmyresult = JSON.parseObject(
                                    result, GetMyResultsEntity.class);

                            // 设置余额
                            tv_balance.setText(getmyresult.getMoney()
                                    + "元");
                            switch (Integer.valueOf(getmyresult.getError())) {
                                // 找回成功
                                case 0:
                                    // 如果请求的的好友数是零的话，返回，不执行
                                    if ("0".equals(getmyresult.getTotal())){
                                        MyToast.Toast(getApplicationContext(),"没有更多数据了");
                                        listView.onRefreshComplete();
                                        return;
                                    }

                                    List<MyResults> moreResult = getmyresult
                                            .getList();
                                    myResult.addAll(moreResult);

                                    Log.i("myResult", myResult.get(0).toString());

                                    adapter.notifyDataSetChanged();
                                    page++;
                                    listView.onRefreshComplete();
                                    break;
                                // 短信验证码错误
                                case -100:
                                    // 令牌错误，执行登出应用
                                    LoginOut.logOut(MyResultsActivity.this);
                                    break;

                            }
                        } catch (Exception e) {

                            tv_balance.setText("0元");
                            e.printStackTrace();
                        } finally {
                            listView.onRefreshComplete();
                        }

                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        // volley出现错误的时候执行onMyError的方法
                        GetErrorBackTool.gerErrorBack(getApplicationContext());

                    }
                });

    }

}
