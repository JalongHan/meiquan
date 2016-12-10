package com.haoqu.meiquan.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.TApplication;
import com.haoqu.meiquan.fragment.FriendsListFragment;
import com.haoqu.meiquan.fragment.MyCenterFragment;
import com.haoqu.meiquan.fragment.PersonaStoreFragment;
import com.haoqu.meiquan.fragment.ShangChengFragment;
import com.haoqu.meiquan.tools.Consts;
import com.haoqu.meiquan.view.MyToast;

public class MainActivity extends BaseActivity {

    private FriendsListFragment friendsListFragment;

    private PersonaStoreFragment personaStoreFragment;

    private MyCenterFragment myCenterFragment;

    private ShangChengFragment shangChengFragment;


    RelativeLayout[] rlArray = new RelativeLayout[4];

    ImageView[] ivArray = new ImageView[4];

    TextView[] tvArray = new TextView[4];
    //fragment数组
    Fragment[] fragments = null;

    /**
     * 当前显示的fragment
     */

    int currentIndex = 0;

    /**
     * 选中的button,显示下一个fragment
     */

    int selectedIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Log.i("savedInstanceState", "null");

        }
        initViews();
        setListenser();
        Log.i("mainactivity", "执行了");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    private void initViews() {
        rlArray[0] = (RelativeLayout) findViewById(R.id.main_rl_personstore);
        rlArray[1] = (RelativeLayout) findViewById(R.id.main_rl_friends);
        rlArray[2] = (RelativeLayout) findViewById(R.id.main_rl_store);
        rlArray[3] = (RelativeLayout) findViewById(R.id.main_rl_mycneter);


        ivArray[0] = (ImageView) findViewById(R.id.main_iv_personstore);
        ivArray[1] = (ImageView) findViewById(R.id.main_iv_friends);
        ivArray[2] = (ImageView) findViewById(R.id.main_iv_store);
        ivArray[3] = (ImageView) findViewById(R.id.main_iv_mycneter);


        tvArray[0] = (TextView) findViewById(R.id.main_tv_personstore);
        tvArray[1] = (TextView) findViewById(R.id.main_tv_friends);
        tvArray[2] = (TextView) findViewById(R.id.main_tv_store);
        tvArray[3] = (TextView) findViewById(R.id.main_tv_mycneter);

        ivArray[0].setSelected(true);
        tvArray[0].setSelected(true);

        personaStoreFragment = new PersonaStoreFragment();
        friendsListFragment = new FriendsListFragment();
        shangChengFragment = new ShangChengFragment();
        myCenterFragment = new MyCenterFragment();


        fragments = new Fragment[]{personaStoreFragment, friendsListFragment, shangChengFragment, myCenterFragment};

        //最开始显示第一个fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment, fragments[0]);
        transaction.show(fragments[0]);
        transaction.commit();


    }

    private void setListenser() {
        MyListener myListener = new MyListener();

        for (int i = 0; i < rlArray.length; i++) {
            rlArray[i].setOnClickListener(myListener);
        }

    }


    class MyListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_rl_personstore:
                    selectedIndex = 0;
                    break;
                case R.id.main_rl_friends:
                    selectedIndex = 1;
                    break;
                case R.id.main_rl_store:
                    selectedIndex = 2;
                    break;
                case R.id.main_rl_mycneter:
                    selectedIndex = 3;
                    break;
            }

            //判断单击是不是当前的
            if (selectedIndex != currentIndex) {
                //不是当前的
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                //当前hide
                fragmentTransaction.hide(fragments[currentIndex]);
                Log.i("fragments", fragments.toString());
                //show你选中
                if (!fragments[selectedIndex].isAdded()) {
                    //以前没添加过

                    fragmentTransaction.add(R.id.fragment, fragments[selectedIndex]);
                }
                //事务显示
                fragmentTransaction.show(fragments[selectedIndex]);
                fragmentTransaction.commit();
                ivArray[selectedIndex].setSelected(true);
                ivArray[currentIndex].setSelected(false);

                tvArray[selectedIndex].setSelected(true);
                tvArray[currentIndex].setSelected(false);
                currentIndex = selectedIndex;
            }
        }
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                MyToast.Toast(this,"再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                //发广播 关闭所有 activity
                TApplication.instance.sendBroadcast(new Intent(Consts.EXITACTION));
            }
            return true;

        }

        //拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

