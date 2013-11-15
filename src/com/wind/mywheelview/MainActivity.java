package com.wind.mywheelview;

import com.wind.mywheelview.adapter.TimeAdapter;
import com.wind.mywheelview.wheelview.WheelView;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	/** 星期天、月份、天总合的String[] */
	private String[] wmd = null;
	/** 月份缩写 */
	private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	/** 星期缩写 */
	private String[] weeks = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
	/** 天数 */
	private String[] days = null;
	/** 时 */
	private String[] hours = null;
	/** 分 */
	private String[] minutes = null;
	/** 上下午 */
	private String[] pam = {"PM", "AM"};
	
	private AlertDialog dl = null;
	private Dialog dialog = null;
	
	private void initWmd(String time) {
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		days = new String[31];
		initDays();
		wmd = months;
		hours = new String[24];
		initHours();
		minutes = new String[60];
		initMinutes();
		//wheelView
		this.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editAge();
			}
		});
		//时间控件
		this.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dateDialog();
			}
		});
	}
	
	/** 日期时间界面 */
	private void dateDialog() {
		dialog = new Dialog(this, R.style.MenuDialog);
		dialog.setContentView(R.layout.expressen_condition_date);
		dialog.show();
	}
	
	private void editAge() {
		View ageView = LayoutInflater.from(this).inflate(R.layout.wb_dialog_wheelview, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(ageView);
		dl = builder.create();
		dl.setCanceledOnTouchOutside(true);
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
				dl.dismiss();
			}
		});
		TimeAdapter adapter = new TimeAdapter(wmd);
		TimeAdapter adapter1 = new TimeAdapter(hours);
		TimeAdapter adapter2 = new TimeAdapter(minutes);
		TimeAdapter adapter3 = new TimeAdapter(pam);
		WheelView wv_age = (WheelView) ageView.findViewById(R.id.wheelview);
		WheelView wv_age1 = (WheelView) ageView.findViewById(R.id.wheelview1);
		WheelView wv_age2 = (WheelView) ageView.findViewById(R.id.wheelview2);
		WheelView wv_age3 = (WheelView) ageView.findViewById(R.id.wheelview3);
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
		if (!dl.isShowing()) {
			dl.show();
		}
	}
	
	private void initDays() {
		for (int i = 0; i < days.length; i++) {
			if (i < 10) {
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
