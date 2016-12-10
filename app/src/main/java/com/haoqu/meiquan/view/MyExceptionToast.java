package com.haoqu.meiquan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqu.meiquan.R;
/**
 * 自定义吐司
 * @author apple
 *
 */
public class MyExceptionToast{
	public static void Toast(Context context,String text){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.toast, null);
		TextView tv_msg = (TextView) view.findViewById(R.id.mytoast_text);
		tv_msg.setText(text);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}


}
