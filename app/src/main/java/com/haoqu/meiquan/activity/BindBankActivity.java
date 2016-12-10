package com.haoqu.meiquan.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.adapter.SpinerAdapter;
import com.haoqu.meiquan.adapter.SpinerAdapter.IOnItemSelectListener;
import com.haoqu.meiquan.entity.ErrorCodeEntity;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.MyToast;
import com.haoqu.meiquan.view.SpinnerPopWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindBankActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_back;
	private ImageView rl_choseBank;
	private EditText et_realName;
	private EditText et_bankId;
	private EditText et_reBankId;
	private EditText et_bankName;
	private Button ib_bindBank;
	private EditText et_bankBank;
	private List<String> allBankName = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_bank);

		allBankName.add("中国银行");
		allBankName.add("工商银行");
		allBankName.add("建设银行");
		allBankName.add("农业银行");
		allBankName.add("招商银行");
		allBankName.add("广发银行");
		allBankName.add("民生银行");
		allBankName.add("兴业银行");



		initView();
		setListener();

	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		//后退按钮
		iv_back = (ImageView) findViewById(R.id.bbActivity_iv_back);
		//请选择开户行
		rl_choseBank = (ImageView)findViewById(R.id.bbActivity_iv_choseBank);
		//选择开户行后显示的银行名称
		et_bankBank = (EditText) findViewById(R.id.bbActivity_et_bankbank);
		//请输入开户人姓名
		et_realName = (EditText)findViewById(R.id.bbActivity_et_realName);
		//请输入银行卡号
		et_bankId = (EditText) findViewById(R.id.bbActivity_et_bankId);
		//请再次确认输入银行卡号
		et_reBankId = (EditText) findViewById(R.id.bbActivity_et_reBankId);
		//请输入开卡行详细地址
		et_bankName = (EditText) findViewById(R.id.bbActivity_et_bankName);
		//绑定此银行卡
		ib_bindBank = (Button) findViewById(R.id.bbActivity_ib_bindBank);

	}
	/**
	 * 设置监听
	 */
	private void setListener() {
		iv_back.setOnClickListener(this);
		rl_choseBank.setOnClickListener(this);
		ib_bindBank.setOnClickListener(this);
	}
	/**
	 * 点击监听
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//按了回退键
			case R.id.bbActivity_iv_back:
				finish();
				break;
			//按了选择银行
			case R.id.bbActivity_iv_choseBank:
				//弹出下拉列表
				popuWindow();


				break;
			//按了提交绑定此银行卡
			case R.id.bbActivity_ib_bindBank:
				//先验证各项填写是否正确
				String bankBank = et_bankBank.getText().toString();
				String realName = et_realName.getText().toString();
				String bankId = et_bankId.getText().toString();
				String reBankId = et_reBankId.getText().toString();
				String bankName = et_bankName.getText().toString();

				//判断内容是否都合格
				if(!reBankId.equals(bankId)){
					MyToast.Toast(getApplicationContext(), "请确认两次卡号");
					return;
				}
				StringBuilder builder = new StringBuilder();
				if (TextUtils.isEmpty(bankBank)) {
					builder.append("开户行为空\n");
				}
				if (TextUtils.isEmpty(realName)) {
					builder.append("开户人姓名为空\n");
				}
				if (TextUtils.isEmpty(bankId)) {
					builder.append("银行卡号为空\n");
				}
				if (TextUtils.isEmpty(bankName)) {
					builder.append("开卡行详细地址为空\n");
				}

				if (!TextUtils.isEmpty(builder.toString())) {
					MyToast.Toast(getApplicationContext(), builder.toString());
					return;
				}

				//网络提交绑定信息
				Tools.showProgressDialog(this);
				bindBank(bankBank, realName, bankId, bankName);
				break;
			default:
				break;
		}
	}
	/**
	 * 弹出下拉列表
	 */
	private void popuWindow() {
		SpinerAdapter adapter = new SpinerAdapter(getApplicationContext(), allBankName);
		adapter.refreshData(allBankName, 0);
		SpinnerPopWindow spinerPopWindow = new SpinnerPopWindow(getApplicationContext());
		spinerPopWindow.setAdatper(adapter);
		spinerPopWindow.setItemListener(new IOnItemSelectListener() {

			@Override
			public void onItemClick(int pos) {
				if (pos >= 0 && pos <= allBankName.size()){
					String value = allBankName.get(pos);
					et_bankName.setText(value.toString());
				}
			}
		});
		spinerPopWindow.setWidth(et_bankName.getWidth());
		spinerPopWindow.showAsDropDown(et_bankName);
	}
	/**
	 * 绑定银行卡去发网络请求
	 * @param bankBank　开户行名字
	 * @param realName　姓名
	 * @param bankId　银行卡号
	 * @param bankName　银行名字
	 */
	private void bindBank(String bankBank, String realName, String bankId,
						  String bankName) {
		//请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("realname", realName);
		params.put("bankname", bankName);
		params.put("bankid", bankId);
		params.put("bankbank", bankBank);
		params.put("uid", TApplication.uid);
		params.put("token", TApplication.token);

		VolleyRequestTool.RequestPost(
				getApplicationContext(),
				Consts.baseUrl+ Consts.APPBindBank + Consts.ogid,
				Consts.APPBindBank,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						try {
							Log.i("BindBank", result);
							ErrorCodeEntity unBindBank = JSON.parseObject(result, ErrorCodeEntity.class);
							switch (Integer.valueOf(unBindBank.getError())) {
								//绑定成功
								case 0:
									MyToast.Toast(BindBankActivity.this, "绑定成功！");

									finish();
									break;
								//任意项为空
								case -3:
									MyToast.Toast(BindBankActivity.this, "任意项为空");
									break;
								//已有绑定信息
								case -11:
									MyToast.Toast(BindBankActivity.this, "已有绑定信息");
									break;
								//令牌错误
								case -100:
								/*执行登出*/
									LoginOut.logOut(BindBankActivity.this);
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
						// TODO Auto-generated method stub
						//volley出现错误的时候执行onMyError的方法
						GetErrorBackTool.gerErrorBack(getApplicationContext());
					}
				});
	}




}
