package com.haoqu.meiquan.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.activity.ChangeInfoActivity;
import com.haoqu.meiquan.activity.LoginActivity;
import com.haoqu.meiquan.activity.MyBankActivity;
import com.haoqu.meiquan.activity.MyResultsActivity;
import com.haoqu.meiquan.activity.TixianRecordActivity;
import com.haoqu.meiquan.tools.CleanCache;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.view.CircleImageView;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.text.DecimalFormat;

public class MyCenterFragment extends Fragment implements OnClickListener{
	private View view;
	private RelativeLayout myIncome;
	private RelativeLayout myBankCards;
	private Button logOut;
	private RelativeLayout tixianRecord;
	private RelativeLayout cleancache;
	private RelativeLayout rl_top;
	private RelativeLayout toChangeInfo;
    private CircleImageView iv_headIcon;
	private TextView tv_nickname;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public void onResume() {
        if(TApplication.avatar.length()>0){
            TApplication.imageLoader.displayImage(TApplication.avatar,iv_headIcon,TApplication.options);
        }
		if(TApplication.nickname.length()>0){
			tv_nickname.setText(TApplication.nickname);
		}else if(TApplication.mobile.length()>0){
            tv_nickname.setText(TApplication.mobile);
        }
        super.onResume();
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_mycenter, null);
		initViews();
		//当版本小于4.4的时候设置头不显示
		try {
			if(VERSION.SDK_INT < VERSION_CODES.KITKAT){
				rl_top.setVisibility(View.GONE);
				Log.i("mycendter判断版本执行了", "mycendter判断版本执行了");
				Log.i("mycendter判断版本执行了", String.valueOf(VERSION.SDK_INT));
				Log.i("mycendter判断版本执行了", String.valueOf(VERSION_CODES.KITKAT));
			}else {
				rl_top.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		setListener();

		return view;
	}
	private void initViews() {
		//顶部的view
		rl_top = (RelativeLayout) view.findViewById(R.id.mcFragment_rl_top);
		//我的分享
		tixianRecord = (RelativeLayout) view.findViewById(R.id.mcFragment_tixianRecord);
		//我的收支
		myIncome = (RelativeLayout) view.findViewById(R.id.mcFragment_myIncome);
		//我的银行卡
		myBankCards = (RelativeLayout) view.findViewById(R.id.mcFragment_myBankCards);
		//清除缓存
		cleancache = (RelativeLayout) view.findViewById(R.id.mcFragment_rl_cleancache);
		//退出登陆
		logOut = (Button) view.findViewById(R.id.mcFragment_logOut);
		//跳转到资料个性
		toChangeInfo = (RelativeLayout) view.findViewById(R.id.mcfragment_to_changeinfo);
        //我的头像
        iv_headIcon = (CircleImageView) view.findViewById(R.id.mcFragment_iv_headIcon);
        //设置头像
        //昵称控件
		tv_nickname = (TextView) view.findViewById(R.id.mcfragment_tv_nickname);
		//昵称



	}
	private void setListener() {
		tixianRecord.setOnClickListener(this);
		myIncome.setOnClickListener(this);
		myBankCards.setOnClickListener(this);
		cleancache.setOnClickListener(this);
		logOut.setOnClickListener(this);
        toChangeInfo.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
            //点击顶部头像都可以 进入个性资料页面
            case R.id.mcfragment_to_changeinfo:

                startActivity(new Intent(getActivity(), ChangeInfoActivity.class));

                break;

			case R.id.mcFragment_tixianRecord:
				startActivity(new Intent(getActivity(), TixianRecordActivity.class));
				break;

			case R.id.mcFragment_myIncome:
				//跳转到我的收支界面
				goToMyIncome();
				break;
			//跳转到绑定银行卡页面
			case R.id.mcFragment_myBankCards:
				startActivity(new Intent(getActivity(), MyBankActivity.class));
				break;
			//点击清除缓存
			case R.id.mcFragment_rl_cleancache:

				cleanCache();
				break;
			case R.id.mcFragment_logOut:

				new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("确认退出登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //跳转到登陆界面
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                //发退出广播,并清空偏好设置
                                LoginOut.logOut(getActivity());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();


				break;


			default:
				break;
		}
	}
	/**
	 * 清除缓存
	 */
	private void cleanCache() {
		long size = 0;
		final File cacheDir = StorageUtils.getOwnCacheDirectory(getActivity(),"prosumer/cache");
		Log.i("cacheDir.getPath()", cacheDir.getPath());

		try {
			size = CleanCache.getFolderSize(new File("/data/data/" + getActivity().getPackageName()));
			Log.i("size", String.valueOf((size/1024/1024)*1.00));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		String totalsize = df.format(Float.valueOf(size)/1024/1024);
		Log.i("size", totalsize);


		new AlertDialog.Builder(getActivity())
				.setTitle("提示")
				.setMessage("清理缓存"+"("+totalsize+"M"+")")
				.setNegativeButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						//点击确认清理缓存
						CleanCache.cleanCustomCache(cacheDir.getPath());
						//清理所有缓存
						CleanCache.cleanApplicationData(getActivity(),
								new String[]{
										"/data/data/"+ getActivity().getPackageName() + "/lib",//下面的lib文件夹
										"/data/data/"+ getActivity().getPackageName() + "/app_webview"});//4.0后app_webview缓存

					}
				})
				.setPositiveButton("取消", null)
				.create().show();
	}
	//跳转到我的收支界面
	private void goToMyIncome() {
		startActivity(new Intent(getActivity(), MyResultsActivity.class));
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
