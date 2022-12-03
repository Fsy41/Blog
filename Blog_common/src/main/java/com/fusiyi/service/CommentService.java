package com.fusiyi.service;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Fusiyi
* @description 针对表【fu_comment(评论表)】的数据库操作Service
* @createDate 2022-11-22 20:03:46
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
