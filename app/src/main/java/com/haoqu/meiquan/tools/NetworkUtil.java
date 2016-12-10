package com.haoqu.meiquan.tools;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;

public class NetworkUtil {

	public static void checkNetworkState(final Context context)
	{
		try {
			//判断有没有网
			boolean hasNet = NetworkState.checkNetState(context);
			if (!hasNet)
			{
				//没网显示一个dialog
				AlertDialog.Builder dialog=new Builder(context);
				dialog.setMessage("亲，现在没网");

				//打开网络
				dialog.setPositiveButton("打开网络", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						//回调 callback
						try {
							//判断android的版本
							int version=android.os.Build.VERSION.SDK_INT;
							if (version>10)
							{
								Intent intent=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								context.startActivity(intent);
								dialog.cancel();
							}
						} catch (Exception e) {
						}

					}
				});

				dialog.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				dialog.show();
			}
		} catch (Exception e) {
		}
	}

}
