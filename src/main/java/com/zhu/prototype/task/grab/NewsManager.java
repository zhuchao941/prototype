package com.zhu.prototype.task.grab;

import java.util.ArrayList;
import java.util.List;

import com.zhu.prototype.entity.News;

/**
 * @author Zhu
 * @date 2015-2-17下午5:42:06
 * @description
 * 
 */
public class NewsManager {
	/** 缓存的news **/
	private List<News> newsList = new ArrayList<News>();

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public boolean contains(String link) {
		for (News news : newsList) {
			if (news.getUrl().equals(link)) {
				return true;
			}
		}
		return false;
	}

}