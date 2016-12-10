package com.haoqu.meiquan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.entity.ErrorCodeEntity;
import com.haoqu.meiquan.entity.GetBankInfoEntity;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.MyToast;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

public class MyBankActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_back;
	private ImageView iv_bankIcon;
	private TextView tv_bankName;
	private TextView tv_cardType;
	private TextView tv_cardNumber;
	private TextView tv_shortCardNumber;
	private RelativeLayout rl_unBindBank;
	private RelativeLayout rl_bankInfo;
	//银行图标
	Map<String, Integer> map = new HashMap<String, Integer>();
	/* Android开源框架Universal-Image-Loader */
	ImageLoader imageLoader;
	private RelativeLayout rl_nobankcard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_bank);
		imageLoader = ImageLoader.getInstance();
		map.put("中国银行", R.mipmap.zhongguohang);
		map.put("工商银行", R.mipmap.gongshanghang);
		map.put("建设银行", R.mipmap.jianshehang);
		map.put("农业银行", R.mipmap.nongyehang);
		map.put("招商银行", R.mipmap.zhaoshanghang);
		map.put("广发银行", R.mipmap.guangfahang);
		map.put("民生银行", R.mipmap.minshenghang);
		map.put("兴业银行", R.mipmap.xingyehang);


		initView();
		setListener();
		getBankInfo();

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getBankInfo();
	}
	/**
	 * 解绑银行卡
	 */
	private void UnBindBank() {
		Tools.showProgressDialog(this);
		//请求参数
		Map<String, String > params = new HashMap<String, String>();
		params.put("uid", TApplication.uid);
		params.put("token", TApplication.token);
		VolleyRequestTool.RequestPost(
				getApplicationContext(),
				Consts.baseUrl+ Consts.APPUnBindBank + Consts.ogid,
				Consts.APPUnBindBank,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							ErrorCodeEntity unBindBank = JSON.parseObject(result, ErrorCodeEntity.class);
							switch (Integer.valueOf(unBindBank.getError())) {
								//解绑成功
								case 0:
									MyToast.Toast(getApplicationContext(), "解绑成功");
									rl_bankInfo.setVisibility(View.GONE);
									rl_unBindBank.setVisibility(View.GONE);
									rl_nobankcard.setVisibility(View.VISIBLE);
									break;
								//未绑定银行卡
								case -1:
									MyToast.Toast(getApplicationContext(), "未绑定银行卡");
									break;
								//令牌错误
								case -100:
									LoginOut.logOut(MyBankActivity.this);
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
						//volley出现错误的时候执行onMyError的方法
						GetErrorBackTool.gerErrorBack(getApplicationContext());

					}
				});


	}

	/**
	 * 获取银行卡绑定信息
	 */
	private void getBankInfo() {
		Tools.showProgressDialog(this);

		Map<String, String > params = new HashMap<String, String>();
		params.put("uid", TApplication.uid);
		params.put("token", TApplication.token);
		VolleyRequestTool.RequestPost(
				getApplicationContext(),
				Consts.baseUrl+ Consts.APPGetBankInfo + Consts.ogid,
				Consts.APPGetBankInfo,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("getBankInfo", result);
						try {

							GetBankInfoEntity bankInfo = JSON.parseObject(result, GetBankInfoEntity.class);
							switch (Integer.valueOf(bankInfo.getError())) {
								//通过
								case 0:
									String bankName = bankInfo.getBankname();
									tv_bankName.setText(bankName);
									if(map.get(bankName)!=null){
										iv_bankIcon.setImageResource(map.get(bankName));
									}
									String str = bankInfo.getBankid();
									if(str.length() > 4){
										tv_shortCardNumber.setText(str.substring(str.length()-4));
									}
									rl_bankInfo.setVisibility(View.VISIBLE);
									rl_unBindBank.setVisibility(View.VISIBLE);
									rl_nobankcard.setVisibility(View.GONE);

									break;
								//未绑定银行卡
								case -1:

									break;
								//令牌错误
								case -100:
									LoginOut.logOut(MyBankActivity.this);
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

	private void initView() {
		//返回按钮
		iv_back = (ImageView)findViewById(R.id.mbActivity_iv_back);
		//无卡状态时显示的图片
		rl_nobankcard = (RelativeLayout) findViewById(R.id.mbActivity_rl_noBank);
		//银行图标
		iv_bankIcon = (ImageView)findViewById(R.id.mbActivity_bank_icon);
		//bankname
		tv_bankName = (TextView)findViewById(R.id.mbActivity_tv_bankName);
		//cardType卡种类
		tv_cardType = (TextView)findViewById(R.id.mbActivity_tv_cardType);
		//卡的号码**** **** ****
		tv_cardNumber = (TextView)findViewById(R.id.mbActivity_tv_cardNumber);
		//卡号后四位，shortCardNumber
		tv_shortCardNumber = (TextView)findViewById(R.id.mbActivity_tv_shortCardNumber);
		//bankinfo
		rl_bankInfo = (RelativeLayout)findViewById(R.id.mbActivity_rl_bankInfo);
		//unBindBank
		rl_unBindBank = (RelativeLayout)findViewById(R.id.mbActivity_rl_unBindBank);


	}

	private void setListener() {
		iv_back.setOnClickListener(this);
		rl_unBindBank.setOnClickListener(this);
        rl_nobankcard.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//点了返回按钮，结束当前activity
			case R.id.mbActivity_iv_back:
				finish();
				break;
			//解除绑定银行卡
			case R.id.mbActivity_rl_unBindBank:
				//运行解绑方法
				new AlertDialog.Builder(this)
						.setTitle("您确定解绑银行卡吗？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								UnBindBank();
							}
						})
						.setNegativeButton("取消", null)
						.create().show();

				break;
			//绑定银行卡
			case R.id.mbActivity_rl_noBank:
				//点击跳转到绑定页面
				startActivity(new Intent(MyBankActivity.this, BindBankActivity.class));
				break;


			default:
				break;
		}
	}

}
