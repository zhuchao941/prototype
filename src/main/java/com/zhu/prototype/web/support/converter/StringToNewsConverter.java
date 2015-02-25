package com.zhu.prototype.web.support.converter;

import javax.annotation.Resource;

import org.springframework.core.convert.converter.Converter;

import com.zhu.prototype.entity.News;
import com.zhu.prototype.service.NewsService;

public class StringToNewsConverter implements Converter<String, News> {

	@Resource
	private NewsService newsService;

	@Override
	public News convert(String source) {
		News news= newsService.getNewsById(source);
		return news;
	}
}
