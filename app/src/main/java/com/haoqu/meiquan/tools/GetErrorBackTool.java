package com.haoqu.meiquan.tools;

import android.content.Context;

import com.haoqu.meiquan.view.MyToast;
/**
 * volley出现错误的时候执行onMyError的方法
 * @author apple
 *
 */
public class GetErrorBackTool {
	public static void gerErrorBack(Context context){
		Tools.closeProgressDialog();
		MyToast.Toast(context, "网络请求失败或当前没有网络");
	}
}
