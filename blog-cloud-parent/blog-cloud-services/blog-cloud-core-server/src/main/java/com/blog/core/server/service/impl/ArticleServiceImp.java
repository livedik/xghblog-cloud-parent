package com.blog.core.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.model.ResultBody;
import com.blog.core.client.model.entity.Article;
import com.blog.core.client.model.vo.ArticleQueryVo;
import com.blog.core.server.mapper.ArticleMapper;
import com.blog.core.server.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.core.server.service.feign.ProcessTaskClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2022-11-16
 */
@Service
@Slf4j
public class ArticleServiceImp extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ProcessTaskClient processTask;

    @Override
    public IPage<Article> queryArticle(ArticleQueryVo query) {
        String title = query.getTitle(); //文件名
        String author = query.getAuthor(); // 创建人
        Integer processStatus = query.getProcessStatus(); //文件类型
        Integer releaseStatus = query.getReleaseStatus(); //文件类型
        Date strTime = query.getStrTime()==null?new Date():query.getStrTime(); //上传开始时间
        Date endTime = query.getEndTime()==null?new Date():query.getEndTime(); //上传截止时间

        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        if (!"".equals(title))queryWrapper.like("title",title);
        if (!"".equals(author))queryWrapper.like("author",author);
        if (processStatus!=null)queryWrapper.eq("process_status",processStatus);
        if (releaseStatus!=null)queryWrapper.eq("release_status",releaseStatus);
        if (strTime!=null) queryWrapper.between("create_time",strTime,endTime);



        Page<Article> page = new Page<>();
        page.setSize(query.getPageSize());
        page.setCurrent(query.getPageIndex());

        IPage<Article> ArticleInfoIPage = this.getBaseMapper().selectPage(page, queryWrapper);

        return ArticleInfoIPage;
    }

    @Override
    public boolean submit(Article entity) {
        String articleId = UUID.randomUUID().toString().replaceAll("-","");
        entity.setAuthor("bibi");
        entity.setArticleId(articleId);
        boolean result = this.save(entity);

        Map map = new HashMap<String,Object>();
        map.put("username",entity.getAuthor());

//        插入成功 启动审核流程
        if (result)processTask.startProcess("articleSubmit",articleId,map);

//        完成第一步流程
        processTask.completeTask(articleId,"","pass");

        return result;
    }

    @Override
    public boolean agree(String articleId,String message,String result) {

        ResultBody resultBody = processTask.completeTask(articleId,message,result);

        boolean complete = processTask.isComplete(articleId);
        if (complete){
            Article article = new Article();
            article.setArticleId(articleId);
//            审核通过
            article.setProcessStatus(2);
            this.updateById(article);
        }

        return true;
    }


}
