package com.haoqu.meiquan.tools;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 计算并设置listview的高度.
 * Created by apple on 16/7/13.
 */
public class SetListViewHeight {

        public static int setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return 0;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            Log.i("height",String.valueOf(params.height));
            listView.setLayoutParams(params);
            return params.height;
        }





}
