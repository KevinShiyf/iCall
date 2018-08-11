package com.icall.free.event;

public class OrderEvent {
	public static final String ORDER_TYPE_COMMON = "common";
	public static final String ORDER_TYPE_FLOW = "flow";
	private String notify_url;

	private String order_no;
	private double gap;
	private double used_kubi;
	private double used_money;
	private String order_type;

	private boolean payable_by_kubi;

	private int orderTag;

	public double getUsed_kubi() {
		return used_kubi;
	}

	public void setUsed_kubi(double used_kubi) {
		this.used_kubi = used_kubi;
	}

	public double getUsed_money() {
		return used_money;
	}

	public void setUsed_money(double used_money) {
		this.used_money = used_money;
	}

	public int getOrderTag() {
		return orderTag;
	}

	public void setOrderTag(int orderTag) {
		this.orderTag = orderTag;
	}

	public boolean isPayable_by_kubi() {
		return payable_by_kubi;
	}

	public void setPayable_by_kubi(boolean payable_by_kubi) {
		this.payable_by_kubi = payable_by_kubi;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public double getGap() {
		return gap;
	}

	public void setGap(double gap) {
		this.gap = gap;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	@Override
	public String toString() {
		return "OrderEvent [notify_url=" + notify_url + ", order_no=" + order_no + ", gap=" + gap + "]";
	}

}
