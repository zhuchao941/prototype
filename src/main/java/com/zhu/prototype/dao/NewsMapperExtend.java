package com.zhu.prototype.dao;

import java.util.List;
import java.util.Map;

import com.zhu.prototype.entity.News;

public interface NewsMapperExtend {
    
	@SuppressWarnings("rawtypes")
	List<News> getNewsPageList(Map paramMap);
	
}