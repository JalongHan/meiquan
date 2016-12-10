package com.haoqu.meiquan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.biz.UploadUtil;
import com.haoqu.meiquan.entity.SubmitAvatarEntity;
import com.haoqu.meiquan.entity.SubmitInfoEntity;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.CircleImageView;
import com.haoqu.meiquan.view.MyToast;
import com.nostra13.universalimageloader.utils.DiscCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChangeInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RelativeLayout rl_changeHeadIcon;
    private EditText et_nickName;
    private CircleImageView iv_headicon;

    /* 请求识别码 */
    @SavedInstanceState
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    @SavedInstanceState
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    @SavedInstanceState
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    @SavedInstanceState
    private static int output_X = 200;
    @SavedInstanceState
    private static int output_Y = 200;

    /* 头像文件 */
    @SavedInstanceState
    private static final String IMAGE_FILE_NAME = "temp_head_image.png";
    private Bitmap bitmap;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    Tools.closeProgressDialog();

                    break;
                case 1:

                    MyToast.Toast(ChangeInfoActivity.this, "上传成功");

                    break;
                case 2:
                    MyToast.Toast(ChangeInfoActivity.this,"图片格式不正确");
                    break;
                case 3:
                    MyToast.Toast(ChangeInfoActivity.this,"上传失败,请重新上传");
                    break;

            }

        }
    };
    private EditText et_changeNickName;
    private EditText et_changepw;
    private Button bt_save;
    private String nickname;
    private String pw;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        initView();
        setListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //后退按钮
        iv_back = (ImageView) findViewById(R.id.ciActivity_iv_back);
        //头像修改
        rl_changeHeadIcon = (RelativeLayout) findViewById(R.id.ciActivity_changeHeadIcon);
        //头像控件
        iv_headicon = (CircleImageView) findViewById(R.id.ciActivity_iv_headIcon);
        if (TApplication.avatar.length() > 0) {
            TApplication.imageLoader.displayImage(TApplication.avatar, iv_headicon, TApplication.options);
        }

        //昵称显示edittext
        et_nickName = (EditText) findViewById(R.id.ciActivity_tv_nickName_name);

        //修改密码的edittext
        et_changepw = (EditText) findViewById(R.id.ciActivity_et_changepw);
        //保存按钮
        bt_save = (Button) findViewById(R.id.ciActivity_save);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        iv_back.setOnClickListener(this);
        rl_changeHeadIcon.setOnClickListener(this);
        bt_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击后退
            case R.id.ciActivity_iv_back:
                finish();
                break;
            //点击头像修改
            case R.id.ciActivity_changeHeadIcon:

                choseHeadicon();


                break;
            //点击保存
            case R.id.ciActivity_save:

                //判断是密码和昵称是否合法
                nickname = et_nickName.getText().toString();
                pw = et_changepw.getText().toString();
                String regex = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{3,8}";
                boolean ok = false;

                if (nickname.length() == 0 && pw.length() == 0) {
                    //都为空什么也不做
                    return;
                } else if (nickname.length() > 0) {
                    if (!nickname.matches(regex)) {
                        MyToast.Toast(this, "昵称不能超过八位或小于三位");
                        return;
                    } else {
                        ok = true;
                    }
                } else if (pw.length() > 0) {
                    if (!pw.matches("^[a-zA-Z\\d]{6,16}$")) {
                        MyToast.Toast(this, "密码请填写6-16位字母和数字");
                        return;
                    } else {
                        ok = true;
                    }
                } else {
                    ok = true;
                }


                if (ok) {
                    changePWandNickName();
                }

                //上传修改后的密码和昵称


                break;
            //点击密码修改

        }
    }

    /**
     * 发请求去修改密码和昵称
     */
    private void changePWandNickName() {
        Tools.showProgressDialog(this);
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", TApplication.uid);
        map.put("token", TApplication.token);
        map.put("nickname", nickname);
        map.put("password", pw);

        VolleyRequestTool.RequestPost(this,
                Consts.baseUrl + Consts.AppSubmitInfo + Consts.ogid,
                Consts.AppSubmitInfo,
                map,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
                    @Override
                    public void onMySuccess(String result) {
                        Log.i("changePWandNN", result);
                        try {
                            SubmitInfoEntity submitInfoEntity = JSON.parseObject(result, SubmitInfoEntity.class);

                            switch (Integer.valueOf(submitInfoEntity.getError())) {
                                case 0:
                                    //成功,将nickname保存到SharedPreferences

                                    SharedPreferences sp = getSharedPreferences("login",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor et = sp.edit();
                                    et.putString("nickname", submitInfoEntity.getNickname());
                                    et.commit();
                                    //将tapplication.nickname赋值 ,让其它 的地方 调用
                                    TApplication.nickname = submitInfoEntity.getNickname();
                                    MyToast.Toast(ChangeInfoActivity.this, "修改成功");
                                    break;
                                case -100:
                                    LoginOut.logOut(ChangeInfoActivity.this);
                                    break;
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Tools.closeProgressDialog();
                        }


                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        MyToast.Toast(ChangeInfoActivity.this, "修改失败,请重新修改");
                        Tools.closeProgressDialog();
                    }
                });

    }

    /**
     * 选择头像
     */
    private void choseHeadicon() {
        //弹出提示框

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:

                        choseHeadIconFromGallery();

                        break;
                    case 1:

                        choseHeadIconFromCapture();

                        break;
                }
            }
        });
        builder.create().show();


    }

    /**
     * 打开相机拍照得图片
     */
    private void choseHeadIconFromCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (hasSDCard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);

    }

    /**
     * 判断是否有sdcard
     *
     * @return true 有  false没有
     */
    private boolean hasSDCard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //有存储的sdcard
            return true;
        } else {
            return false;
        }

    }

    /**
     * 打开相册选择图片
     */
    private void choseHeadIconFromGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");//可选择图片视频
        //修改为以下两句代码
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CODE_GALLERY_REQUEST);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
//            MyToast.Toast(this, "取消");
            return;
        }


        Log.i("requestCode", String.valueOf(requestCode));
        Log.i("resultCode", String.valueOf(resultCode));
        switch (requestCode) {

            case CODE_CAMERA_REQUEST:
                if (hasSDCard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    MyToast.Toast(this, "没有内存卡");
                }

                break;
            case CODE_GALLERY_REQUEST:
//                if(data.get){
//
//                }

                Log.i("data.path",data.getData().getPath());
                cropRawPhoto(data.getData());

                break;
            case CODE_RESULT_REQUEST:

                setImageToHeadIcon(data);

                break;


        }
    }

    /**
     * 提取保存裁剪的之后的图片,并设置头像
     *
     * @param data
     */
    private void setImageToHeadIcon(Intent data) {

        Bundle bundle = data.getExtras();

        if (bundle != null) {
            bitmap = bundle.getParcelable("data");
            iv_headicon.setImageBitmap(bitmap);
        }


        upLoadHeadIcon();


    }

    /**
     * 上传头像
     */
    private void upLoadHeadIcon() {
        try {

            final String requestURL = Consts.baseUrl + Consts.APPSubmitAvatar + Consts.ogid;
            final File file = Tools.saveFile(bitmap, "icon", getApplicationContext());
            Log.i("file", file.getPath());
            Tools.showProgressDialog(ChangeInfoActivity.this);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {


                            try {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("uid", TApplication.uid);
                                params.put("token", TApplication.token);
                                Map<String, File> files = new HashMap<String, File>();
                                files.put("uploadfile", file);
                                final String request = UploadUtil.post(requestURL, params, files);


                                SubmitAvatarEntity submitAvatarEntity = JSON.parseObject(request,SubmitAvatarEntity.class);
                                switch (Integer.valueOf(submitAvatarEntity.getError())){
                                    //上传成功
                                    case 0:
                                        String avatar = submitAvatarEntity.getAvatar();
                                        Log.i("request", request);
                                        SharedPreferences sp = getSharedPreferences("login",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor et = sp.edit();
                                        et.putString("avatar", avatar);
                                        et.commit();
                                        //赋值给Tapplication的,供其它的调用.
                                        TApplication.avatar = avatar;
                                        //清除一下imageloader中缓存的图片.因为网址是一样的,让其它地方的正常显示
                                        MemoryCacheUtils.removeFromCache(avatar, TApplication.imageLoader.getMemoryCache());
                                        DiscCacheUtils.removeFromCache(avatar, TApplication.imageLoader.getDiscCache());
                                        //发送上传成功的消息
                                        handler.sendEmptyMessage(1);
                                        break;
                                    //图片格式不允许
                                    case -10:
                                        handler.sendEmptyMessage(2);
                                        break;
                                    //令牌错误
                                    case -100:
                                        LoginOut.logOut(ChangeInfoActivity.this);
                                        break;

                                }




                            } catch (IOException e) {
                                handler.sendEmptyMessage(3);
                                e.printStackTrace();
                            } finally {
                                handler.sendEmptyMessage(0);
                            }

                        }
                    }
            ).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪原始的图片
     *
     * @param data
     */
    private void cropRawPhoto(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        // 设置裁剪
        intent.putExtra("crop", true);
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }


    public static String getImageStr(Bitmap bitmap) {

        //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }
}