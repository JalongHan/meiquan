package com.haoqu.meiquan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.entity.FriendsList;

import java.util.ArrayList;

public class FriendsListAdapter extends BaseAdapter{


	private LayoutInflater inflater;
	private Context context;
	ArrayList<FriendsList> friendsList;



	public FriendsListAdapter(
			Context context, ArrayList<FriendsList> friendsList) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.friendsList = friendsList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i("FriendsListAdapter", friendsList.toString());
		return friendsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return friendsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		View view = convertView;
		if(view == null){
			view = inflater.inflate(R.layout.friendslist_item, null);
			mHolder = new ViewHolder();
			mHolder.nickname = (TextView)view.findViewById(R.id.friendslist_nikename);
			mHolder.result = (TextView) view.findViewById(R.id.friendslist_result);
			mHolder.ranking = (TextView) view.findViewById(R.id.friendslist_tv_ranking);
            mHolder.headicon = (ImageView) view.findViewById(R.id.friendslist_friend_headicon);
			view.setTag(mHolder);

		}else{
			mHolder = (ViewHolder) view.getTag();
		}
		FriendsList list = (FriendsList) getItem(position);

		if(list.getNickname()!= null && list.getNickname().length()>0){
			Log.i("nickname",list.getNickname());
			mHolder.nickname.setText(list.getNickname());
		}else{
			Log.i("moblie",list.getMobile());
			mHolder.nickname.setText(list.getMobile());
		}

		mHolder.result.setText(list.getYeji());
        TApplication.imageLoader.displayImage(list.getHeadurl(),mHolder.headicon,TApplication.options);
		mHolder.ranking.setText(String.valueOf(position+1));

		return view;
	}

	class ViewHolder{
		TextView nickname,result,ranking;
        ImageView headicon;
	}

}
