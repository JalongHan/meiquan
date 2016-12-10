package com.haoqu.meiquan.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.fragment.CodeRegisterFragment;
import com.haoqu.meiquan.fragment.NoCodeRegisterFragment;

import java.util.ArrayList;

public class RegisterActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {

	private ImageView iv_back;

	private TextView tv_codeRegister;

	private TextView tv_noCodeRegister;

	private ViewPager viewPager;

	private ArrayList<Fragment> fragments;

	private FragmentPagerAdapter adapter;

	private View v_line1;

	private View v_line2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		/*控件*/
		initViews();
		setListener();
		/*加载fragment*/
		fragments = new ArrayList<Fragment>();
		fragments.add(new CodeRegisterFragment());
		fragments.add(new NoCodeRegisterFragment());
		adapter = new FragmentPagerAdapter(getFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fragments.get(arg0);
			}
		};
		viewPager.setAdapter(adapter);
	}

	private void initViews() {
		/*返回按键*/
		iv_back = (ImageView) findViewById(R.id.rActivity_iv_back);
		/*邀请码注册文本*/
		tv_codeRegister = (TextView) findViewById(R.id.rActivity_tv_codeRegister);
		/*无邀请码注册文本*/
		tv_noCodeRegister = (TextView) findViewById(R.id.rActivity_tv_noCodeRegister);
		/*下面的viewpager*/
		viewPager = (ViewPager)findViewById(R.id.rActivity_viewPager);
		/*注册标题　下面的两条黄线*/
		v_line1 = (View)findViewById(R.id.rActivity_v_line1);
		v_line2 = (View)findViewById(R.id.rActivity_v_line2);

		/*默认有码注册是黄字*/
		tv_codeRegister.setTextColor(getResources().getColor(R.color.background_white));
		tv_noCodeRegister.setTextColor(getResources().getColor(R.color.register_title_nochose));
		v_line2.setVisibility(View.INVISIBLE);

	}

	private void setListener() {
		iv_back.setOnClickListener(this);
		tv_codeRegister.setOnClickListener(this);
		tv_noCodeRegister.setOnClickListener(this);
		viewPager.setOnPageChangeListener(this);

	}
	/*以下都为监听*/
	/*button点击的监听*/
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rActivity_iv_back:
				finish();
				break;
			case R.id.rActivity_tv_codeRegister:

				viewPager.setCurrentItem(0);
				tv_codeRegister.setTextColor(getResources().getColor(R.color.background_white));
				tv_noCodeRegister.setTextColor(getResources().getColor(R.color.register_title_nochose));
				v_line1.setVisibility(View.VISIBLE);
				v_line2.setVisibility(View.INVISIBLE);
				break;
			case R.id.rActivity_tv_noCodeRegister:
				viewPager.setCurrentItem(1);
				tv_codeRegister.setTextColor(getResources().getColor(R.color.register_title_nochose));
				tv_noCodeRegister.setTextColor(getResources().getColor(R.color.background_white));
				v_line1.setVisibility(View.INVISIBLE);
				v_line2.setVisibility(View.VISIBLE);
				break;
			case R.id.crFragment_bt_commit:
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				break;
			default:
				break;
		}
	}
	/*viewpager划动监听*/
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
			case 0:
				tv_codeRegister.setTextColor(getResources().getColor(R.color.background_white));
				tv_noCodeRegister.setTextColor(getResources().getColor(R.color.register_title_nochose));
				v_line1.setVisibility(View.VISIBLE);
				v_line2.setVisibility(View.INVISIBLE);
				break;
			case 1:
				tv_codeRegister.setTextColor(getResources().getColor(R.color.register_title_nochose));
				tv_noCodeRegister.setTextColor(getResources().getColor(R.color.background_white));
				v_line1.setVisibility(View.INVISIBLE);
				v_line2.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}


}
