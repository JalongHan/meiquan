package com.haoqu.meiquan.adapter;

import java.util.List;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.entity.MyResults;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetMyResultsAdapter extends BaseAdapter{


	private LayoutInflater inflater;
	private Context context;
	private List<MyResults> myResult;

	public GetMyResultsAdapter(Context context, List<MyResults> myResult) {
		super();
		this.context = context;
		this.myResult = myResult;
		this.inflater = LayoutInflater.from(context);
		Log.i("adapter.myresult", myResult.toString());
	}


	@Override
	public int getCount() {
		return myResult.size();
	}

	@Override
	public Object getItem(int position) {
		return myResult.get(position);
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
			view = inflater.inflate(R.layout.mractivity_listview_item_detail, null);
			mHolder = new ViewHolder();
			mHolder.mractivity_wxnickname = (TextView) view.findViewById(R.id.mractivity_wxnickname);
			mHolder.mractivity_shangpinmingcheng = (TextView) view.findViewById(R.id.mractivity_shangpinmingcheng);
			mHolder.mractivity_xiaoshoujiage = (TextView) view.findViewById(R.id.mractivity_xiaoshoujiage);
			mHolder.mractivity_HRyongjin = (TextView) view.findViewById(R.id.mractivity_HRyongjin);
			mHolder.mractivity_leixing = (TextView) view.findViewById(R.id.mractivity_leixing);
			view.setTag(mHolder);

		}else{
			mHolder = (ViewHolder) view.getTag();
		}
		MyResults result = (MyResults) getItem(position);
		mHolder.mractivity_wxnickname.setText(result.getWxnickname());
		mHolder.mractivity_shangpinmingcheng.setText(result.getTitle().toString());
		mHolder.mractivity_xiaoshoujiage.setText("¥ "+result.getPrice().toString());
		mHolder.mractivity_HRyongjin.setText("¥ "+result.getFyp().toString());
		if(result.getLevel().equals("1")){
			mHolder.mractivity_leixing.setText("HR佣金");
		}else {
			mHolder.mractivity_leixing.setText("销售佣金");
		}



		return view;
	}

	class ViewHolder {
		TextView
				mractivity_wxnickname,//微信呢称
				mractivity_shangpinmingcheng,//商品名称
				mractivity_xiaoshoujiage,//销售价格
				mractivity_HRyongjin,//HR佣金
				mractivity_leixing;//类型
	}

}
