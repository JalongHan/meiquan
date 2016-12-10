package com.haoqu.meiquan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.entity.LoginEntity;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.NetworkUtil;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.MyToast;
import com.nostra13.universalimageloader.utils.DiscCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private EditText et_account;
    private EditText et_passWord;
    private Button bt_login;
    private TextView bt_iWantRegister;
    private TextView bt_forgort;
    private String account;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            setContentView(R.layout.activity_login);

            SharedPreferences sp = getSharedPreferences("login",
                    Context.MODE_PRIVATE);
            /*如果有存过就取出值来直接登陆,取不到值说明没登陆过，先检查网络*/
            Log.i("boolean", String.valueOf(sp.contains("phonekey")));
            if (sp.contains("phonekey")) {
//				TApplication.uid = sp.getString("uid", "");
//				TApplication.token = sp.getString("token", "");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                NetworkUtil.checkNetworkState(this);
            }

			/* 初始化控件 */
            initView();


            setLinisteners();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setLinisteners() {
        bt_login.setOnClickListener(this);
        bt_iWantRegister.setOnClickListener(this);
        bt_forgort.setOnClickListener(this);
    }

    /**
     * 设置监听器
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登陆按钮
            case R.id.lActivity_bt_login:
            /* 登陆 */
                account = et_account.getText().toString();
                passWord = et_passWord.getText().toString();

                if (!account.matches("^[\\d]{11}")) {
                    MyToast.Toast(getApplicationContext(), "手机号码不正确");
                    return;
                }
                if (TextUtils.isEmpty(account)) {
                    MyToast.Toast(LoginActivity.this, "手机号码为空");
                    return;
                }
                if (!passWord.matches("^[a-zA-Z\\d]{6,16}$")) {
                    MyToast.Toast(LoginActivity.this, "密码请填写6-16位字母和数字");
                    return;
                }

                StringBuilder builder = new StringBuilder();
                if (TextUtils.isEmpty(account)) {
                    builder.append("用户名为空\n");
                }
                if (TextUtils.isEmpty(passWord)) {
                    builder.append("密码为空\n");
                }
                if (!TextUtils.isEmpty(builder.toString())) {
                    Toast.makeText(LoginActivity.this, builder.toString(),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //执行登陆业务
                Tools.showProgressDialog(LoginActivity.this);
                Login();

                break;
            //注册按钮
            case R.id.lActivity_bt_register:
//			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, 0);
                break;
            //忘记密码按钮
            case R.id.lActivity_bt_forgetPassword:
                startActivity(new Intent(LoginActivity.this,
                        ForgetPassWordActivity.class));
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        Log.i("requestCode", String.valueOf(requestCode));
        Log.i("resultCode", String.valueOf(resultCode));
        switch (resultCode) {
            case 1:
                String phone = data.getStringExtra("phoneNumber");
                String pasword = data.getStringExtra("passWord");
                et_account.setText(phone);
                et_passWord.setText(pasword);
                break;

            default:
                break;
        }

    }

    /**
     * 登陆
     */
    private void Login() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", account);
        params.put("password", passWord);
        params.put("uuid", TApplication.UUID);
        VolleyRequestTool.RequestPost(
                getApplicationContext(),
                Consts.baseUrl + Consts.loginUrl + Consts.ogid,
                Consts.loginUrl,
                params,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {

                    @Override
                    public void onMySuccess(String result) {
                        Log.i("result", result);
                        try {
                            LoginEntity loginEntity = JSON.parseObject(result,
                                    LoginEntity.class);
                            Tools.closeProgressDialog();
                            switch (Integer.valueOf(loginEntity.getError())) {
                                // 通过
                                case 0:
                                    TApplication.token = loginEntity.getToken();
                                    TApplication.uid = loginEntity.getUid();
                                    TApplication.avatar = loginEntity.getAvatar();
                                    TApplication.nickname = loginEntity.getNickname();
                                    TApplication.mobile = loginEntity.getMobile();
                                    // 用偏好设置来存储用户名和密码 和其它 信息
                                    SharedPreToSave(loginEntity);

                                    startActivity(new Intent(LoginActivity.this,
                                            MainActivity.class));
                                    LoginActivity.this.finish();
                                    break;
                                case -2:
                                    MyToast.Toast(LoginActivity.this, "账号或密码错误");
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
                        //volley出现错误的时候执行onMyError的方法
                        GetErrorBackTool.gerErrorBack(getApplicationContext());

                    }
                });

    }

    /**
     * 用偏好设置来存储用户名和密码记录登陆信息
     */
    private void SharedPreToSave(LoginEntity loginEntity) {
        SharedPreferences sp = getSharedPreferences("login",
                Context.MODE_PRIVATE);
        Editor et = sp.edit();
        et.putString("phonekey", account);
        et.putString("pasowrd", passWord);
        et.putString("token", loginEntity.getToken());
        et.putString("uid", loginEntity.getUid());
        et.putString("sid", loginEntity.getSid());
        et.putString("level", loginEntity.getLevel());
        et.putString("mobile", loginEntity.getMobile());
        et.putString("nickname", loginEntity.getNickname());
        et.putString("avatar", loginEntity.getAvatar());
        //清除一下imageloader中缓存的图片.因为网址是一样的,让其它地方的正常显示
        MemoryCacheUtils.removeFromCache(loginEntity.getAvatar(), TApplication.imageLoader.getMemoryCache());
        DiscCacheUtils.removeFromCache(loginEntity.getAvatar(), TApplication.imageLoader.getDiscCache());
        et.commit();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        /* 账号输入控件 */
        et_account = (EditText) findViewById(R.id.lActivity_account_input);
        /* 密码输入控件 */
        et_passWord = (EditText) findViewById(R.id.lActivity_passWord_input);
        /* 登陆按钮 */
        bt_login = (Button) findViewById(R.id.lActivity_bt_login);
		/* 我要注册按钮 */
        bt_iWantRegister = (TextView) findViewById(R.id.lActivity_bt_register);
		/* 忘记密码按钮 */
        bt_forgort = (TextView) findViewById(R.id.lActivity_bt_forgetPassword);

    }

}
