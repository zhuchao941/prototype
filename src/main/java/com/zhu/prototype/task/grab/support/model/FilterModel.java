package com.zhu.prototype.task.grab.support.model;

public class FilterModel {
	private String selector;
	private String urlAttr;
	private String timeAttr;
	private String type; // 抓取的新闻类型

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public String getUrlAttr() {
		return urlAttr;
	}

	public void setUrlAttr(String urlAttr) {
		this.urlAttr = urlAttr;
	}

	public String getTimeAttr() {
		return timeAttr;
	}

	public void setTimeAttr(String timeAttr) {
		this.timeAttr = timeAttr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
