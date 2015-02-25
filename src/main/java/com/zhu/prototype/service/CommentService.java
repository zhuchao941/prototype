package com.zhu.prototype.service;

import java.util.List;

import com.zhu.prototype.entity.Comment;

public interface CommentService {
	public List<Comment> getCommentListByNewsId(String newsId);
	
	public int addComment(Comment comment);
}
