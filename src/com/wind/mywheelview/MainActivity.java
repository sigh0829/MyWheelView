package com.wind.mywheelview;

import com.wind.mywheelview.adapter.TimeAdapter;
import com.wind.mywheelview.util.StringUtil;
import com.wind.mywheelview.wheelview.WheelView;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private int mIndexWmd = 0;
	private int mIndexHour = 0;
	private int mIndexMinute = 0;
	private int mIndexPam = 0;
	private String year = null;
	/** 星期天、月份、天总合的String[] */
	private String[] wmd = null;
	/** 月份缩写 */
	private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	/** 星期缩写 日一二三四五六 */
	private String[] weeks = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	/** 天数 */
	private String[] days = null;
	/** 时 */
	private String[] hours = null;
	/** 分 */
	private String[] minutes = null;
	/** 上下午 */
	private String[] pam = {"AM", "PM"};
	
	private TextView tv_time = null;
	
	/**
	 * 初始化星期天、月份、天总合的String[]
	 * @param y
	 * @param m
	 */
	private void initWmd(String y, String m) {
		int dayCount = StringUtil.month(Integer.valueOf(y), Integer.valueOf(m));
		wmd = new String[dayCount];
		for (int i = 0; i < dayCount; i++) {
			int index = StringUtil.day1(Integer.valueOf(y), Integer.valueOf(m), Integer.valueOf(days[i]));
			wmd[i] = weeks[index] + " " + months[Integer.valueOf(m) - 1] + " " + days[i];
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		days = new String[31];
		initDays();
		hours = new String[24];
		initHours();
		minutes = new String[60];
		initMinutes();
		//例子：2013.11.16 10:48 PM(下午)
		year = "2013";
		String month = "11";
		String day = "16";
		String hour = "10";
		String minute = "48";
		mIndexHour = Integer.valueOf(hour);
		mIndexMinute = Integer.valueOf(minute);
		mIndexPam = mIndexHour > 12 ? 1 : 0;//判断上下午人数组坐标
		initWmd(year, month);
		for (int i = 0; i < wmd.length; i++) {
			Log.d("MainActivity", wmd[i]);
			if (day.equals(days[i])) {
				mIndexWmd = i;
			}
		}
		tv_time = (TextView) this.findViewById(R.id.main_textView);
		//wheelView
		this.findViewById(R.id.main_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dateWheelView(mIndexWmd, mIndexHour, mIndexMinute, mIndexPam);
			}
		});
		//时间控件
		this.findViewById(R.id.main_button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dateDialog();
			}
		});
	}
	
	/** 日期时间界面 */
	private void dateDialog() {
		Dialog dialog = new Dialog(this, R.style.MenuDialog);
		dialog.setContentView(R.layout.expressen_condition_date);
		dialog.show();
	}
	
	/**
	 * 
	 * @param indexWmd 
	 * @param indexHour
	 * @param indexMinute
	 * @param indexPam
	 */
	private void dateWheelView(int indexWmd, int indexHour, int indexMinute, int indexPam) {
		View ageView = LayoutInflater.from(this).inflate(R.layout.wb_dialog_wheelview, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(ageView);
		final AlertDialog dl = builder.create();
		dl.setCanceledOnTouchOutside(true);
		TimeAdapter adapter = new TimeAdapter(wmd);
		TimeAdapter adapter1 = new TimeAdapter(hours);
		TimeAdapter adapter2 = new TimeAdapter(minutes);
		TimeAdapter adapter3 = new TimeAdapter(pam);
		final WheelView wv_age = (WheelView) ageView.findViewById(R.id.wheelview);
		final WheelView wv_age1 = (WheelView) ageView.findViewById(R.id.wheelview1);
		final WheelView wv_age2 = (WheelView) ageView.findViewById(R.id.wheelview2);
		final WheelView wv_age3 = (WheelView) ageView.findViewById(R.id.wheelview3);
		Button bt_back = (Button) ageView.findViewById(R.id.wv_back);
		bt_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dl.dismiss();
			}
		});
		Button bt_sure = (Button) ageView.findViewById(R.id.wv_sure);
		bt_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int index = wv_age.getCurrentItem();
				int index1 = wv_age1.getCurrentItem();
				int index2 = wv_age2.getCurrentItem();
				int index3 = wv_age3.getCurrentItem();
				Log.d("MainActivity", wmd[index] + " " + hours[index1] + " " + minutes[index2] + " " + pam[index3]);
				String[] strings = wmd[index].split(" ");//获得星期、月份、日期的数组
				String changeString = year + "." + getMonth(strings[1]) + "." + strings[2] + " " + hours[index1] + ":" + minutes[index2] + " " + pam[index3];
				tv_time.setText("修改的时间为：\n" + changeString);
				Log.d("MainActivity", changeString);
				dl.dismiss();
			}
		});
		wv_age.setAdapter(adapter);
		wv_age1.setAdapter(adapter1);
		wv_age2.setAdapter(adapter2);
		wv_age3.setAdapter(adapter3);
		wv_age.setCyclic(true);
		wv_age1.setCyclic(true);
		wv_age2.setCyclic(true);
		wv_age3.setCyclic(false);
		wv_age.setVisibleItems(5);
		wv_age1.setVisibleItems(5);
		wv_age2.setVisibleItems(5);
		wv_age3.setVisibleItems(5);
		wv_age.setCurrentItem(indexWmd);//设置星期、月份、日期
		wv_age1.setCurrentItem(indexHour);//设置小时
		wv_age2.setCurrentItem(indexMinute);//设置分
		wv_age3.setCurrentItem(indexPam);//设置上下午
		if (!dl.isShowing()) {
			dl.show();
		}
	}
	
	/**
	 * 根据月份人缩写，获得月份整型：输入 Jan，返回 01月 
	 * @param sourMonth
	 * @return
	 */
	private String getMonth(String sourMonth) {
		for (int i = 0; i < months.length; i++) {
			if (months[i].equals(sourMonth)) {
				if (i < 9) {
					return "0" + String.valueOf(i + 1);
				}
				return String.valueOf(i + 1);
			}
		}
		return "";
	}
	
	private void initDays() {
		for (int i = 0; i < days.length; i++) {
			if (i < 9) {
				days[i] = "0" + String.valueOf(i + 1);
			} else {
				days[i] = String.valueOf(i + 1);
			}
		}
	}
	
	private void initHours() {
		for (int i = 0; i < hours.length; i++) {
			if (i < 10) {
				hours[i] = "0" + String.valueOf(i);
			} else {
				hours[i] = String.valueOf(i);
			}
		}
	}
	
	private void initMinutes() {
		for (int i = 0; i < minutes.length; i++) {
			if (i < 10) {
				minutes[i] = "0" + String.valueOf(i);
			} else {
				minutes[i] = String.valueOf(i);
			}
		}
	}
	
}
