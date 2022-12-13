package com.blog.core.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.core.client.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.core.client.model.vo.ArticleQueryVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2022-11-16
 */
public interface ArticleService extends IService<Article> {

    IPage<Article> queryArticle(ArticleQueryVo query);


    boolean submit(Article entity);

    boolean agree(String articleId,String message,String result);


}
