package com.zhu.prototype.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhu.prototype.dao.NewsMapper;
import com.zhu.prototype.entity.News;
import com.zhu.prototype.service.NewsService;

@Service("newsService")
public class NewsServiceImpl implements NewsService{

	@Resource
	private NewsMapper newsMapper;
	
	@Override
	public int addNews(News news) {
		return newsMapper.insert(news);
	}

}
