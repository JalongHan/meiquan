package com.haoqu.meiquan.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.view.MyToast;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class FriendSharePageActivity extends BaseActivity implements OnClickListener {
	private IWXAPI wxApi;
	private WebView webView;
	private RelativeLayout bt_share;
	private TextView tv_title;
	private ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_share_page);
		Tools.showProgressDialog(this);
		// 实例化
		wxApi = WXAPIFactory.createWXAPI(this, Consts.WX_APP_ID);
		wxApi.registerApp(Consts.WX_APP_ID);
		initView();
		setListener();

	}


	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {
		webView = (WebView) findViewById(R.id.fsActivity_webView);
		bt_share = (RelativeLayout) findViewById(R.id.fsActivity_share);
		tv_title = (TextView) findViewById(R.id.fsActivity_tv_title);
		iv_back = (ImageView) findViewById(R.id.fsActivity_iv_back);

		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);
//		ws.setJavaScriptCanOpenWindowsAutomatically(true);
//		ws.setSaveFormData(false);
//		ws.setAppCacheEnabled(true);
//		ws.setDefaultTextEncodingName("UTF-8");
//		ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		/* 设置适应屏幕 */
//		ws.setJavaScriptCanOpenWindowsAutomatically(true);
//		ws.setBuiltInZoomControls(true);// support zoom
//		ws.setUseWideViewPort(true);// 这个很关键
//		ws.setLoadWithOverviewMode(true);
		// 创建WebViewClient对象 不让webview用浏览器加载
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub

				super.onPageFinished(view, url);
				Tools.closeProgressDialog();

			}
		});

		webView.setWebChromeClient(new WebChromeClient(){
			//当js调用 console.log()是将会执行
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
				return super.onConsoleMessage(consoleMessage);
			}
		});





		webView.loadUrl(Consts.baseUrl + Consts.APPSharPage + Consts.ogid
				+ "&uid=" + TApplication.uid);
	}

	private void setListener() {
		bt_share.setOnClickListener(this);
		iv_back.setOnClickListener(this);
	}

	/**
	 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
	 *
	 * @param flag
	 *            (0:分享到微信好友，1：分享到微信朋友圈)
	 */
	private void wechatShare(int flag) {

		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = Consts.baseUrl + Consts.APPSharPage + Consts.ogid
				+ "&uid=" + TApplication.uid + "&inwx=1";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "在"+getResources().getString(R.string.app_name)+"购好货，速来围观捡便宜喽~~";
		msg.description = "优惠享不够！爽到停不下来！";
		// 这里替换一张自己工程里的图片资源
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon);
		msg.setThumbImage(thumb);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		wxApi.sendReq(req);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fsActivity_share:
				if (!wxApi.isWXAppInstalled()) {
					MyToast.Toast(FriendSharePageActivity.this, "您还未安装微信客户端");
					return;
				}
				new AlertDialog.Builder(this)
						.setTitle("请选择要分享的方式")
						.setPositiveButton("朋友圈", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								wechatShare(1);// 分享到微信朋友圈
							}
						})
						.setNegativeButton("好友", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								wechatShare(0);// 分享到微信好友
							}
						})
						.create().show();

				break;

			case R.id.fsActivity_iv_back:
				finish();

			default:
				break;
		}

	}


}
