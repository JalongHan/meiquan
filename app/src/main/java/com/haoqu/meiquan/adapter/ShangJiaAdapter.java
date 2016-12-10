package com.haoqu.meiquan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.entity.ShopListAll;
import com.haoqu.meiquan.tools.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 美圈街中优质商家的listview的adapter
 * Created by apple on 16/7/13.
 */
public class ShangJiaAdapter extends BaseAdapter{

    /* Android开源框架Universal-Image-Loader */
    ImageLoader imageLoader;
    /* 图片设置 */
    DisplayImageOptions options = Options.getListOptions();;
    private LayoutInflater inflater;
    private Context context;
    private List<ShopListAll> shopList;
    private View view;


    public ShangJiaAdapter(Context context, List<ShopListAll> shopList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        this.shopList = shopList;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        view = convertView;
        if(view == null){

            mHolder = new ViewHolder();
            view = inflater.inflate(R.layout.shangcheng_store_item,null);
            mHolder.mallpic = (ImageView) view.findViewById(R.id.scfragment_iv_mallpic);
            mHolder.mallname = (TextView) view.findViewById(R.id.scfragment_tv_mallname);
            mHolder.malldesc = (TextView) view.findViewById(R.id.scfragment_tv_malldesc);
            mHolder.sps = (TextView) view.findViewById(R.id.scfragment_tv_sps);
            mHolder.xl = (TextView) view.findViewById(R.id.scfragment_tv_xl);
            view.setTag(mHolder);
        }else {
            mHolder = (ViewHolder) view.getTag();
        }

        ShopListAll splist = shopList.get(position);
        imageLoader.displayImage(splist.getMallpic(),mHolder.mallpic,options);
        mHolder.mallname.setText(splist.getMallname());
        mHolder.malldesc.setText(splist.getMalldesc());
        mHolder.sps.setText(splist.getSps());
        mHolder.xl.setText(splist.getXl());

        return view;
    }

    class ViewHolder {
        ImageView mallpic;//商家图片
        TextView mallname,//商家名
                malldesc,//商家描述
                sps,//商品数
                xl;//销量
    }


}
