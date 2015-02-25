package com.zhu.prototype.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhu.prototype.dao.NewsMapper;
import com.zhu.prototype.dao.NewsMapperExtend;
import com.zhu.prototype.dto.PageDTO;
import com.zhu.prototype.entity.News;
import com.zhu.prototype.entity.NewsExample;
import com.zhu.prototype.service.NewsService;
import com.zhu.prototype.utils.StringUtils;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Resource
	private NewsMapper newsMapper;

	@Resource
	private NewsMapperExtend newsMapperExtend;

	@Override
	public int addNews(News news) {
		return newsMapper.insert(news);
	}

	@Override
	public List<News> getNewsList() {
		NewsExample example = new NewsExample();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.get(Calendar.DAY_OF_YEAR) - 5);
		example.createCriteria()
				.andDateGreaterThanOrEqualTo(calendar.getTime());
		List<News> newsList = newsMapper.selectByExampleWithBLOBs(example);
		return newsList;
	}

	@Transactional
	@Override
	public int addNewsList(List<News> newsList) {
		if (CollectionUtils.isEmpty(newsList)) {
			return 0;
		}
		int i = 0;
		for (News news : newsList) {
			i += addNews(news);
		}
		return i;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<News> getNewsList(Map paramMap, PageDTO pageDTO) {
		if (preparePaging(paramMap, pageDTO)) {
			return newsMapperExtend.getNewsPageList(paramMap);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected boolean preparePaging(Map paramMap, PageDTO pageDTO) {

		if (pageDTO.getRows() == 0) {
			pageDTO.setRows(10); // default value
		}
		if (pageDTO.getPage() == 0) {
			pageDTO.setPage(1); // default value
		}

		if (StringUtils.isNotBlank(pageDTO.getSort())) {
			String order = StringUtils.isBlank(pageDTO.getOrder()) ? "asc"
					: pageDTO.getOrder();
			paramMap.put("orderByClause", pageDTO.getSort() + " " + order);
		}

		paramMap.put("rows", pageDTO.getRows());
		paramMap.put("begin", (pageDTO.getPage() - 1) * pageDTO.getRows());
		return true;
	}

	@Override
	public News getNewsById(String id) {
		return newsMapper.selectByPrimaryKey(id);
	}

}
