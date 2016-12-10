package com.haoqu.meiquan.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.haoqu.meiquan.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXShare {
	private static IWXAPI wxApi;
	public static void wechatShare(int flag,Context context){
		/**
		 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
		 *
		 * @param flag
		 * (0:分享到微信好友，1：分享到微信朋友圈)
		 */
		// 实例化
		Log.i("wechatShare", "wechatShare");
		wxApi = WXAPIFactory.createWXAPI(context, Consts.WX_APP_ID);
		wxApi.registerApp(Consts.WX_APP_ID);

		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "";
		msg.description = "这里填写内容";
		// 这里替换一张自己工程里的图片资源
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),
				R.mipmap.ic_launcher);
		msg.setThumbImage(thumb);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		wxApi.sendReq(req);

	}
}
