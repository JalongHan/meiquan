package com.haoqu.meiquan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.adapter.SpActivityRefreshGrieViewAdapter;
import com.haoqu.meiquan.entity.AppointGoosList;
import com.haoqu.meiquan.entity.GetGoodsListEntity;
import com.haoqu.meiquan.tools.ACache;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.GetErrorBackTool;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.MyExceptionToast;
import com.haoqu.meiquan.view.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShangPinListActivity extends BaseActivity implements
		OnClickListener {


	private ImageView iv_back;

	private TextView tv_classify;

	private PullToRefreshGridView mPullRefreshGridView;

	private List<AppointGoosList> goodList = new ArrayList<AppointGoosList>();

	private SpActivityRefreshGrieViewAdapter adapter;

	private int page;

	private String cid;

	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shang_pin_list);

		initViews();
		setListener();
		/* 先获得一页数据 */
		getfirstPageDate();
	}

	private void getfirstPageDate() {
		page = 1;
		Intent intent = getIntent();
		cid = intent.getStringExtra(Consts.KEY_DATA);
		// 获取前一页传过来的title给当前面做标题
		name = intent.getStringExtra("name");
		tv_classify.setText(name);

		Log.i("cid", String.valueOf(cid));
		// getAppointGoodsListBiz.getAppointGoodsList(cid, page);
		GetDate();
		// new GetDataTask().execute();
		// page++;
	}

	@SuppressWarnings("unchecked")
	private void setListener() {
		iv_back.setOnClickListener(this);

		mPullRefreshGridView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				Log.i("TAG", "我在上拉");
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				page = 1;
				goodList.clear();
				GetDate();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				Log.i("TAG", "是拉到底了还是在哪啊");

				GetMoreDate();
			}
		});

		/* mPullRefreshGridView的点击事件 */
		GridView actualGridView = mPullRefreshGridView.getRefreshableView();
		actualGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				Intent intent = new Intent(ShangPinListActivity.this,
						GoosDetailsActivity.class);
				Log.i("TApplication.aglist.siz",
						String.valueOf(goodList.size()));
				intent.putExtra("web", goodList.get(position).getId());
				intent.putExtra("thumb", goodList.get(position).getThumb());
				Log.i("position",
						String.valueOf(goodList.get(position).getId()));
				startActivity(intent);

			}
		});
	}

	protected void GetDate() {
		Tools.showProgressDialog(this);

		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", TApplication.uid);
		params.put("token", TApplication.token);
		params.put("cid", String.valueOf(cid));
		params.put("page", String.valueOf(page));

		VolleyRequestTool.RequestPost(getApplicationContext(), Consts.baseUrl
						+ Consts.APPGetAppointGoodsList + Consts.ogid,
				Consts.APPGetAppointGoodsList, params,
				new VolleyListenerInterface(this,
						VolleyListenerInterface.mListener,
						VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							jsonParse(result,0);
						} catch (Exception e) {
							Log.i("Exception", e.getMessage());
							MyExceptionToast.Toast(getApplicationContext(),
									e.getMessage());
						} finally {
							Tools.closeProgressDialog();
						}

					}



					@Override
					public void onMyError(VolleyError error) {
						// volley出现错误的时候执行onMyError的方法
						GetErrorBackTool.gerErrorBack(getApplicationContext());
						ACache cache = ACache.get(ShangPinListActivity.this);
						String result = cache.getAsString(Consts.spDATA+cid);
//						Log.i("shangpinlist.result", String.valueOf(result.length()));
						if(result == null){
							Log.i("result", "null");
						}else{
							jsonParse(result,1);
						}
					}
				});

	}

	/**
	 * 将得到的字符串解析显示
	 * @param result
	 * @param  in 0的时候保存result 1的时候不保存
	 */
	private void jsonParse(String result,int in) {
		GetGoodsListEntity getGoodsList = JSON.parseObject(
				result, GetGoodsListEntity.class);
		switch (Integer.valueOf(getGoodsList.getError())) {
			// 成功
			case 0:
				if ("0".equals(getGoodsList.getTotal()
				)) {
					return;
				}
				goodList = getGoodsList.getList();
				if(goodList!=null && goodList.size()>0){
					adapter = new SpActivityRefreshGrieViewAdapter(
							getApplicationContext(), goodList);
					mPullRefreshGridView.setAdapter(adapter);

				}
				page++;
				mPullRefreshGridView.onRefreshComplete();


				switch (in) {
					case 0:
				/* 把得到的字符json字符串缓存起来 */
						Log.i("fragmentpsList", "把得到的字符json字符串缓存起来");
						ACache cache = ACache.get(ShangPinListActivity.this);
						cache.put(Consts.spDATA+cid, result);
						break;

					default:
						break;
				}

				break;
			// 令牌错误
			case -100:
				// 如果令牌错误的逻辑
				// 跳转到登陆界面
				// 发退出广播
				LoginOut.logOut(ShangPinListActivity.this);
				break;
			default:

				break;
		}
	}

	private void initViews() {
		iv_back = (ImageView) findViewById(R.id.spActivity_ib_back);
		tv_classify = (TextView) findViewById(R.id.spActivity_tv_classify);
		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		GridView gv = mPullRefreshGridView.getRefreshableView();
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mPullRefreshGridView.setMode(Mode.BOTH);
		// 设置mPullRefreshGridView上提加载时的加载提示
		mPullRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel(
				"上拉加载...");
		mPullRefreshGridView.getLoadingLayoutProxy(false, true)
				.setRefreshingLabel("正在加载...");
		mPullRefreshGridView.getLoadingLayoutProxy(false, true)
				.setReleaseLabel("松开加载更多...");

		// 设置mPullRefreshGridView下拉加载时的加载提示
		mPullRefreshGridView.getLoadingLayoutProxy(true, false).setPullLabel(
				"下拉刷新...");
		mPullRefreshGridView.getLoadingLayoutProxy(true, false)
				.setRefreshingLabel("正在刷新...");
		mPullRefreshGridView.getLoadingLayoutProxy(true, false)
				.setReleaseLabel("松开刷新...");

		// actualListView = pull_refresh_list.getRefreshableView();
		// registerForContextMenu(actualListView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.spActivity_ib_back:
				finish();
				break;

			default:
				break;
		}
	}

	private void GetMoreDate() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", TApplication.uid);
		params.put("token", TApplication.token);
		params.put("cid", String.valueOf(cid));
		params.put("page", String.valueOf(page));

		VolleyRequestTool.RequestPost(getApplicationContext(), Consts.baseUrl
						+ Consts.APPGetAppointGoodsList + Consts.ogid,
				Consts.APPGetAppointGoodsList, params,
				new VolleyListenerInterface(this,
						VolleyListenerInterface.mListener,
						VolleyListenerInterface.mErrorListener) {

					@Override
					public void onMySuccess(String result) {
						Log.i("result", result);
						try {
							GetGoodsListEntity getGoodsList = JSON.parseObject(
									result, GetGoodsListEntity.class);
							switch (Integer.valueOf(getGoodsList.getError())) {
								// 成功
								case 0:
									if (!getGoodsList.getTotal().equals("0")) {
										MyToast.Toast(ShangPinListActivity.this, "正在加载");
										List<AppointGoosList> nextgoodList = (ArrayList<AppointGoosList>) getGoodsList
												.getList();
										if(nextgoodList!=null&&nextgoodList.size()>0){
											goodList.addAll(nextgoodList);
											adapter.notifyDataSetChanged();
											page++;
										}

										mPullRefreshGridView.onRefreshComplete();

									} else {
										MyToast.Toast(ShangPinListActivity.this, "没有更多数据了");
										mPullRefreshGridView.onRefreshComplete();

									}
									break;
								// 令牌错误
								case -100:
									// 如果令牌错误的逻辑
									// 跳转到登陆界面
									// 发退出广播
									LoginOut.logOut(ShangPinListActivity.this);
									break;

							}
						} catch (Exception e) {
							Log.i("Exception", e.getMessage());
							MyExceptionToast.Toast(getApplicationContext(),
									e.getMessage());
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

}
