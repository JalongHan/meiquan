package com.haoqu.meiquan.tools;

import android.graphics.Bitmap;

import com.haoqu.meiquan.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class Options {
	public static DisplayImageOptions getListOptions(){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				//设置下载的图片是否缓存在内存中
				.cacheInMemory(true)
				//设置下载的图片是否缓存在SD卡中
				.cacheOnDisc(true)
//				// 设置图片下载期间显示的图片
//				.showImageOnLoading(android.R.drawable.ic_menu_set_as)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.mipmap.unloadimg)
				// 设置图片Uri加载失败的时候显示的图片
				.showImageOnFail(R.mipmap.unloadimg)
				//是否考虑JPEG图像EXIF参数（旋转，翻转）
				.considerExifParams(true)
				//设置图片以如何的编码方式显示
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				//设置图片的解码类型
				.bitmapConfig(Bitmap.Config.RGB_565)
				//设置图片在下载前是否重置，复位
				.resetViewBeforeLoading(true)
				//淡入
				.displayer(new FadeInBitmapDisplayer(100))
				.build();

		return options;
	}
}
