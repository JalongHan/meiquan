package com.haoqu.meiquan.tools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.haoqu.meiquan.R;
import com.haoqu.meiquan.entity.PSListEntity;
import com.haoqu.meiquan.entity.PersonStoreList;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tools {
	private static ProgressDialog progressDialog;
    private static ImageView progressbar;

    /**
	 * 显示进度条
	 *
	 * @param activity
	 */
	public static void showProgressDialog(Activity activity) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(activity);
			progressDialog.show();

			View view=View.inflate(activity, R.layout.dialog,null);

            progressbar = (ImageView)view.findViewById(R.id.progressbar_iv);

            Animation anim = AnimationUtils.loadAnimation(activity, R.anim.rotate);
            progressbar.startAnimation(anim);

			//progressDialog.setMessage(msg);
//			TextView tv= (TextView) view.findViewById(R.id.tv_dialog);
//			tv.setText(msg);
			progressDialog.getWindow().setContentView(view);
			view.measure(0,0);

			progressDialog.getWindow().setLayout(view.getMeasuredWidth(),view.getMeasuredHeight());

			progressDialog.setCanceledOnTouchOutside(false);

		}
	}

	public static void closeProgressDialog() {
		if (progressDialog != null) {
            progressbar.clearAnimation();
			progressDialog.cancel();
			progressDialog = null;
			System.gc();
		}
	}

	/**
	 * 保存文件
	 * @param bm bitmap
	 * @param fileName filename
	 * @param context context
	 * @return
	 * @throws IOException
     */
	public static File saveFile(Bitmap bm, String fileName, Context context) throws IOException {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "meiquan/headicon");
		String path = cacheDir.getPath();
		if(!cacheDir.exists()){
			cacheDir.mkdir();
		}
		File myCaptureFile = new File(path +"/"+ fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
		return myCaptureFile;
	}

	/**
	 * 将首页需要的list转换为能用的list
	 * @param personList 传进来 的personlist
     * @return
     */
	public static List<PSListEntity> ListParse(List<PersonStoreList> personList) {
		List<PSListEntity> list = new ArrayList<PSListEntity>();
		for (int i = 0; i < personList.size(); i++) {
			for (int j = 0; j < personList.get(i).getGoods().size(); j++) {

				PersonStoreList personStoreList = personList.get(i);
				PSListEntity psListEntity = new PSListEntity(
						personStoreList.getGoods().get(j).getCostprice(),
						personStoreList.getGoods().get(j).getDescription(),
						personStoreList.getId(),
						personStoreList.getGoods().get(j).getMarketprice(),
						personList.get(i).getName(),
						personList.get(i).getGoods().get(j).getShowsales(),
						personList.get(i).getGoods().get(j).getId(),
						personStoreList.getGoods().get(j).getThumb(),
						personStoreList.getGoods().get(j).getTitle(),
						personStoreList.getGoods().get(j).getYuanjia()

				);


				list.add(psListEntity);
			}
		}
		return list;
	}

}
