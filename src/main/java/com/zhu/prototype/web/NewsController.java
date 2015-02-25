package com.zhu.prototype.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhu.prototype.dto.PageDTO;
import com.zhu.prototype.entity.Comment;
import com.zhu.prototype.entity.News;
import com.zhu.prototype.service.CommentService;
import com.zhu.prototype.service.NewsService;
import com.zhu.prototype.utils.CollectionUtils;
import com.zhu.prototype.utils.StringUtils;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

	@Resource
	private NewsService newsService;
	@Resource
	private CommentService commentService;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/newsList")
	@ModelAttribute
	public String newsList(PageDTO pageDTO, Model model) {

		pageDTO.setOrder("desc");
		pageDTO.setSort("date");
		List<News> newsList = newsService.getNewsList(new HashMap(), pageDTO);
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

	@SuppressWarnings("unchecked")
	@RequestMapping("/details/{news}")
	public ModelAndView details(@ModelAttribute("news") News news) {
		if (news == null || news.getId() == null) {
			return null;
		}
		ModelAndView mv = new ModelAndView("news/details");
		List<Comment> commentList = commentService.getCommentListByNewsId(news
				.getId());
		Map<String, List<Comment>> map = new HashMap<String, List<Comment>>(1);
		if (CollectionUtils.isNotEmpty(commentList)) {
			map = CollectionUtils.group(commentList, "replyId");
		}
		mv.addObject("commentMap", map);
		return mv;
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

	@RequestMapping(value = "/details/submitComment", method = RequestMethod.POST)
	public String submitComment(Comment comment) {
		comment.setDate(new Date());
		comment.setStatus(1);
		comment.setUserId("zhu");
		commentService.addComment(comment);
		return "redirect:" + "/news/details/" + comment.getNewsId();
	}

}
