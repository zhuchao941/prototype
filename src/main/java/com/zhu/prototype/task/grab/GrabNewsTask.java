package com.zhu.prototype.task.grab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.zhu.prototype.entity.News;
import com.zhu.prototype.service.NewsService;
import com.zhu.prototype.task.grab.support.constant.HoopConstant;
import com.zhu.prototype.task.grab.support.model.FilterModel;
import com.zhu.prototype.task.grab.support.utils.GrabUtils;
import com.zhu.prototype.utils.IOUtils;

/**
 * @author Zhu
 * @date 2015-2-17下午5:54:54
 * @description
 * 
 */
@Component
public class GrabNewsTask implements InitializingBean {

	@Resource
	private WebApplicationContext context;

	@Resource
	private NewsService newsService;
	private NewsManager newsManager = new NewsManager();
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void afterPropertiesSet() throws Exception {
		newsManager.setNewsList(newsService.getNewsList());

		Timer timer = new Timer("新闻抓取");
		grabFromHoop(timer);
	}

	private void grabFromHoop(Timer timer) {
		grabFromHoopNewslist(timer);
	}

	/**
	 * 抓取虎扑新声
	 * 
	 * @param timer
	 */
	private void grabFromHoopNewslist(Timer timer) {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					doGrabHoopVoice();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("抓取虎扑新声出错:" + e);
				}
			}
		}, 0, 1000 * 60 * 60);

	}

	@SuppressWarnings("unchecked")
	private void doGrabHoopVoice() throws IOException {

		logger.debug("开始抓取虎扑新声--------------");

		FilterModel filterModel = new FilterModel();
		filterModel.setSelector(".news-list .otherInfo a.time");
		filterModel.setUrlAttr("abs:href");
		filterModel.setTimeAttr("title");
		filterModel.setType("voice");
		Set<String> linkSet = getLinkSet(HoopConstant.NEWS_LIST, filterModel);

		logger.debug("采集到" + linkSet.size() + "条新闻，等待采集详情.......");

		@SuppressWarnings("rawtypes")
		Map filterMap = new HashMap();
		filterMap.put("titleSelector", ".artical-title h1");
		filterMap.put("picSelector",
				".artical-content-read .artical-importantPic img");
		filterMap.put("contentSelector", ".artical-main-content");
		System.out.println("path = "
				+ context.getServletContext().getRealPath("")
				+ "/download/img/");
		filterMap.put("picPath", context.getServletContext().getRealPath("") + "/download/img/");
		List<News> newsList = null;
		newsList = grabNews(linkSet, filterMap);

		if (CollectionUtils.isNotEmpty(newsList)) {
			// 倒着添加
			newsManager.getNewsList().addAll(0, newsList);
			newsService.addNewsList(newsList);
		} else {
			logger.debug("一条新闻都没有抓取到...................");
		}
	}

	/**
	 * 用来获得页面上的链接地址列表
	 * 
	 * @param url
	 * @param filter
	 * @return
	 */
	public Set<String> getLinkSet(String url, FilterModel filter) {

		Document doc = GrabUtils.getDocument(url);
		Elements links = doc.select(filter.getSelector());
		Set<String> linkSet = new HashSet<String>();
		for (int i = 0; i < links.size(); i++) {
			Element link = links.get(i);
			String href = link.attr(filter.getUrlAttr());
			
			if (newsManager.contains(href)) {
				logger.debug(href + ":该新闻已存在");
				continue;
			}

			/**
			 * if (filter.getTimeAttr() != null) { String time =
			 * link.attr(filter.getTimeAttr()); // 同天的新闻才会被抓取 if
			 * (!time.split(" ")[0].equals(new SimpleDateFormat(
			 * "yyyy-MM-dd").format(new Date()))) { // continue; } }
			 **/

			// 过滤出虎扑新声的链接，别的页面的抓取方式不一样
			if (filter.getType() != null && filter.getType().equals("voice")) {
				if (href.indexOf("voice") > -1) {
					linkSet.add(href);
				}
			} else {
				linkSet.add(href);
			}
		}
		return linkSet;
	}

	/**
	 * 得到新闻明细，包括Title、Description、PicUrl和Url TODO
	 * 这个抓取方法应该要确保抓到的数据存在，比如说title、pic等
	 * 
	 * 这个方法是用来抓取新数据的，抓到的数据是要插入到数据库中的
	 * 
	 * @param linkList
	 *            传入一组链接，尝试连接每个链接来获得信息
	 * @param filterMap
	 *            用来作选择器，暂时还未用
	 * @return
	 * @throws IOException
	 */
	public List<News> grabNews(Set<String> linkList,
			Map<String, String> filterMap) throws IOException {

		List<News> newsList = new ArrayList<News>();

		for (String link : linkList) {

			logger.info("正在抓取" + link);
			Document doc = GrabUtils.getDocument(link);

			String title = null;
			if (filterMap.get("titleSelector") != null) {
				title = doc.select(filterMap.get("titleSelector")).text();
			}
			String picUrl = null;
			if (filterMap.get("picSelector") != null) {
				picUrl = doc.select(filterMap.get("picSelector")).attr(
						"abs:src");
			}

			// 将图片下载到本地，因为远程访问可能由于refer防止盗链无法访问403
			// fileName作为Item的picURL。可能会没有下载到图片，返回null
			String fileName = IOUtils.downloadResource(picUrl,
					filterMap.get("picPath"));
			String content = doc.select(filterMap.get("contentSelector"))
					.html();

			News news = new News();
			news.setTitle(title);
			news.setDescription(title);
			news.setContent(content);
			news.setPicurl(fileName);
			news.setSrc("虎扑体育");
			news.setUrl(link);
			news.setDate(new Date());
			newsList.add(news);

			logger.debug("抓取到[" + title + "]");
		}
		return newsList;
	}
}
