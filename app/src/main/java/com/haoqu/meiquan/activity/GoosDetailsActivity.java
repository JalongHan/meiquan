package com.haoqu.meiquan.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class GoosDetailsActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout bt_share;
	private Integer id;
	private IWXAPI wxApi;
	private TextView tv_title;
	private ImageView iv_back;
	private WebView webView;
	private String thumburl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 实例化
		wxApi = WXAPIFactory.createWXAPI(this, Consts.WX_APP_ID);
		wxApi.registerApp(Consts.WX_APP_ID);
		setContentView(R.layout.activity_goos_details);
		Tools.showProgressDialog(this);
		initViews();

		setListener();

	}

	private void setListener() {
		bt_share.setOnClickListener(this);
		iv_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.gdActivity_share:
				if (!wxApi.isWXAppInstalled()) {
					MyToast.Toast(GoosDetailsActivity.this, "您还未安装微信客户端");
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

			case R.id.gdActivity_iv_back:
				finish();

			default:
				break;
		}
	}

	/**
	 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
	 *
	 * @param flag
	 * (0:分享到微信好友，1：分享到微信朋友圈)
	 */
	private void wechatShare(int flag) {

		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = Consts.baseUrl + Consts.APPGetGoodsDetails
				+ Consts.ogid + "&gid=" + id+"&uid="+TApplication.uid+"&inwx=1";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "与你分享TA的最爱商品，好东西，就一起购";
		msg.description = "爆款尖货，只买对的不买贵的！眼光挑，就来"+getResources().getString(R.string.app_name);
		// 这里替换一张自己工程里的图片资源
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
//				R.drawable.ic_launcher);
		/**
		 * 压缩前一页传过来的图片。当微信分享的图片
		 */
		Bitmap thumb = Bitmap.createScaledBitmap(ImageLoader.getInstance().loadImageSync(thumburl), 120, 120, true);
		msg.setThumbImage(thumb);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		wxApi.sendReq(req);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initViews() {

		webView = (WebView) findViewById(R.id.gdActivity_webView);
		bt_share = (RelativeLayout) findViewById(R.id.gdActivity_share);
		tv_title = (TextView) findViewById(R.id.gdActivity_tv_title);
		iv_back = (ImageView) findViewById(R.id.gdActivity_iv_back);


		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);

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

//		WebSettings ws = wv_webview.getSettings();
//		ws.setJavaScriptEnabled(true);
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
//		// 创建WebViewClient对象 不让webview用浏览器加载
//		wv_webview.setWebViewClient(new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				return false;
//			}
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				// TODO Auto-generated method stub
//
//				super.onPageFinished(view, url);
//				Tools.closeProgressDialog();
//
//			}
//		});
//		//获取网页的标题
//		WebChromeClient wvcc = new WebChromeClient() {
//			@Override
//			public void onReceivedTitle(WebView view, String title) {
//				super.onReceivedTitle(view, title);
//				Log.d("ANDROID_LAB", "TITLE=" + title);
//				tv_title.setText(title);
//
//			}
//
//
//
//		};
//		// 设置setWebChromeClient对象
//		wv_webview.setWebChromeClient(wvcc);

		Intent intent = getIntent();
		String goodsDetailsID = intent.getStringExtra("web");
		thumburl = intent.getStringExtra("thumb");
		Log.i("goodsDetailsID", goodsDetailsID);
		id = Integer.valueOf(goodsDetailsID);
		webView.loadUrl(Consts.baseUrl + Consts.APPGetGoodsDetails
				+ Consts.ogid + "&gid=" + id);

	}

}
