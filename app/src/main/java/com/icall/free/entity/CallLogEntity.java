package com.icall.free.entity;

import java.io.Serializable;

/**
 * 
 * @author wanghao 2015-4-10 通话记录item
 *
 */
public class CallLogEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8137603923913282748L;

	private int type; // 来电:1，拨出:2,未接:3
	private String phone; // 电话号码
	private String name; // 名字
	private long date; // 日期
	private long duration; // 通话时长
	private int src; // 拨号来源
	
	private boolean isSelected = false;
	
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPhone() {
		if (phone == null)
			return "";
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDate() {
		return this.date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getDuration() {
		return this.duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getSrc() {
		return this.src;
	}

	public void setSrc(int src) {
		this.src = src;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
