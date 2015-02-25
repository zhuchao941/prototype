package com.zhu.prototype.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhu.prototype.dao.CommentMapper;
import com.zhu.prototype.entity.Comment;
import com.zhu.prototype.entity.CommentExample;
import com.zhu.prototype.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;

	@Override
	public List<Comment> getCommentListByNewsId(String newsId) {
		CommentExample example = new CommentExample();
		example.createCriteria().andNewsIdEqualTo(newsId);
		return commentMapper.selectByExample(example);
	}

	@Override
	public int addComment(Comment comment) {
		return commentMapper.insert(comment);
	}

}
