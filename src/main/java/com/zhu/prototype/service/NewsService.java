package com.zhu.prototype.service;

import java.util.List;
import java.util.Map;

import com.zhu.prototype.dto.PageDTO;
import com.zhu.prototype.entity.News;

public interface NewsService {
	public int addNews(News news);
	
	public int addNewsList(List<News> newsList);
	
	public List<News> getNewsList();
	
	@SuppressWarnings("rawtypes")
	public List<News> getNewsList(Map paramMap, PageDTO pageDTO);
	
}
