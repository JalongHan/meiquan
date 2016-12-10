package com.haoqu.meiquan.adapter;

import java.util.List;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.entity.AppointGoosList;
import com.haoqu.meiquan.tools.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpActivityRefreshGrieViewAdapter extends BaseAdapter {

	/* Android开源框架Universal-Image-Loader */
	ImageLoader imageLoader;
	/* 图片设置 */
	DisplayImageOptions options = Options.getListOptions();;
	private LayoutInflater inflater;
	private Context context;
	private List<AppointGoosList> goodsList;
	private AppointGoosList list;

	public SpActivityRefreshGrieViewAdapter(Context context,
											List<AppointGoosList> goodsList) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		this.goodsList = goodsList;
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
		ViewHolder mHolder = null;

		View view = convertView;
		if (view == null) {

			view = inflater.inflate(R.layout.shangpinlist_gridview_item, null);
			mHolder = new ViewHolder();
			mHolder.spActivity_iv_pic = (ImageView) view
					.findViewById(R.id.spActivity_iv_pic);
			mHolder.spActivity_tv_title = (TextView) view
					.findViewById(R.id.spActivity_tv_title);
			mHolder.spActivity_tv_description = (TextView) view.findViewById(R.id.spActivity_tv_description);
			mHolder.spActivity_tv_prices = (TextView) view
					.findViewById(R.id.spActivity_tv_prices);
			mHolder.spActivity_tv_yuanjia = (TextView) view
					.findViewById(R.id.spActivity_tv_yuanjia);

			view.setTag(mHolder);

		} else {

			mHolder = (ViewHolder) view.getTag();

		}
		list = (AppointGoosList) getItem(position);
		mHolder.spActivity_tv_title.setText(list.getTitle());
		mHolder.spActivity_tv_prices.setText(list.getMarketprice());
		mHolder.spActivity_tv_description.setText(list.getDescription());


        mHolder.spActivity_tv_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		mHolder.spActivity_tv_yuanjia.setText(list.getYuanjia());

		imageLoader.displayImage(list.getThumb(), mHolder.spActivity_iv_pic,
				options);


		return view;
	}

	class ViewHolder {
		/* 图片 */
		ImageView spActivity_iv_pic;
		// 标题title,描述description，价格　prices ,原价yuanjia
		TextView spActivity_tv_title, spActivity_tv_description,spActivity_tv_prices,spActivity_tv_yuanjia;
	}

}
