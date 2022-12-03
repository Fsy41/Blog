package com.fusiyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fusiyi.domain.entity.ArticleTag;
import com.fusiyi.service.ArticleTagService;
import com.fusiyi.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author Fusiyi
* @description 针对表【fu_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2022-11-24 16:01:07
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




