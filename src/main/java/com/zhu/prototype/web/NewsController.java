package com.zhu.prototype.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhu.prototype.dto.PageDTO;
import com.zhu.prototype.entity.News;
import com.zhu.prototype.service.NewsService;
import com.zhu.prototype.utils.StringUtils;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

	@Resource
	private NewsService newsService;

	@RequestMapping("/newsList")
	@ModelAttribute
	public String newsList(Model model) {

		List<News> newsList = newsService.getNewsList();
		newsList = newsList.subList(0, 10);
		if (CollectionUtils.isNotEmpty(newsList)) {
			int len = newsList.size();
			for (int i = 0; i < len; i++) {
				News news = newsList.get(i);
				news.setContent(StringUtils.buildDigest(news.getContent()));
			}
		}
		model.addAttribute(newsList);
		return "news/newsList";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/waterfall")
	@ModelAttribute
	public String waterfall(PageDTO pageDTO, Model model) {
		List<News> newsList = newsService.getNewsList(new HashMap(), pageDTO);
		if (CollectionUtils.isNotEmpty(newsList)) {
			int len = newsList.size();
			for (int i = 0; i < len; i++) {
				News news = newsList.get(i);
				news.setContent(StringUtils.buildDigest(news.getContent()));
			}
		}
		model.addAttribute(newsList);
		return "news/waterfall";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getNewsList")
	@ResponseBody
	public List<News> getNewsList(PageDTO pageDTO) {
		List<News> newsList = newsService.getNewsList(new HashMap(), pageDTO);
		if (CollectionUtils.isNotEmpty(newsList)) {
			int len = newsList.size();
			for (int i = 0; i < len; i++) {
				News news = newsList.get(i);
				news.setContent(StringUtils.buildDigest(news.getContent()));
			}
		}
		return newsList;
	}
}
