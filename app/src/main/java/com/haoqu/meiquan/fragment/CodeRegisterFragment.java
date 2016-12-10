package com.haoqu.meiquan.fragment;


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

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.entity.ErrorCodeEntity;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.CountDown;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.MyExceptionToast;
import com.haoqu.meiquan.view.MyToast;

import java.util.HashMap;
import java.util.Map;

public class CodeRegisterFragment extends Fragment implements OnClickListener{

	private View view;
	private EditText et_phoneNumber;
	private EditText et_testCode;
	private EditText et_passWord;
	private EditText et_inviteCode;
	private Button bt_commit;
	private Button bt_getTestCode;
	private String phoneNumber;
	private String smsTestCode;
	private String passWord;
	private String inviteCode;
    private CountDown countDown;


    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		countDown = new CountDown();
	}
	@Override
	public void onDestroy() {
//        CountDown.getTimer().cancel();
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_code_register, null);
		getView();
		initViews();

		setListeners();
		return view;
	}

	/*添加监听*/
	private void setListeners() {
		bt_getTestCode.setOnClickListener(this);
		bt_commit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.crFragment_bt_getTestCode:
				phoneNumber = et_phoneNumber.getText().toString();
				if(!phoneNumber.matches("^[\\d]{11}")){
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
			/*发网络请求给手机发验证码*/
				GetSMSCode();


				break;

			case R.id.crFragment_bt_commit:
				phoneNumber = et_phoneNumber.getText().toString();
				smsTestCode = et_testCode.getText().toString();
				passWord = et_passWord.getText().toString();
				inviteCode = et_inviteCode.getText().toString();
			/*判断各内容是否为空*/
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
				if (TextUtils.isEmpty(inviteCode)) {
					builder.append("邀请码为空\n");
				}
				if (!TextUtils.isEmpty(builder.toString())) {
					MyToast.Toast(getActivity(), builder.toString());
					return;
				}
				if(!phoneNumber.matches("^[\\d]{11}")){
					MyToast.Toast(getActivity(), "手机号码不正确");
					return;
				}
				if (TextUtils.isEmpty(phoneNumber)) {
					MyToast.Toast(getActivity(), "手机号码为空");
					return;
				}
				if(!passWord.matches("[a-zA-Z\\d]{6,16}")){
					MyToast.Toast(getActivity(), "密码请填写6-16位字母和数据");
					return;
				}


			/*发网络请求注册*/
				InviteRegister();

				break;


			default:
				break;
		}
	}

	/**
	 * 发网络请求注册
	 */
	private void InviteRegister() {
		Tools.showProgressDialog(getActivity());


		Map<String, String > params = new HashMap<String, String>();
		params.put("mode", Consts.INVITE_REGISTER);
		params.put("mobile", phoneNumber);
		params.put("password", passWord);
		params.put("uuid", TApplication.UUID);
		params.put("invitcode", inviteCode);
		params.put("sms", smsTestCode);


		VolleyRequestTool.RequestPost(
				getActivity(),
				Consts.baseUrl + Consts.APPRegisterUrl + Consts.ogid,
				Consts.APPRegisterUrl,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							ErrorCodeEntity errorCode = JSON.parseObject(result,
									ErrorCodeEntity.class);

							switch (Integer.valueOf(errorCode.getError())) {
								// 通过
								case 0:
									MyToast.Toast(getActivity(), "注册成功");
									//把注册的账号密码传过去
									Intent intent = new Intent();
									intent.putExtra("phoneNumber", phoneNumber);
									intent.putExtra("passWord", passWord);
//								intent.setClass(getActivity(), LoginActivity.class);
									getActivity().setResult(1,intent);
//								getActivity().startActivity(intent);
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
							MyExceptionToast.Toast(getActivity(), e.getMessage());
						}finally{
							Tools.closeProgressDialog();
						}

					}

					@Override
					public void onMyError(VolleyError error) {
						//volley出现错误的时候执行onMyError的方法
						GetErrorBackTool.gerErrorBack(getActivity());

					}
				});


	}
	/**
	 * 发网络请求给手机发验证码
	 */
	private void GetSMSCode() {

		Map<String, String > params = new HashMap<String, String>();
		params.put("mobile", phoneNumber);
		VolleyRequestTool.RequestPost(
				getActivity(),
				Consts.baseUrl + Consts.getPhoneTestCodeUrl + Consts.ogid,
				Consts.getPhoneTestCodeUrl,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							ErrorCodeEntity errorCode = JSON.parseObject(result,
									ErrorCodeEntity.class);
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
							MyExceptionToast.Toast(getActivity(), e.getMessage());
						}finally{
							Tools.closeProgressDialog();
						}

					}

					@Override
					public void onMyError(VolleyError error) {
						//volley出现错误的时候执行onMyError的方法
						GetErrorBackTool.gerErrorBack(getActivity());

					}
				});


	}



	/*控件初始化*/
	private void initViews() {
		/*手机号输入*/
		et_phoneNumber = (EditText) view.findViewById(R.id.crFragment_phoneNumber);
		/*验证码*/
		et_testCode = (EditText) view.findViewById(R.id.crFragment_et_testCode);
		/*获取验证码按钮*/
		bt_getTestCode = (Button) view.findViewById(R.id.crFragment_bt_getTestCode);
		/*密码输入*/
		et_passWord = (EditText) view.findViewById(R.id.crFragment_passWord_input);
		/*邀请码*/
		et_inviteCode = (EditText) view.findViewById(R.id.crFragment_et_inviteCode);
		/*提交按键*/
		bt_commit = (Button) view.findViewById(R.id.crFragment_bt_commit);
	}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        CountDown.getTimer().cancel();
        if (countDown.getTimer() != null) {
            countDown.getTimer().cancel();
        }
    }
}

