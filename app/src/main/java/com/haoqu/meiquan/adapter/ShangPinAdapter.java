package com.haoqu.meiquan.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.entity.HotGoodsList;
import com.haoqu.meiquan.tools.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 美圈 街中优质商品列表的adapter
 * Created by apple on 16/7/14.
 */
public class ShangPinAdapter extends BaseAdapter {

    /* Android开源框架Universal-Image-Loader */
    ImageLoader imageLoader;
    /* 图片设置 */
    DisplayImageOptions options = Options.getListOptions();
    ;
    private LayoutInflater inflater;
    private Context context;
    private List<HotGoodsList> goodsList;

    public ShangPinAdapter(Context context, List<HotGoodsList> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
        this.imageLoader = ImageLoader.getInstance();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.personstore_list_item,null);
            mHolder = new ViewHolder();
            mHolder.thumb = (ImageView) view.findViewById(R.id.psfragment_iv_thumb);
            mHolder.title = (TextView) view.findViewById(R.id.psfragment_tv_title);
            mHolder.description = (TextView) view.findViewById(R.id.psfragment_tv_description);
            mHolder.marketprice = (TextView) view.findViewById(R.id.psfragment_tv_marketprice);
            mHolder.yuanjia = (TextView) view.findViewById(R.id.psfragment_tv_yuanjia);
            mHolder.showsales = (TextView) view.findViewById(R.id.psfragment_tv_showsales);
            mHolder.zhonglei = (RelativeLayout) view.findViewById(R.id.psfragment_rl_zhonglei);
            mHolder.line = (View) view.findViewById(R.id.psfragment_line1);
            view.setTag(mHolder);

        }else {
            mHolder = (ViewHolder) view.getTag();
        }
        HotGoodsList hotGoodsList = (HotGoodsList) getItem(position);
        imageLoader.displayImage(hotGoodsList.getThumb(),mHolder.thumb,options);
        mHolder.title.setText(hotGoodsList.getTitle());
        mHolder.description.setText(hotGoodsList.getDescription());
        mHolder.marketprice.setText(hotGoodsList.getMarketprice());
        //原价加划线
        mHolder.yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        mHolder.yuanjia.setText("¥ "+hotGoodsList.getYuanjia());
        mHolder.showsales.setText(hotGoodsList.getShowsales());
        mHolder.zhonglei.setVisibility(View.GONE);
        mHolder.line.setVisibility(View.GONE);

        return view;
    }


    class ViewHolder {
        ImageView thumb;//商品图片
        TextView title,//标题
                description,//商品描述
                marketprice,//特价
                yuanjia,//原价
                showsales;//销量
        RelativeLayout zhonglei;//种类顶部条,这次显示不需要
        View line;//虚线
    }
}
