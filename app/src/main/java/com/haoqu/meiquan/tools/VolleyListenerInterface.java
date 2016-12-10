package com.haoqu.meiquan.tools;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import android.app.Fragment;
import android.content.Context;
/**
 *  Volley请求（成功或失败）的监听事件封装：
 * @author apple
 *
 */
public abstract class VolleyListenerInterface {

	public Context mContext;
	public static Response.Listener<String> mListener;
	public static Response.ErrorListener mErrorListener;

	public VolleyListenerInterface(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
		this.mContext = context;
		this.mErrorListener = errorListener;
		this.mListener = listener;
	}

	public VolleyListenerInterface(
			Fragment codeRegisterFragment,
			Listener<String> mListener2, ErrorListener mErrorListener2) {
		// TODO Auto-generated constructor stub
	}

	// 请求成功时的回调函数
	public abstract void onMySuccess(String result);

	// 请求失败时的回调函数
	public  abstract void onMyError(VolleyError error);

	// 创建请求的事件监听
	public Response.Listener<String> responseListener() {
		mListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String s) {
				onMySuccess(s);
			}
		};
		return mListener;
	}

	// 创建请求失败的事件监听
	public Response.ErrorListener errorListener() {
		mErrorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				onMyError(volleyError);
			}



		};
		return mErrorListener;
	}
}

