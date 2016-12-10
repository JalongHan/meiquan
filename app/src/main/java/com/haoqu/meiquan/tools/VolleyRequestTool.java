package com.haoqu.meiquan.tools;

import java.util.Map;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.haoqu.meiquan.TApplication;

public class VolleyRequestTool {
	public static StringRequest stringRequest;
	public static Context context;

	/*
    * 获取GET请求内容
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
	public static void RequestGet(Context context, String url, String tag, VolleyListenerInterface volleyListenerInterface) {
		// 清除请求队列中的tag标记请求
		TApplication.getHttpQueue().cancelAll(tag);
		// 创建当前的请求，获取字符串内容
		stringRequest = new StringRequest(Request.Method.GET, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener());
		// 为当前请求添加标记
		stringRequest.setTag(tag);
		// 将当前请求添加到请求队列中
		TApplication.getHttpQueue().add(stringRequest);
//	        stringRequest.setRetryPolicy( new DefaultRetryPolicy( 10*1000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
//			        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
//			        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
		// 重启当前请求队列
		TApplication.getHttpQueue().start();
	}

	/*
    * 获取POST请求内容（请求的代码为Map）
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * params：POST请求内容；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
	public static void RequestPost(Context context, String url, String tag, final Map<String, String> params, VolleyListenerInterface volleyListenerInterface) {
		// 清除请求队列中的tag标记请求
		TApplication.getHttpQueue().cancelAll(tag);
		// 创建当前的POST请求，并将请求内容写入Map中
		stringRequest = new StringRequest(Request.Method.POST, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener()){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		// 为当前请求添加标记
		stringRequest.setTag(tag);
		// 将当前请求添加到请求队列中
		TApplication.getHttpQueue().add(stringRequest);

		stringRequest.setRetryPolicy( new DefaultRetryPolicy( 10*1000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );

		// 重启当前请求队列
		TApplication.getHttpQueue().start();
	}
}

