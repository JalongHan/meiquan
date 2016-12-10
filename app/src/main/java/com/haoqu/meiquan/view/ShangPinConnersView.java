package com.haoqu.meiquan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
/**
 * 商品列表中的item，将此item切为圆角，使图片也会上半部分圆角显示下半部分还是直角
 * @author apple
 *
 */
public class ShangPinConnersView extends RelativeLayout{
	/*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
//	private float[] rids = {10.0f,10.0f,10.0f,10.0f,0.0f,0.0f,0.0f,0.0f,};
	public ShangPinConnersView(Context context, AttributeSet attrs,
							   int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public ShangPinConnersView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public ShangPinConnersView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onDraw(Canvas canvas) {
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//		Bitmap rawBitmap = getBitmap(getDrawable());
//		if(rawBitmap != null){
//			int viewWidth = getWidth();
//			int viewHeight = getHeight();
//			int viewMinSize = Math.min(viewWidth, viewHeight);
//			
//		}
//		canvas.drawRoundRect(rect, rx, ry, paint)
		Path path = new Path();
		int w = getWidth();
		int h = getHeight();
//		path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
		path.addRoundRect(new RectF(0, 0, w, h), 10.0f, 10.0f, Path.Direction.CW);
		path.close();
		canvas.clipPath(path);
//		canvas.drawPath(path, paint);

		super.onDraw(canvas);
	}
	private Bitmap getBitmap(Drawable drawable) {
		if(drawable instanceof BitmapDrawable){
			return ((BitmapDrawable) drawable).getBitmap();
		}else if(drawable instanceof ColorDrawable){
			Rect rect = drawable.getBounds();
			int width = rect.right - rect.left;
			int height = rect.bottom - rect.top;
			int color = ((ColorDrawable) drawable).getColor();
			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

			Canvas canvas = new Canvas(bitmap);
			canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));

			return bitmap;
		}


		return null;
	}
}
