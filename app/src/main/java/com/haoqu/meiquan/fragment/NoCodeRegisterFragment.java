package com.haoqu.meiquan.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.adapter.SpinerAdapter;
import com.haoqu.meiquan.adapter.SpinerAdapter.IOnItemSelectListener;
import com.haoqu.meiquan.entity.City;
import com.haoqu.meiquan.entity.ErrorCodeEntity;
import com.haoqu.meiquan.entity.GetCity;
import com.haoqu.meiquan.entity.GetProvince;
import com.haoqu.meiquan.entity.GetStore;
import com.haoqu.meiquan.entity.Province;
import com.haoqu.meiquan.entity.Store;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.CountDown;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.MyExceptionToast;
import com.haoqu.meiquan.view.MyToast;
import com.haoqu.meiquan.view.SpinnerPopWindow;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoCodeRegisterFragment extends Fragment implements OnClickListener {

    private View view;
    private EditText et_testCode;
    private EditText et_passWord;
    private EditText et_phoneNumber;
    private Button bt_getTestCode;
    private Button bt_commit;
    private RelativeLayout sp_city;
    private RelativeLayout sp_province;
    private RelativeLayout sp_store;

    private TextView tv_province;
    private TextView tv_city;
    private TextView tv_trades;
    private TextView tv_store;
    /* 点选　的商铺ID */
    private String sid;
    private CountDown countDown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        countDown = new CountDown();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        CountDown.getTimer().cancel();
        if (countDown.getTimer() != null) {
            countDown.getTimer().cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nocode_register, null);
        initViews();
        setListeners();

        return view;
    }

    // 设置PopWindow

    // 设置PopWindow
    /* 设置数据 */
    private void setData(final List<String> str, final TextView tv) {

        SpinerAdapter mAdapter = new SpinerAdapter(getActivity(), str);
        mAdapter.refreshData(str, 0);
        SpinnerPopWindow mSpinerPopWindow = new SpinnerPopWindow(getActivity());
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(new IOnItemSelectListener() {

            @Override
            public void onItemClick(int pos) {
                Log.i("mSpinerPopWindow.lisn", "执行了");
                if (pos >= 0 && pos <= str.size()) {
                    String value = str.get(pos);
                    tv.setText(value.toString());
                }
            }
        });
        mSpinerPopWindow.setWidth(tv.getWidth());
        mSpinerPopWindow.showAsDropDown(tv);

    }

    private void setListeners() {
        bt_getTestCode.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
        sp_province.setOnClickListener(this);
        sp_city.setOnClickListener(this);
        sp_store.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
		/* 点击获取验证码 */
            case R.id.nrFragment_bt_getTestCode:
                String phoneNumber = et_phoneNumber.getText().toString();
                if (!phoneNumber.matches("^[\\d]{11}")) {
                    MyToast.Toast(getActivity(), "手机号码不正确");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    MyToast.Toast(getActivity(), "手机号码为空");
                    return;
                }


			/*使按钮倒计时*/
//				CountDown.countDownTime(bt_getTestCode);
                countDown.countDownTime(bt_getTestCode);
			/* 发网络请求给手机发验证码 */
                GetSMSCode(phoneNumber);

                break;
		/* 点击提交 */
            case R.id.nrFragment_iv_commit:
                phoneNumber = et_phoneNumber.getText().toString();
                String smsTestCode = et_testCode.getText().toString();
                String passWord = et_passWord.getText().toString();

                StringBuilder builder = new StringBuilder();
                if (TextUtils.isEmpty(phoneNumber)) {
                    builder.append("手机号为空\n");
                }
                if (TextUtils.isEmpty(smsTestCode)) {
                    builder.append("验证码为空\n");
                }
                if (TextUtils.isEmpty(passWord)) {
                    builder.append("密码为空\n");
                }

                if (!TextUtils.isEmpty(builder.toString())) {
                    MyToast.Toast(getActivity(), builder.toString());
                    return;
                }
                if (!phoneNumber.matches("^[\\d]{11}")) {
                    MyToast.Toast(getActivity(), "手机号码不正确");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    MyToast.Toast(getActivity(), "手机号码为空");
                    return;
                }
                if (!passWord.matches("[a-zA-Z\\d]{6,16}")) {
                    MyToast.Toast(getActivity(), "密码请填写6-16位字母和数据");
                    return;
                }
                if (sid == null) {
                    MyToast.Toast(getActivity(), "请先选择商铺");
                    return;
                }

			/* 发网络请求注册 */
                Tools.showProgressDialog(getActivity());
                smsInviteRegister(phoneNumber, smsTestCode, passWord);

                break;

            case R.id.nrFragment_sp_province:
                // volley发请求去获取省份并显示
                getShowProvince();

                break;

            case R.id.nrFragment_sp_city:
                // volley发请求去获取城市并显示

                getShowCity();

                break;


            case R.id.nrFragment_sp_store:
                // volley发请求去获取商铺并显示
                getShowStore();
                break;

            default:
                break;
        }
    }

    /**
     * 发网络请求注册
     *
     * @param phoneNumber 手机号
     * @param smsTestCode 　短信码
     * @param passWord    　密码
     */
    private void smsInviteRegister(final String phoneNumber,
                                   String smsTestCode, final String passWord) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mode", Consts.TRADES_REGISTER);
        params.put("mobile", phoneNumber);
        params.put("password", passWord);
        params.put("sms", smsTestCode);
        params.put("uuid", TApplication.UUID);
        params.put("sid", sid);

        VolleyRequestTool.RequestPost(getActivity(), Consts.baseUrl
                        + Consts.APPRegisterUrl + Consts.ogid, Consts.APPRegisterUrl,
                params, new VolleyListenerInterface(this,
                        VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("result", result);
                        try {
                            ErrorCodeEntity errorCode = JSON.parseObject(
                                    result, ErrorCodeEntity.class);
                            switch (Integer.valueOf(errorCode.getError())) {
                                // 通过
                                case 0:
                                    MyToast.Toast(getActivity(), "注册成功");
                                    // 把注册的账号密码传过去
                                    Intent intent = new Intent();
                                    intent.putExtra("phoneNumber", phoneNumber);
                                    intent.putExtra("passWord", passWord);
                                    // intent.setClass(getActivity(),
                                    // LoginActivity.class);
                                    getActivity().setResult(1, intent);
                                    // getActivity().startActivity(intent);
                                    getActivity().finish();
                                    break;
                                // 短信验证码错误
                                case -2:
                                    MyToast.Toast(getActivity(), "短信验证码错误");
                                    break;

                                // 任意项为空
                                case -3:
                                    MyToast.Toast(getActivity(), "任意项为空");
                                    break;
                                // 邀请码错误
                                case -9:
                                    MyToast.Toast(getActivity(), "邀请码错误");
                                    break;

                            }
                        } catch (Exception e) {
                            Log.i("Exception", e.getMessage());
                            MyExceptionToast.Toast(getActivity(),
                                    e.getMessage());
                        } finally {
                            Tools.closeProgressDialog();
                        }

                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        // volley出现错误的时候执行onMyError的方法
                        GetErrorBackTool.gerErrorBack(getActivity());

                    }
                });
    }

    /**
     * volley发请求去获取商铺并显示
     */
    private void getShowStore() {
        StringRequest stringRequest = new StringRequest(Method.POST,
                Consts.baseUrl + Consts.APPGetStoreMessage + Consts.ogid,
                new Listener<String>() {
                    @Override
                    public void onResponse(String arg0) {
                        Log.i("执行到了getShowTreads", arg0);
                        GetStore getStore = JSON.parseObject(arg0,
                                GetStore.class);
                        // Log.i("friendsListe",
                        // getStore.getError().toString());
                        // Log.i("friendsListe",
                        // getStore.getList().get(0).toString());
                        if ("0".equals(getStore.getError())) {
                            List<Store> store = getStore.getList();

                            // Log.i("city", store.get(0).toString());

                            List<String> str = new ArrayList<String>();
                            if (store != null && store.size() > 0) {
                                sid = store.get(0).getId();
                                for (int i = 0; i < store.size(); i++) {
                                    str.add(store.get(i).getMallname());
                                }
                                setData(str, tv_store);
                            } else {
                                MyToast.Toast(getActivity(), "未获取到数据");
                            }
                        } else {
                            MyToast.Toast(getActivity(), "未获取到数据");
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.i("执行到了", "getParams");
                Map<String, String> map = new HashMap<String, String>();
                map.put("step", "5");
                map.put("chengshi", tv_city.getText().toString());

                return map;

            }
        };
        stringRequest.setTag("getfriendslist");
        TApplication.queue.add(stringRequest);
        TApplication.queue.start();
    }


    /**
     * volley发请求去获取省份并显示
     */
    private void getShowProvince() {
        StringRequest stringRequest = new StringRequest(Method.POST,
                Consts.baseUrl + Consts.APPGetStoreMessage + Consts.ogid,
                new Listener<String>() {

                    @Override
                    public void onResponse(String arg0) {
                        Log.i("执行到了RegisterGetStoreBiz", arg0);
                        GetProvince getprovince = JSON.parseObject(arg0,
                                GetProvince.class);
                        // Log.i("friendsListe",
                        // getprovince.getError().toString());
                        // Log.i("friendsListe",
                        // getprovince.getList().get(0).toString());
                        if ("0".equals(getprovince.getError())) {
                            List<Province> province = getprovince.getList();

                            // Log.i("province", province.get(0).toString());

                            List<String> str = new ArrayList<String>();
                            if (province != null && province.size() > 0) {
                                for (int i = 0; i < province.size(); i++) {
                                    str.add(province.get(i).getShengfen());
                                }
                                setData(str, tv_province);
                            } else {
                                MyToast.Toast(getActivity(), "未获取到数据");
                            }
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                GetErrorBackTool.gerErrorBack(getActivity());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.i("执行到了", "getParams");
                Map<String, String> map = new HashMap<String, String>();
                map.put("step", "1");

                return map;

            }
        };
        stringRequest.setTag("getfriendslist");
        TApplication.queue.add(stringRequest);
        TApplication.queue.start();
    }

    /**
     * volley发请求去获取城市并显示
     */
    private void getShowCity() {
        StringRequest stringRequest = new StringRequest(Method.POST,
                Consts.baseUrl + Consts.APPGetStoreMessage + Consts.ogid,
                new Listener<String>() {
                    @Override
                    public void onResponse(String arg0) {
                        Log.i("执行到了getCity", arg0);
                        GetCity getCity = JSON.parseObject(arg0, GetCity.class);
                        // Log.i("friendsListe", getCity.getError().toString());
                        // Log.i("friendsListe",
                        // getCity.getList().get(0).toString());
                        if ("0".equals(getCity.getError())) {
                            List<City> city = getCity.getList();

                            // Log.i("city", city.get(0).toString());

                            List<String> str = new ArrayList<String>();
                            if (city != null && city.size() > 0) {
                                for (int i = 0; i < city.size(); i++) {
                                    str.add(city.get(i).getChengshi());
                                }
                                setData(str, tv_city);
                            } else {
                                MyToast.Toast(getActivity(), "未获取到数据");
                            }
                        } else {
                            MyToast.Toast(getActivity(), "未获取到数据");
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                GetErrorBackTool.gerErrorBack(getActivity());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.i("执行到了", "getParams");
                Map<String, String> map = new HashMap<String, String>();
                map.put("step", "2");
                map.put("shengfen", tv_province.getText().toString());

                return map;

            }
        };
        stringRequest.setTag("getfriendslist");
        TApplication.queue.add(stringRequest);
        TApplication.queue.start();
    }

    private void initViews() {
		/* 省份spinner */
        sp_province = (RelativeLayout) view
                .findViewById(R.id.nrFragment_sp_province);
        tv_province = (TextView) view.findViewById(R.id.nrFragment_tv_province);
		/* 城市spinner */
        sp_city = (RelativeLayout) view.findViewById(R.id.nrFragment_sp_city);
        tv_city = (TextView) view.findViewById(R.id.nrFragment_tv_city);
		/* 商铺spinner */
        sp_store = (RelativeLayout) view.findViewById(R.id.nrFragment_sp_store);
        tv_store = (TextView) view.findViewById(R.id.nrFragment_tv_store);
		/* 手机号 */
        et_phoneNumber = (EditText) view
                .findViewById(R.id.nrFragment_et_phoneNumber);
		/* 密码 */
        et_passWord = (EditText) view
                .findViewById(R.id.nrFragment_passWord_input);
		/* 验证码 */
        et_testCode = (EditText) view.findViewById(R.id.nrFragment_et_testCode);
		/* 获取验证码 */
        bt_getTestCode = (Button) view
                .findViewById(R.id.nrFragment_bt_getTestCode);
		/* 提交 */
        bt_commit = (Button) view.findViewById(R.id.nrFragment_iv_commit);
    }

    /**
     * 发网络请求给手机发验证码
     */
    private void GetSMSCode(String phoneNumber) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", phoneNumber);
        VolleyRequestTool.RequestPost(getActivity(), Consts.baseUrl
                        + Consts.getPhoneTestCodeUrl + Consts.ogid,
                Consts.getPhoneTestCodeUrl, params,
                new VolleyListenerInterface(this,
                        VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("result", result);
                        try {
                            ErrorCodeEntity errorCode = JSON.parseObject(
                                    result, ErrorCodeEntity.class);
                            switch (Integer.valueOf(errorCode.getError())) {
                                // 通过
                                case 0:
                                    MyToast.Toast(getActivity(), "请接收验证码");
                                    break;
                                // 手机号已被使用
                                case -1:
                                    MyToast.Toast(getActivity(), "手机号已被使用");
                                    countDown.getTimer().cancel();
                                    countDown.getTimer().onFinish();
                                    break;

                            }
                        } catch (Exception e) {
                            Log.i("Exception", e.getMessage());
                            MyExceptionToast.Toast(getActivity(),
                                    e.getMessage());
                        } finally {
                            Tools.closeProgressDialog();
                        }

                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        // volley出现错误的时候执行onMyError的方法
                        GetErrorBackTool.gerErrorBack(getActivity());

                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        CountDown.getTimer().cancel();
    }

}
