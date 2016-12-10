package com.haoqu.meiquan;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.haoqu.meiquan.entity.UserEntity;
import com.haoqu.meiquan.tools.Options;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.UUID;

public class TApplication extends Application {
    /*volley请求队列，放到application中，网络请求队列都是整个APP内使用的全局性对象，因此最好写入Application类中*/
    public static RequestQueue queue;
    /*当前登陆的用户手机号和密码*/
    public static UserEntity user = new UserEntity();
    /*授权令牌*/
    public static String token = "";
    /*uid*/
    public static String uid = "";
    /*头像网址*/
    public static String avatar = "";
    //用户的昵称
    public static String nickname = "";
    //用户的手机号
    public static String mobile = "";
    /*tapplication全局用来发广播*/
    public static TApplication instance;
    /*imageloader*/
    public static ImageLoader imageLoader;
    /*imageloader的option*/
    public static DisplayImageOptions options;
    /*UUID*/
    public static String UUID;


    @Override
    public void onCreate() {
        super.onCreate();
        //建立appliction的对象
        instance = this;
        imageLoader = ImageLoader.getInstance();
        options = Options.getListOptions();
         /* Volley配置 */
        // 建立Volley的Http请求队列
        queue = Volley.newRequestQueue(getApplicationContext());
        //imageloader的配置
        initImageLoader(getApplicationContext());
        UUID = getMyUUID();
        getUidToken();
        Log.i("Tapplication.token",TApplication.token);
        Log.i("Tapplication.uid",TApplication.uid);
    }

    //用来获得volley消息队列/可以设置tag，退出的时候退出这个请求
    public static RequestQueue getHttpQueue() {
        return queue;
    }

    /**
     * 获得UUID唯一设备识别码
     *
     * @return
     */
    private String getMyUUID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "meiquan/cache");
        Log.i("cacheDir", cacheDir.getPath());
        //创建配置imageLoader(所有的选项都是可选的，只使用那些你真的想定制)这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                //.discCacheFileNameGenerator(new HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.discCacheFileCount(100) //缓存的File数量
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                //.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                //.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);//全局初始化此配置

    }



    private void getUidToken(){
        SharedPreferences sp = getSharedPreferences("login",
                Context.MODE_PRIVATE);
			/*如果有存过就取出值来直接登陆,取不到值说明没登陆过，先检查网络*/
        Log.i("boolean", String.valueOf(sp.contains("phonekey")));
        if (sp.contains("phonekey")) {
            TApplication.uid = sp.getString("uid", "");
            TApplication.token = sp.getString("token", "");
            TApplication.avatar = sp.getString("avatar","");
            TApplication.nickname = sp.getString("nickname","");
            TApplication.mobile = sp.getString("mobile","");
        }
    }



}
