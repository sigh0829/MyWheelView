package com.wind.mywheelview.adapter;

public class TimeAdapter implements WheelAdapter {
	
	/** 上下午 */
	private String[] strings = null;
	
	public TimeAdapter(String[] strings) {
		this.strings = strings;
	}

	@Override
	public int getItemsCount() {
		// TODO Auto-generated method stub
		return strings.length;
	}

	@Override
	public String getItem(int index) {
		// TODO Auto-generated method stub
		return strings[index];
	}

	@Override
	public int getMaximumLength() {
		// TODO Auto-generated method stub
		return strings[strings.length - 1].length();
	}

}
