package com.haoqu.meiquan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.adapter.TixianRecordAdapter;
import com.haoqu.meiquan.entity.TakeLogEntity;
import com.haoqu.meiquan.entity.TixianList;
import com.haoqu.meiquan.entity.TixianSumEntity;
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

public class TixianRecordActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_back;

	private PullToRefreshListView listView;

	private int page;

	protected List<TixianList> tixianList;

	protected TixianRecordAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian_record);
		page = 1;
		initView();
		setListener();
		Tools.showProgressDialog(this);
		TixianRecord();
		TixianSum();
	}
	/**
	 * 请求已提现金额
	 */
	private void TixianSum() {

		Map<String, String > params = new HashMap<String, String>();
		params.put("uid", TApplication.uid);
		params.put("token", TApplication.token);


		VolleyRequestTool.RequestPost(
				getApplicationContext(),
				Consts.baseUrl + Consts.APPMyMoney + Consts.ogid,
				Consts.APPMyMoney,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							TixianSumEntity takelog = JSON.parseObject(result,TixianSumEntity.class);
							switch (Integer.valueOf(takelog.getError())) {
								//通过
								case 0:
									String oktake = takelog.getOktake();
									if(oktake==null){
										oktake = "0";
									}

									break;
								//令牌错误
								case -100:
									LoginOut.logOut(TixianRecordActivity.this);
									break;
								default:
									break;

							}
						} catch (Exception e) {
							e.printStackTrace();
						}finally{

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
		// 后退按钮
		iv_back = (ImageView) findViewById(R.id.trActivity_iv_back);

		// listview
		listView = (PullToRefreshListView) findViewById(R.id.trActivity_listView);
		listView.setMode(Mode.PULL_FROM_END);
		// 设置PullRefreshListView上提加载时的加载提示
		listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
		listView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
				"正在加载...");
		listView.getLoadingLayoutProxy(false, true)
				.setReleaseLabel("松开加载更多...");
	}

	private void setListener() {
		iv_back.setOnClickListener(this);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				Log.i("setOnRefreshListener", "执行到了");
				try {
					moreTixianRecord();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
	}
	/**
	 * 获得更多的提现数据
	 */
	protected void moreTixianRecord() {

		Map<String, String > params = new HashMap<String, String>();
		params.put("uid", TApplication.uid);
		params.put("token", TApplication.token);
		params.put("page", String.valueOf(page));


		VolleyRequestTool.RequestPost(
				getApplicationContext(),
				Consts.baseUrl + Consts.APPTakeLog + Consts.ogid,
				Consts.APPTakeLog,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("moreTixianRecord", result);
						try {

							TakeLogEntity takelog = JSON.parseObject(result,TakeLogEntity.class);
							switch (Integer.valueOf(takelog.getError())) {
								//通过
								case 0:
									String total = takelog.getTotal();
									if("0".equals(total)){
										listView.onRefreshComplete();
										MyToast.Toast(getApplicationContext(), "没有更多数据了");
										return;
									}
									List<TixianList> moretixianList = takelog.getList();
									tixianList.addAll(moretixianList);
									page++;
									listView.onRefreshComplete();
									adapter.notifyDataSetChanged();
									break;
								//令牌错误
								case -100:
									LoginOut.logOut(TixianRecordActivity.this);
									break;
								default:
									break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
						}

					}

					@Override
					public void onMyError(VolleyError error) {
						//volley出现错误的时候执行onMyError的方法
						GetErrorBackTool.gerErrorBack(getApplicationContext());

					}
				});




	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.trActivity_iv_back:
				finish();
				break;

			default:
				break;
		}
	}

	/**
	 * 请求提现记录
	 */
	private void TixianRecord() {

		Map<String, String > params = new HashMap<String, String>();
		params.put("uid", TApplication.uid);
		params.put("token", TApplication.token);
		params.put("page", String.valueOf(page));


		VolleyRequestTool.RequestPost(
				getApplicationContext(),
				Consts.baseUrl + Consts.APPTakeLog + Consts.ogid,
				Consts.APPTakeLog,
				params,
				new VolleyListenerInterface(this,VolleyListenerInterface.mListener,VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							Log.i("TixianRecord", result);
							TakeLogEntity takelog = JSON.parseObject(result,TakeLogEntity.class);
							switch (Integer.valueOf(takelog.getError())) {
								//通过
								case 0:
									tixianList = takelog.getList();
									if(tixianList != null && tixianList.size()>0){
										adapter = new TixianRecordAdapter(getApplicationContext(), tixianList);
										listView.setAdapter(adapter);
										page++;
									}

									break;
								//令牌错误
								case -100:
									LoginOut.logOut(TixianRecordActivity.this);
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

}
