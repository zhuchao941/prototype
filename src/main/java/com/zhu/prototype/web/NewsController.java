package com.zhu.prototype.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhu.prototype.entity.News;
import com.zhu.prototype.service.NewsService;

@Controller
@RequestMapping("/user")
public class NewsController {
	
	@Resource
	private NewsService newsService;
	
	@RequestMapping("/testInsertNews")
	public String testInsertNews(News news){
		int i = newsService.addNews(news);
		System.out.println(news.getId());
		System.out.println(i);
		return null;
	}
}
