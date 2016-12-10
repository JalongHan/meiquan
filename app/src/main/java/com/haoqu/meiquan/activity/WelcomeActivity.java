package com.haoqu.meiquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import com.haoqu.meiquan.R;

public class WelcomeActivity extends BaseActivity implements OnClickListener {
	/*跳过广告textview*/
//	private TextView tv_skipAD;
	Handler handler = new Handler();
	private Runnable st;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		/*初始化控件*/
		initViews();
//		tv_skipAD.setOnClickListener(this);

		/*延迟两秒跳转页面*/

		st=new Runnable() {
			public void run() {
				toLogin();
			}
		};

		handler.postDelayed(st, 2000);
	}
	/**
	 * 初始化控件
	 */
	private void initViews() {
		/*跳过广告textview*/
//		tv_skipAD = (TextView)findViewById(R.id.wActivity_tv_skipAD);
	}

	/**
	 * 按钮的应用
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//			case R.id.wActivity_tv_skipAD:
//			/*跳转到登陆页面*/
//				handler.removeCallbacks(st);
//				toLogin();
//				break;
//
//			default:
//				break;
		}
	}
	/**
	 * 跳转到登陆页面
	 */
	private void toLogin() {
		Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
		startActivity(intent);
		finish();
	}



}
