package com.haoqu.meiquan.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断是否有网络工具类
 * 有网络返回true,没有返回false
 * @author apple
 *
 */
public class NetworkState {
	/**
	 * 判断是否有网络
	 *
	 * @param context　上下文
	 * @return　有网络返回true,没有返回false
	 */
	public static boolean checkNetState(Context context){
		ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo=manager.getActiveNetworkInfo();
		if(activeNetworkInfo == null){
			return false;
		}else {
			return true;
		}

	}
}
