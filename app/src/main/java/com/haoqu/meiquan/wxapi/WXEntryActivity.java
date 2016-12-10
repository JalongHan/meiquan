package com.haoqu.meiquan.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.view.MyToast;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity  extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, Consts.WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }

    /*不要忘记了onNewIntent这个方法，也要写上，为了防止这个Activity处于栈顶的时候微信回调我们*/
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    /**微信主动请求我们**/
    @Override
    public void onReq(BaseReq req) {

    }



    @Override
    public void onResp(BaseResp resp) {
//        LogManager.show("TAG", "resp.errCode:" + resp.errCode + ",resp.errStr:"
//                + resp.errStr, 1);
        String result = "";
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
                Log.i("分享成功", "执行到这了");
                result = "分享成功";
                MyToast.Toast(this,result);
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
                Log.i("分享取消", "执行到这了");
                result = "分享取消";
                MyToast.Toast(this,result);
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //分享拒绝
                Log.i("分享拒绝 ", "执行到这了");
                break;
        }


    }


}
