package com.haoqu.meiquan.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.entity.PSListEntity;
import com.haoqu.meiquan.tools.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class personaStoreFragmentListViewAdapter extends BaseAdapter implements View.OnClickListener{
    /* Android开源框架Universal-Image-Loader */
    ImageLoader imageLoader;
    /* 图片设置 */
    DisplayImageOptions options = Options.getListOptions();
    ;
    private LayoutInflater inflater;
    private Context context;
    private List<PSListEntity> list;
    private PSListEntity psListEntity;
    private Callback mCallback;

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }


    /**
     * 自定义接口,用于回调按钮点击事件到Fragment
     */

    public interface Callback{
        public void click(View v);
    }

    public personaStoreFragmentListViewAdapter(Context context, List<PSListEntity> list,Callback callback) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        this.list = list;
        this.mCallback = callback;
        Log.i("personaStoreFragmentLi", list.toString());

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder mHolder;
        View view = convertView;
        Log.i("personaStoreFragmentLi", list.toString());
        if (view == null) {
            view = inflater.inflate(R.layout.personstore_list_item, null);

            mHolder = new ViewHolder();
            mHolder.rl_shangpin = (RelativeLayout) view.findViewById(R.id.psfragment_rl_shangpin);
            mHolder.rl_zhonglei = (RelativeLayout) view.findViewById(R.id.psfragment_rl_zhonglei);
            mHolder.thumb = (ImageView) view.findViewById(R.id.psfragment_iv_thumb);

            mHolder.tv_zhonglei = (TextView) view.findViewById(R.id.psfragment_tv_zhonglei);
            mHolder.title = (TextView) view.findViewById(R.id.psfragment_tv_title);
            mHolder.description = (TextView) view.findViewById(R.id.psfragment_tv_description);
            mHolder.marketprice = (TextView) view.findViewById(R.id.psfragment_tv_marketprice);
            mHolder.yuanjia = (TextView) view.findViewById(R.id.psfragment_tv_yuanjia);
            mHolder.showsales = (TextView) view.findViewById(R.id.psfragment_tv_showsales);
            mHolder.line = (View) view.findViewById(R.id.psfragment_line1);


            view.setTag(mHolder);

        } else {
            mHolder = (ViewHolder) view.getTag();
        }

        psListEntity = (PSListEntity) getItem(position);

        //给mholder中的各控件赋值
        mHolder.tv_zhonglei.setText(psListEntity.getName());
        mHolder.title.setText(psListEntity.getTitle());
        imageLoader.displayImage(psListEntity.getThumb(), mHolder.thumb, options);
        mHolder.marketprice.setText(psListEntity.getMarketprice());
        mHolder.description.setText(psListEntity.getDescription());
        //给原价加划线
        mHolder.yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        mHolder.yuanjia.setText("¥ "+psListEntity.getYuanjia());
        mHolder.showsales.setText(psListEntity.getShowsales());
        //给两个分类加上点击事件
        mHolder.rl_shangpin.setTag(position);
        mHolder.rl_shangpin.setOnClickListener(this);
        mHolder.rl_zhonglei.setTag(position);
        mHolder.rl_zhonglei.setOnClickListener(this);

        //根据条件显示或隐藏分组名
        //根据位置获得到种类名
        String name = getZhonglei(position);
        //根据种类名找到这个种类最小的位置
        int minPos = getPosFromZhonglei(name);
        //隐藏或显示 item分组
        if (position == minPos) {
            mHolder.rl_zhonglei.setVisibility(View.VISIBLE);
            mHolder.line.setVisibility(View.VISIBLE);
        } else {
            mHolder.rl_zhonglei.setVisibility(View.GONE);
            mHolder.line.setVisibility(View.GONE);
        }

        return view;
    }

    /**
     * 根据种类名找到这个种类最小的位置
     *
     * @param name
     * @return
     */
    private int getPosFromZhonglei(String name) {
        for (int i = 0; i < list.size(); i++) {
            String na = getZhonglei(i);
            if (na.equals(name)) {
                return i;
            }
        }

        return -1;
    }


    /**
     * //根据位置获得到种类名
     *
     * @param position
     * @return 位置的种类名
     */
    public String getZhonglei(int position) {
        return list.get(position).getName();
    }

    ;


    public class ViewHolder {
        public RelativeLayout rl_shangpin,//商品列表
                rl_zhonglei;//种类列表
        public ImageView thumb;//图片地址
        public TextView tv_zhonglei,//种类
                title,//原价
                description,//描述
                marketprice,//售价,特价
                yuanjia,//原价
                showsales;//销量
        public View line;

    }

}
