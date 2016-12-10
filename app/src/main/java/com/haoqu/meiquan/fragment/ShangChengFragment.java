package com.haoqu.meiquan.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.activity.GoosDetailsActivity;
import com.haoqu.meiquan.activity.ShangjiaListActivity;
import com.haoqu.meiquan.adapter.ShangJiaAdapter;
import com.haoqu.meiquan.adapter.ShangPinAdapter;
import com.haoqu.meiquan.entity.GetHotGoodsList;
import com.haoqu.meiquan.entity.HotGoodsList;
import com.haoqu.meiquan.entity.ShopListAll;
import com.haoqu.meiquan.entity.ShopListAllAll;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.tools.LoginOut;
import com.haoqu.meiquan.tools.Tools;
import com.haoqu.meiquan.tools.VolleyListenerInterface;
import com.haoqu.meiquan.tools.VolleyRequestTool;
import com.haoqu.meiquan.view.pullViewpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 美圈街总商城
 * Created by Han on 16/7/12.
 */
public class ShangChengFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {


    private View view;
    private TextView tv_bendi;
    private TextView tv_quanguo;
    private Button bt_meifa;
    private Button bt_hufu;
    private Button bt_caizhuang;
    private Button bt_qita;
    private TextView tv_shangjia;
    private TextView tv_shangpin;
    private pullViewpager viewPager;
    private View v_line1;
    private View v_line2;
    private RelativeLayout scrollView;
    private RelativeLayout rl2;
    private LinearLayout ll1;
    private LinearLayout activity_ll1;
    private LinearLayout ll_inscrollview;
    private ArrayList<View> viewList;
    private ListView listViewSP;
    private ListView listViewSJ;
    private ShangJiaAdapter SJadapter;
    private List<ShopListAll> shopListAll = new ArrayList<ShopListAll>();
    //全国商户url
    String quanguoshopurl = Consts.baseUrl + Consts.APPHotShopList + Consts.ogid + Consts.type + Consts.quanguo;
    //商铺请求url,开始的时候是本地的

    String shopUrl = Consts.baseUrl + Consts.APPHotShopList + Consts.ogid + Consts.bendi + Consts.type+"";
    private Button[] btnary;
    private List<HotGoodsList> hotGoodsLists;
    private int moveHeight;
    private int move;
    private LinearLayout ll_vp;
    private int height;
    private int rl2Height;
    private int ll1Height;
    private PagerAdapter pageradapter;
    private ViewGroup.LayoutParams llvpheight;
    private int shotllvpheight;
    private ImageView iv_meifa_cornermark;
    private ImageView iv_hufu_cornermark;
    private ImageView iv_caizhuang_cornermark;
    private ImageView iv_qita_cornermark;
    private ImageView[] ivary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shangcheng, null);
        initView();
//        initFragment();
        ll_inscrollview.setFocusable(true);
        ll_inscrollview.setFocusableInTouchMode(true);
        ll_inscrollview.requestFocus();
        initListView(inflater);
        setListener();

        return view;
    }

    /**
     * 在viewpager中加载不同的listview
     */
    private void initListView(LayoutInflater inflater) {
        Log.i("initListView", "initListView");
        viewList = new ArrayList<View>();
        View view = inflater.inflate(R.layout.listview, null);
        View view1 = inflater.inflate(R.layout.listview, null);
        listViewSP = (ListView) view.findViewById(R.id.ListView);
        listViewSJ = (ListView) view1.findViewById(R.id.ListView);
        viewList.add(listViewSJ);
        viewList.add(listViewSP);

        pageradapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        viewPager.setAdapter(pageradapter);
        //本地商铺请求url
        getHotShopList(listViewSJ, shopUrl);

        getHotGoodsList(listViewSP);


    }

    /**
     * 发请求去获取热门商品的数据
     *
     * @param listViewSP 要显示的listview
     */
    private void getHotGoodsList(final ListView listViewSP) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", TApplication.uid);
        map.put("token", TApplication.token);
        map.put("num", "10");


        VolleyRequestTool.RequestPost(getActivity(),
                Consts.baseUrl + Consts.APPHotGoodsList + Consts.ogid,
                Consts.APPHotGoodsList,
                map,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
                    @Override
                    public void onMySuccess(String result) {

                        Log.i("APPHotGoodsList", result);

                        try {
                            GetHotGoodsList getGoodsListEntity = JSON.parseObject(result, GetHotGoodsList.class);
                            if (getGoodsListEntity != null) {
                                switch (Integer.valueOf(getGoodsListEntity.getError())) {
                                    //通过
                                    case 0:
                                        hotGoodsLists = getGoodsListEntity.getList();
                                        if (hotGoodsLists != null && hotGoodsLists.size() > 0) {
                                            ShangPinAdapter shangPinAdapter = new ShangPinAdapter(getActivity(), hotGoodsLists);
                                            listViewSP.setAdapter(shangPinAdapter);

                                        }

                                        break;
                                    //令牌错误
                                    case -100:
                                        LoginOut.logOut(getActivity());
                                        break;
                                    default:

                                        break;
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Log.i("商家", "执行关菊花");
                            Tools.closeProgressDialog();
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        byte[] str = TApplication.getHttpQueue().getCache().get(Consts.baseUrl + Consts.APPHotGoodsList + Consts.ogid).data;
                        Log.i("str", new String(str));
                        String result = new String(str);
                        try {
                            GetHotGoodsList getGoodsListEntity = JSON.parseObject(result, GetHotGoodsList.class);
                            if (getGoodsListEntity != null) {
                                switch (Integer.valueOf(getGoodsListEntity.getError())) {
                                    //通过
                                    case 0:
                                        hotGoodsLists = getGoodsListEntity.getList();
                                        if (hotGoodsLists != null && hotGoodsLists.size() > 0) {
                                            ShangPinAdapter shangPinAdapter = new ShangPinAdapter(getActivity(), hotGoodsLists);
                                            listViewSP.setAdapter(shangPinAdapter);

                                        }

                                        break;
                                    //令牌错误
                                    case -100:
                                        LoginOut.logOut(getActivity());
                                        break;
                                    default:

                                        break;
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Log.i("商家", "执行关菊花");
                            Tools.closeProgressDialog();
                        }

                    }
                }
        );


    }

    /**
     * 发请求去获取热门商家数据
     *
     * @param listviewSJ 要显示的listview
     */
    private void getHotShopList(final ListView listviewSJ, final String url) {
        Tools.showProgressDialog(getActivity());
        /**
         * 发请求去获取数据
         */
        Log.i("getHotShopList", url);
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", TApplication.uid);
        map.put("token", TApplication.token);
        map.put("gnum", "5");


        VolleyRequestTool.RequestPost(getActivity(),
                url,
                Consts.APPHotShopList,
                map,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
                    @Override
                    public void onMySuccess(String result) {

                        Log.i("APPHotShopList", result);

                        try {
                            ShopListAllAll shopListAllAll = JSON.parseObject(result, ShopListAllAll.class);
                            if (shopListAllAll != null) {
                                switch (Integer.valueOf(shopListAllAll.getError())) {
                                    //通过
                                    case 0:
                                        shopListAll.clear();
                                        shopListAll.addAll(shopListAllAll.getList());
                                        if (SJadapter == null) {
                                            SJadapter = new ShangJiaAdapter(getActivity(), shopListAll);
                                            listviewSJ.setAdapter(SJadapter);
                                        } else {
                                            SJadapter.notifyDataSetChanged();
                                        }

                                        break;
                                    //令牌错误
                                    case -100:
                                        LoginOut.logOut(getActivity());

                                        break;

                                    case -200:

                                        shopListAll.clear();
                                        if (SJadapter == null) {
                                            SJadapter = new ShangJiaAdapter(getActivity(), shopListAll);
                                            listviewSJ.setAdapter(SJadapter);
                                        } else {
                                            SJadapter.notifyDataSetChanged();
                                        }
                                        break;

                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Log.i("商家", "执行关菊花");
                            Tools.closeProgressDialog();
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        byte[] str = TApplication.getHttpQueue().getCache().get(url).data;
                        Log.i("str", new String(str));
                        String result = new String(str);
                        try {
                            ShopListAllAll shopListAllAll = JSON.parseObject(result, ShopListAllAll.class);
                            if (shopListAllAll != null) {
                                switch (Integer.valueOf(shopListAllAll.getError())) {
                                    //通过
                                    case 0:
                                        shopListAll.clear();
                                        shopListAll.addAll(shopListAllAll.getList());
                                        if (SJadapter == null) {
                                            SJadapter = new ShangJiaAdapter(getActivity(), shopListAll);
                                            listviewSJ.setAdapter(SJadapter);
                                        } else {
                                            SJadapter.notifyDataSetChanged();
                                        }

                                        break;
                                    //令牌错误
                                    case -100:
                                        LoginOut.logOut(getActivity());

                                        break;
                                    default:

                                        break;

                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Log.i("商家", "执行关菊花");
                            Tools.closeProgressDialog();
                        }
                    }
                }
        );


    }


    /**
     * 设置监听
     */
    private void setListener() {
        tv_bendi.setOnClickListener(this);
        tv_quanguo.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
        bt_meifa.setOnClickListener(this);
        bt_hufu.setOnClickListener(this);
        bt_caizhuang.setOnClickListener(this);
        bt_qita.setOnClickListener(this);
        tv_shangjia.setOnClickListener(this);
        tv_shangpin.setOnClickListener(this);
        //添加商家listview的当前的监听
        listViewSJ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(Consts.KEY_DATA, String.valueOf(shopListAll.get(position).getId()));
                Log.i("scfragment.sid",String.valueOf(shopListAll.get(position).getId()));
                intent.putExtra("name", (shopListAll.get(position).getMallname()));
                intent.setClass(getActivity(), ShangjiaListActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //添加商品listview的当前的监听
        listViewSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoosDetailsActivity.class);
                intent.putExtra("web", hotGoodsLists.get(position).getId());
                intent.putExtra("thumb", hotGoodsLists.get(position).getThumb());
                getActivity().startActivity(intent);
            }
        });

//        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
//            @Override
//            public void onScroll(int scrollY) {
//                Log.i("scrollY", String.valueOf(scrollY));
//                moveHeight = scrollY;
//            }
//        });

        //获得rl2的高度
        ViewTreeObserver vto = rl2.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                rl2Height = rl2.getHeight();
            }
        });
        //获得ll1的高度
        ViewTreeObserver vto1 = ll1.getViewTreeObserver();
        vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ll1Height = ll1.getHeight();
            }
        });


    }

    /**
     * 控件的点击监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shangcheng_bendi:


                for (int i = 0; i < btnary.length; i++) {
                    if(btnary[i].isSelected()){
                        btnary[i].setSelected(false);
                        ivary[i].setVisibility(View.GONE);
                    }
                }
                shopUrl = Consts.baseUrl + Consts.APPHotShopList + Consts.ogid + Consts.bendi + Consts.type;
                Log.i("执行", "执行shangcheng_bendi");
                Log.i("执行", String.valueOf(tv_bendi.getTextSize()));

                getHotShopList(listViewSJ, shopUrl);



                tv_bendi.setTextColor(getResources().getColor(R.color.color_pink));
                tv_bendi.setTextSize(13);
                tv_quanguo.setTextColor(getResources().getColor(R.color.text_color_dark));
                tv_quanguo.setTextSize(10);

                break;
            case R.id.shangcheng_quanguo:


                for (int i = 0; i < btnary.length; i++) {
                    if(btnary[i].isSelected()){
                        btnary[i].setSelected(false);
                        ivary[i].setVisibility(View.GONE);
                    }
                }
                shopUrl = Consts.baseUrl + Consts.APPHotShopList + Consts.ogid + Consts.quanguo + Consts.type;
                Log.i("执行", "执行shangcheng_bendi");
                Log.i("执行", String.valueOf(tv_bendi.getTextSize()));
                getHotShopList(listViewSJ, shopUrl);


                tv_bendi.setTextColor(getResources().getColor(R.color.text_color_dark));
                tv_bendi.setTextSize(10);
                tv_quanguo.setTextColor(getResources().getColor(R.color.color_pink));
                tv_quanguo.setTextSize(13);


                break;
            case R.id.meifa:
                //设置按钮状态 并去发请求优质商家的数据
                SelectedAndRequest(R.id.meifa);

                break;
            case R.id.hufu:
                //设置按钮状态 并去发请求优质商家的数据
                SelectedAndRequest(R.id.hufu);
                break;
            case R.id.caizhuang:
                //设置按钮状态 并去发请求优质商家的数据
                SelectedAndRequest(R.id.caizhuang);
                break;
            case R.id.qita:
                //设置按钮状态 并去发请求优质商家的数据
                SelectedAndRequest(R.id.qita);
                break;
            case R.id.shangcheng_tv_shangjia:
                viewPager.setCurrentItem(0);

                break;
            case R.id.shangcheng_tv_shangpin:

                viewPager.setCurrentItem(2);

                break;
        }
    }

    private void AnimationUPDOWN(final float height) {
        AnimationSet set1 = new AnimationSet(true);
        TranslateAnimation translate1 = new TranslateAnimation(0, 0, 0, height);
        set1.addAnimation(translate1);
        set1.setDuration(1000);
        set1.setFillAfter(true);

//        params = scrollView.getLayoutParams();
//        int svHeight = scrollView.getHeight();
//        params.height = svHeight + rl2.getHeight() + ll1.getHeight();
//        Log.i("params.height", String.valueOf(params.height));
//                scrollView.setLayoutParams(params);
//        Log.i("rl2+ll1", String.valueOf(rl2.getHeight() + ll1.getHeight()));
//                将inscrollview切掉上面象素
//        ll_inscrollview.offsetTopAndBottom((int)height);
        ll_inscrollview.startAnimation(set1);

        set1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            /**
             * 动画结束时
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                //view划动到指定位置,清除动画,防止 闪屏
                ll_inscrollview.scrollTo(0, -(int) height);
                Log.i("getleft", String.valueOf(ll_inscrollview.getHeight()));
                ll_inscrollview.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 设置四个分类搜索按钮的状态,选中或未选中,并根据选中的发送请求去请求数据
     *
     * @param id 点击的按钮的id
     */
    private void SelectedAndRequest(int id) {

        for (int i = 0; i < btnary.length; i++) {
            //如果某个button与当前点击的id 相同
            if (btnary[i].getId() == id) {
                //如果button已经是点击状态了,返回
                if (btnary[i].isSelected()) return;
                Log.i("选中了", btnary[i].getTag().toString());
                btnary[i].setSelected(true);
                ivary[i].setVisibility(View.VISIBLE);
                //urlencode转码,暂时先不用.
//                try {
//                    Log.i("美发用品", URLEncoder.encode("美发用品","UTF-8")) ;
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }


                getHotShopList(listViewSJ, shopUrl + (btnary[i].getTag().toString()));

            } else {
                //如果某个button与当前点击的id 不同
                btnary[i].setSelected(false);
                ivary[i].setVisibility(View.GONE);

            }
        }

    }

    /**
     * viewpager的监听
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            //划到优质商家时
            case 0:
                tv_shangjia.setTextColor(getResources().getColor(R.color.text_black));
                v_line1.setVisibility(View.VISIBLE);
                tv_shangpin.setTextColor(getResources().getColor(R.color.text_color_dark));
                v_line2.setVisibility(View.GONE);
                //动画
//                Log.i("-moveheight",String.valueOf(height));
//                ll_inscrollview.animate().translationX(0).translationY(0).setDuration(1000);
//                scrollView.scrollTo(0,0);
//                ll_inscrollview.scrollTo(0,-height);

                ll_vp.animate().translationX(0).translationY(0).setDuration(1000);
                ll1.animate().translationX(0).translationY(0).setDuration(1000);
                rl2.animate().translationX(0).translationY(0).setDuration(1000);
                //设置ll-vp的高度 1秒后,动画结束 后再设置
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llvpheight = ll_vp.getLayoutParams();
                        llvpheight.height = shotllvpheight;
                        ll_vp.setLayoutParams(llvpheight);
                    }
                }, 1000);


                break;
            //划到优质商品时
            case 1:

                tv_shangjia.setTextColor(getResources().getColor(R.color.text_color_dark));
                v_line1.setVisibility(View.GONE);
                tv_shangpin.setTextColor(getResources().getColor(R.color.text_black));
                v_line2.setVisibility(View.VISIBLE);
                //动画
//                height = rl2.getHeight() + ll1.getHeight();
                Log.i("moveheight", String.valueOf(move));
                //划动scrollview 后需要向上移的的高度
                move = rl2Height + ll1Height - moveHeight;
                Log.i("move", String.valueOf(move));

                shotllvpheight = ll_vp.getHeight();
                /**
                 * 获得屏幕的高度
                 */
                WindowManager manager = getActivity().getWindowManager();
                DisplayMetrics outMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(outMetrics);
                int height2 = outMetrics.heightPixels;

                if (move > 0) {

                    ll_vp.animate().translationX(0).translationY(-(rl2Height + ll1Height)).setDuration(1000);
                    ll1.animate().translationX(0).translationY(-(rl2Height + ll1Height)).setDuration(1000);
                    rl2.animate().translationX(0).translationY(-(rl2Height + ll1Height)).setDuration(1000);
                    Log.i("move-height", String.valueOf(height));


                }


                int listViewSPHeight = listViewSP.getHeight();
                Log.i("listViewSPheight", String.valueOf(listViewSPHeight));


                llvpheight = ll_vp.getLayoutParams();
                llvpheight.height = scrollView.getHeight();
                ll_vp.setLayoutParams(llvpheight);
                Log.i("height2", String.valueOf(height2));
//                ViewGroup.LayoutParams vpheight = viewPager.getLayoutParams();
//                vpheight.height = height2;
//                viewPager.setLayoutParams(vpheight);
//
//                ViewGroup.LayoutParams listviewHeight = listViewSP.getLayoutParams();
//                listviewHeight.height = height2+rl2Height + ll1Height;
//                listViewSP.setLayoutParams(listviewHeight);


                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 初始化控件
     */
    private void initView() {
        //本地按钮
        tv_bendi = (TextView) view.findViewById(R.id.shangcheng_bendi);
        //全国按钮
        tv_quanguo = (TextView) view.findViewById(R.id.shangcheng_quanguo);
        //美发用品按钮
        bt_meifa = (Button) view.findViewById(R.id.meifa);
        //护肤用品按钮
        bt_hufu = (Button) view.findViewById(R.id.hufu);
        //彩妆粉底按钮
        bt_caizhuang = (Button) view.findViewById(R.id.caizhuang);
        //其它商品按钮
        bt_qita = (Button) view.findViewById(R.id.qita);
        btnary = new Button[]{bt_meifa, bt_hufu, bt_caizhuang, bt_qita};
        btnary[0].setTag("美容");
        btnary[1].setTag("美发");
        btnary[2].setTag("美甲");
        btnary[3].setTag("其它");
        //美发用品选中后角标
        iv_meifa_cornermark = (ImageView) view.findViewById(R.id.meifa_cornermark);
        //护肤用品选中后角标
        iv_hufu_cornermark = (ImageView) view.findViewById(R.id.hufu_cornermark);
        //彩妆用品选中后角标
        iv_caizhuang_cornermark = (ImageView) view.findViewById(R.id.caizhuang_cornermark);
        //其它用品选中后角标
        iv_qita_cornermark = (ImageView) view.findViewById(R.id.qita_cornermark);
        ivary = new ImageView[]{iv_meifa_cornermark,iv_hufu_cornermark,iv_caizhuang_cornermark,iv_qita_cornermark};

        //优质商家
        tv_shangjia = (TextView) view.findViewById(R.id.shangcheng_tv_shangjia);
        //商家下面的线
        v_line1 = (View) view.findViewById(R.id.shangcheng_v_line1);
        //优质商品
        tv_shangpin = (TextView) view.findViewById(R.id.shangcheng_tv_shangpin);
        //商家下面的线
        v_line2 = (View) view.findViewById(R.id.shangcheng_v_line2);
        //下面的viewpager
        viewPager = (pullViewpager) view.findViewById(R.id.shangcheng_viewpager);
        //scrollView
        scrollView = (RelativeLayout) view.findViewById(R.id.shangcheng_scrollView);
        //scrollView中的控件
        ll_inscrollview = (LinearLayout) view.findViewById(R.id.shangcheng_ll_inscrollview);
        //本地和全国所在控件
        rl2 = (RelativeLayout) view.findViewById(R.id.shangcheng_rl2);
        //四个按钮所在的控件
        ll1 = (LinearLayout) view.findViewById(R.id.shangcheng_ll1);
        //导航栏控件
        activity_ll1 = (LinearLayout) view.findViewById(R.id.Activity_ll1);
        //下面的控件viewpager及导航
        ll_vp = (LinearLayout) view.findViewById(R.id.psfragment_ll_vp);


    }

    @Override
    public void onStart() {
        height = rl2Height + ll1Height;
        Log.i("moveonstart", String.valueOf(height));
        super.onStart();
    }
}
