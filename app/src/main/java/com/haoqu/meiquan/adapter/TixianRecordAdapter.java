package com.haoqu.meiquan.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.entity.TixianList;
import com.haoqu.meiquan.tools.DateTools;

public class TixianRecordAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context context;
	private List<TixianList> tixianList;


	public TixianRecordAdapter( Context context,
								List<TixianList> tixianList) {
		super();
		this.inflater = LayoutInflater.from(context);;
		this.context = context;
		this.tixianList = tixianList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tixianList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tixianList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		View view = convertView;
		if(view == null){
			//重用了我的业绩中列表的项
			view = inflater.inflate(R.layout.tractivity_listview_item_detail, null);
			mHolder = new ViewHolder();
//			mHolder.tractivity_user = (TextView) view.findViewById(R.id.tractivity_user);
			mHolder.tractivity_tixiansum = (TextView) view.findViewById(R.id.tractivity_tixiansum);
			mHolder.tractivity_tixianday = (TextView) view.findViewById(R.id.tractivity_tixianday);
			mHolder.tractivity_tixianstatus = (TextView) view.findViewById(R.id.tractivity_tixianstatus);
			view.setTag(mHolder);
		}else {
			mHolder = (ViewHolder) view.getTag();
		}
		TixianList tixianList= (TixianList) getItem(position);
//		mHolder.tractivity_user.setText(tixianList.getTid());
		//显示金额
		mHolder.tractivity_tixiansum.setText(tixianList.getMoney());
		//将时间戳转换为字符串显示
		String date = tixianList.getCreatedate()+"000";
		Log.i("date", date);
		String tixianday = DateTools.getDateToString(Long.valueOf(date));
		Log.i("tixianday", tixianday);
		mHolder.tractivity_tixianday.setText(tixianday);

		//将状态码转换成文字，并显示
		String status = "";
		switch (Integer.valueOf(tixianList.getStatus())) {
			case 0:
				status = "申请中";
				break;
			case 1:
				status = "拒绝";
				break;
			case 2:
				status = "已完成";
				break;
			default:
				break;
		}
		mHolder.tractivity_tixianstatus.setText(status);

		return view;
	}

	class ViewHolder{
		TextView
//				tractivity_user,//用户
				tractivity_tixiansum,//提现金额
				tractivity_tixianday,//提现日期
				tractivity_tixianstatus;//提现状态

	}

}
