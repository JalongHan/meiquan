package com.haoqu.meiquan.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.entity.ErrorCodeEntity;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.CountDown;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.MyToast;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassWordActivity extends BaseActivity implements OnClickListener {

	private EditText et_phoneNumber;
	private EditText et_testCode;
	private EditText et_NewPassWord1;
	private EditText et_NewPassWord2;
	private ImageView iv_back;
	private Button bt_getTestCode;
	private Button bt_updateAcknowledge;
	private CountDown countDown = new CountDown();
    private LinearLayout ll1;
    private RelativeLayout rl_space;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pass_word);
		/*控件初始化*/
		initViews();

		setListeners();
	}




    /**
	 * 控件初始化*/
	private void initViews() {
		/*手机号*/
		et_phoneNumber = (EditText) findViewById(R.id.fpActivity_et_phoneNumber);
		/*获取验证码*/
		bt_getTestCode = (Button) findViewById(R.id.fpActivity_bt_getTestCode);
		/*输入验证码*/
		et_testCode = (EditText) findViewById(R.id.fpActivity_et_testCode);
		/*输入新密码1*/
		et_NewPassWord1 = (EditText) findViewById(R.id.fpActivity_et_newPassWord1);
		/*输入新密码2*/
		et_NewPassWord2 = (EditText) findViewById(R.id.fpActivity_et_newPassWord2);
		/*确认修改按钮*/
		bt_updateAcknowledge = (Button) findViewById(R.id.fpActivity_bt_updateAcknowledge);
		/*返回按钮*/
		iv_back = (ImageView) findViewById(R.id.fpActivity_iv_back);

	}
	/**
	 * 添加监听
	 */
	private void setListeners() {
		iv_back.setOnClickListener(this);
		bt_getTestCode.setOnClickListener(this);
		bt_updateAcknowledge.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//点击回退按钮
			case R.id.fpActivity_iv_back:
				finish();
				break;
			//点击获取验证码
			case R.id.fpActivity_bt_getTestCode:
				String phoneNumber = et_phoneNumber.getText().toString();
				if(!phoneNumber.matches("^[\\d]{11}")){

					MyToast.Toast(getApplicationContext(), "手机号码不正确");
					return;
				}
				if (TextUtils.isEmpty(phoneNumber)) {
					MyToast.Toast(getApplicationContext(), "手机号码为空");
					return;
				}
			    /*使按钮倒计时*/

                countDown.countDownTime(bt_getTestCode);

                /*发网络请求给手机发验证码*/
                getSMSCode(phoneNumber);

				break;
			//点击找回密码
			case R.id.fpActivity_bt_updateAcknowledge:
				phoneNumber = et_phoneNumber.getText().toString();
				String testCode = et_testCode.getText().toString();
				String newPassWord1 = et_NewPassWord1.getText().toString();
				String newPassWord2 = et_NewPassWord2.getText().toString();

			/*判断号码是否为空*/
				StringBuilder builder = new StringBuilder();
				if (TextUtils.isEmpty(phoneNumber)) {
					builder.append("手机号为空\n");
				}
				if (TextUtils.isEmpty(testCode)) {
					builder.append("验证码为空\n");
				}
				if (TextUtils.isEmpty(newPassWord1)) {
					builder.append("密码1为空\n");
				}
				if (TextUtils.isEmpty(newPassWord2)) {
					builder.append("密码2为空\n");
				}
				if (!TextUtils.isEmpty(builder.toString())) {
					MyToast.Toast(getApplicationContext(), builder.toString());
					return;
				}
				if(!newPassWord1.equals(newPassWord2)){
					MyToast.Toast(getApplicationContext(), "新密码输入不同,请重新输入");
					return;
				}
			/*发网络请求找回密码*/
				Tools.showProgressDialog(this);

				findPassWord(phoneNumber, testCode, newPassWord1);

				break;
			default:
				break;
		}
	}
	/**
	 * 发送网络请求去获取验证码
	 * @param phoneNumber　手机号
	 */
	private void getSMSCode(String phoneNumber) {
		Map<String, String > params = new HashMap<String, String>();
		params.put("mobile", phoneNumber);
		VolleyRequestTool.RequestPost(
				getApplicationContext(),
				Consts.baseUrl+ Consts.APPGetBackPassWordSMS + Consts.ogid,
				Consts.APPGetBackPassWordSMS,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							ErrorCodeEntity code = JSON.parseObject(result, ErrorCodeEntity.class);
							switch (Integer.valueOf(code.getError())) {
								//找回成功
								case 0:
									MyToast.Toast(getApplication(), "请接收验证码");
									break;
								//短信验证码错误
								case -1:
									MyToast.Toast(getApplication(), "手机号已被使用");
                                    countDown.getTimer().cancel();
                                    countDown.getTimer().onFinish();
									break;

							}
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
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
	 * 找回密码
	 * @param phoneNumber　手机号
	 * @param testCode　验证码
	 * @param newPassWord1　密码
	 */
	private void findPassWord(String phoneNumber, String testCode,
							  String newPassWord1) {
		Map<String, String > map = new HashMap<String, String>();
		map.put("mobile", phoneNumber);
		map.put("password", newPassWord1);
		map.put("sms", testCode);

		VolleyRequestTool.RequestPost(
				ForgetPassWordActivity.this,
				Consts.baseUrl+ Consts.AppGetBackPassWord + Consts.ogid,
				Consts.AppGetBackPassWord,
				map,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							ErrorCodeEntity code = JSON.parseObject(result, ErrorCodeEntity.class);
							switch (Integer.valueOf(code.getError())) {
								//找回成功
								case 0:
									MyToast.Toast(getApplication(), "找回成功");
									finish();
									break;
								//短信验证码错误
								case -2:
									MyToast.Toast(getApplication(), "短信验证码错误");
									break;
								//任意项为空
								case -3:
									MyToast.Toast(getApplication(), "任意项为空");
									break;
								default:

									break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
							Tools.closeProgressDialog();
						}

					}

					@Override
					public void onMyError(VolleyError error) {
						Log.i("onMyError", error.getMessage());
						//volley出现错误的时候执行onMyError的方法
						GetErrorBackTool.gerErrorBack(getApplicationContext());

					}
				});
	}

    @Override
    protected void onDestroy() {
        if (countDown.getTimer() != null){
            countDown.getTimer().cancel();
        }

        super.onDestroy();

    }
}
