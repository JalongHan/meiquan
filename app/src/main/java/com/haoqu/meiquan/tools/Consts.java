package com.haoqu.meiquan.tools;

public class Consts {
	/*url头*/
	public static String baseUrl = "http://we.okgotouch.com/";
	/*登陆app*/
	public static String loginUrl = "mobile.php?act=module&name=app_fenxiao_wode&do=login&weid=";
	/*获取手机验证码*/
	public static String getPhoneTestCodeUrl = "mobile.php?act=module&name=app_fenxiao_wode&do=getsms&weid=";
	/*APP注册*/
	public static String APPRegisterUrl = "mobile.php?act=module&name=app_fenxiao_wode&do=register2016&weid=";
	/*APP找回密码*/
	public static String AppGetBackPassWord = "mobile.php?act=module&name=app_fenxiao_wode&do=findpwd&weid=";
	/*获取找回手机验证码*/
	public static String APPGetBackPassWordSMS = "mobile.php?act=module&name=app_fenxiao_wode&do=getsms2&weid=";
	/*获取个人店铺分类*/
	public static String APPGetPersonStoreList = "mobile.php?act=module&name=app_fenxiao_shangcheng&do=shopindex&weid=";
	/*获取商品列表*/
	public static String APPGetAppointGoodsList = "mobile.php?act=module&name=app_fenxiao_shangcheng&do=goodslist&weid=";
	/*获取商品详情页*/
	public static String APPGetGoodsDetails = "mobile.php?act=module&name=app_fenxiao_shangcheng&do=goodsdetail&weid=";
	/*获取好友列表*/
	public static String APPGetFriendsList = "mobile.php?act=module&name=app_fenxiao_wode&do=friends&weid=";
	/*APP注册时获取商铺资料*/
	public static String APPGetStoreMessage	= "mobile.php?act=module&name=app_fenxiao_wode&do=getrdata&weid=";
	/*APP我的业绩*/
	public static String APPGetMyResults = "mobile.php?act=module&name=app_fenxiao_wode&do=myresults&weid=";
	/*App获取银行绑定信息*/
	public static String APPGetBankInfo = "mobile.php?act=module&name=app_fenxiao_wode&do=bankinfo&weid=";
	/*App绑定银行卡*/
	public static String APPBindBank = "mobile.php?act=module&name=app_fenxiao_wode&do=bindbank&weid=";
	/*App解除绑定银行卡*/
	public static String APPUnBindBank = "mobile.php?act=module&name=app_fenxiao_wode&do=unbindbank&weid=";
	/*APP申请提现*/
	public static String APPTakeMoney = "mobile.php?act=module&name=app_fenxiao_wode&do=takemoney&weid=";
	/*APP我的提现记录*/
	public static String APPTakeLog = "mobile.php?act=module&name=app_fenxiao_wode&do=takelog&weid=";
	/*我的余额*/
	public static String APPMyMoney = "mobile.php?act=module&name=app_fenxiao_wode&do=mymoney&weid=";
	/*-获取我的分享页*/
	public static String APPSharPage = "mobile.php?act=module&name=app_fenxiao_shangcheng&do=sharepage&weid=";
	/*APP商户总销量排行（总商城全国的优质商家）*/
	public static  String APPHotShopList = "mobile.php?act=module&name=app_fenxiao_shangcheng&do=hotshoplist&weid=";
	/*APP提交头像*/
	public static String APPSubmitAvatar = "mobile.php?act=module&name=app_fenxiao_wode&do=submitavatar&weid=";
	/*-App提交个人资料*/
	public static String AppSubmitInfo = "mobile.php?act=module&name=app_fenxiao_wode&do=submitinfo&weid=";
	public static String quanguo = "&mode=all";
	public static String bendi = "&mode=local";
	public static String type = "&type=";
    /*-APP商品销量排行*/
    public static String APPHotGoodsList = "mobile.php?act=module&name=app_fenxiao_shangcheng&do=hotgoodslist&weid=";
	/*weid*/
	public static String ogid = "7";

	/*注册模式*/
	public static final String INVITE_REGISTER = "1";
	public static final String TRADES_REGISTER = "2";


	/*wxAPP_ID*/
	public static final String WX_APP_ID = "wx5cd3f61961078a4b";

	/*传递数据时用*/
	public static final String KEY_DATA = "data";
	/*psfrgament请求的商户数据*/
	public static final String psDATA = "psDATA";
	/*shangpinactivity请求商品的数据*/
	public static final String spDATA = "spDATA";
	/*friendslistFragment请求的好友数据*/
	public static final String flDATA = "flDATA";
	/*发送退出广播*/
	public static final String EXITACTION = "action.exit";

}
