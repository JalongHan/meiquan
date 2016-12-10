package com.haoqu.meiquan.view;

import java.util.List;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.adapter.SpinerAdapter;
import com.haoqu.meiquan.adapter.SpinerAdapter.IOnItemSelectListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
/**
 *  自定义SpinerPopWindow类
 * @author apple
 *
 */
public class SpinnerPopWindow extends PopupWindow implements OnItemClickListener{

    private Context mContext;
    private ListView mListView;
    private SpinerAdapter mAdapter;
    private IOnItemSelectListener mItemSelectListener;


    public SpinnerPopWindow(Context context)
    {
        super(context);

        mContext = context;
        init();
    }


    public void setItemListener(IOnItemSelectListener listener){
        mItemSelectListener = listener;
    }

    public void setAdatper(SpinerAdapter adapter){
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }


    private void init()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);

        setFocusable(true);
//        ColorDrawable dw = new ColorDrawable(0x00);
//        setBackgroundDrawable(dw);


        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
    }


    public void refreshData(List<String> str, int selIndex)
    {
        if (str != null && selIndex  != -1)
        {
            if (mAdapter != null){
                mAdapter.refreshData(str, selIndex);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        dismiss();
        if (mItemSelectListener != null){
            mItemSelectListener.onItemClick(pos);
        }
    }


}
