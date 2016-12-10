package com.haoqu.meiquan.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.haoqu.meiquan.tools.Consts;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;


public class BaseActivity extends Activity {
	//退出广播
	private ExitReceiver exitReceiver = new ExitReceiver();

    private final String TAG = "BaseActivity";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface SavedInstanceState {
    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.EXITACTION);
		registerReceiver(exitReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(exitReceiver);
	}
	//注册这个关闭activity的广播
	class ExitReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("BaseActivity", "退出一个activity");
			BaseActivity.this.finish();

		}

	}

	/**
	 * 保存状态
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Field[] fields = this.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		Annotation[] ans;
		for (Field f : fields) {
			ans = f.getDeclaredAnnotations();
			for (Annotation an : ans) {
				if (an instanceof SavedInstanceState) {
					try {
						Object o = f.get(this);
						if (o == null) {
							continue;
						}
						String fieldName = f.getName();
						if (o instanceof Integer) {
							outState.putInt(fieldName, f.getInt(this));
						} else if (o instanceof String) {
							outState.putString(fieldName, (String) f.get(this));
						} else if (o instanceof Long) {
							outState.putLong(fieldName, f.getLong(this));
						} else if (o instanceof Short) {
							outState.putShort(fieldName, f.getShort(this));
						} else if (o instanceof Boolean) {
							outState.putBoolean(fieldName, f.getBoolean(this));
						} else if (o instanceof Byte) {
							outState.putByte(fieldName, f.getByte(this));
						} else if (o instanceof Character) {
							outState.putChar(fieldName, f.getChar(this));
						} else if (o instanceof CharSequence) {
							outState.putCharSequence(fieldName, (CharSequence) f.get(this));
						} else if (o instanceof Float) {
							outState.putFloat(fieldName, f.getFloat(this));
						} else if (o instanceof Double) {
							outState.putDouble(fieldName, f.getDouble(this));
						} else if (o instanceof String[]) {
							outState.putStringArray(fieldName, (String[]) f.get(this));
						} else if (o instanceof Parcelable) {
							outState.putParcelable(fieldName, (Parcelable) f.get(this));
						} else if (o instanceof Serializable) {
							outState.putSerializable(fieldName, (Serializable) f.get(this));
						} else if (o instanceof Bundle) {
							outState.putBundle(fieldName, (Bundle) f.get(this));
						}
					} catch (IllegalArgumentException e) {
						Log.e(TAG, e.getMessage());
					} catch (IllegalAccessException e) {
						Log.e(TAG, e.getMessage());
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
					}
				}
			}
		}

		super.onSaveInstanceState(outState);
	}

	/**
	 * 在这里恢复数据
	 *
	 * @param savedInstanceState
	 */
	private void restoreInstanceState(Bundle savedInstanceState) {
		Field[] fields = this.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		Annotation[] ans;
		for (Field f : fields) {
			ans = f.getDeclaredAnnotations();
			for (Annotation an : ans) {
				if (an instanceof SavedInstanceState) {
					try {
						String fieldName = f.getName();
						Class cls = f.getType();
						if (cls == int.class || cls == Integer.class) {
							f.setInt(this, savedInstanceState.getInt(fieldName));
						} else if (String.class.isAssignableFrom(cls)) {
							f.set(this, savedInstanceState.getString(fieldName));
						} else if (Serializable.class.isAssignableFrom(cls)) {
							f.set(this, savedInstanceState.getSerializable(fieldName));
						} else if (cls == long.class || cls == Long.class) {
							f.setLong(this, savedInstanceState.getLong(fieldName));
						} else if (cls == short.class || cls == Short.class) {
							f.setShort(this, savedInstanceState.getShort(fieldName));
						} else if (cls == boolean.class || cls == Boolean.class) {
							f.setBoolean(this, savedInstanceState.getBoolean(fieldName));
						} else if (cls == byte.class || cls == Byte.class) {
							f.setByte(this, savedInstanceState.getByte(fieldName));
						} else if (cls == char.class || cls == Character.class) {
							f.setChar(this, savedInstanceState.getChar(fieldName));
						} else if (CharSequence.class.isAssignableFrom(cls)) {
							f.set(this, savedInstanceState.getCharSequence(fieldName));
						} else if (cls == float.class || cls == Float.class) {
							f.setFloat(this, savedInstanceState.getFloat(fieldName));
						} else if (cls == double.class || cls == Double.class) {
							f.setDouble(this, savedInstanceState.getDouble(fieldName));
						} else if (String[].class.isAssignableFrom(cls)) {
							f.set(this, savedInstanceState.getStringArray(fieldName));
						} else if (Parcelable.class.isAssignableFrom(cls)) {
							f.set(this, savedInstanceState.getParcelable(fieldName));
						} else if (Bundle.class.isAssignableFrom(cls)) {
							f.set(this, savedInstanceState.getBundle(fieldName));
						}
					} catch (IllegalArgumentException e) {
						Log.e(TAG, e.getMessage());
					} catch (IllegalAccessException e) {
						Log.e(TAG, e.getMessage());
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
					}

				}
			}
		}
	}



}
