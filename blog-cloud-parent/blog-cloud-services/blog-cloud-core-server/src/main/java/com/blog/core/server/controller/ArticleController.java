package com.blog.core.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.common.model.ResultBody;
import com.blog.core.client.model.entity.Article;
import com.blog.core.client.model.vo.ArticleQueryVo;
import com.blog.core.server.service.ArticleService;
import com.blog.core.server.service.feign.ProcessTaskClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  文章业务控制器
 * </p>
 *
 * @author 
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;




    /***
     *
     * @return
     */
    @ApiOperation(value = "文章列表条件查询",notes ="文章列表条件查询" )
    @PostMapping("/queryArticle")
    public ResultBody queryArticle(@RequestBody ArticleQueryVo query)
    {

        IPage<Article> pageEntitys = articleService.queryArticle(query);
        List<Article> records = pageEntitys.getRecords();
        long total = pageEntitys.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",records);
        map.put("pageTotal",total);
        return ResultBody.ok().data(map);
    }

    /***
     *
     * @param articleId
     * @return
     */
    @ApiOperation(value = "文章单个删除",notes ="文章单个删除" )
    @DeleteMapping("/deleteById")
    public ResultBody deleteById(@RequestParam String articleId)
    {
        boolean result = articleService.removeById(articleId);
        return result==true?ResultBody.ok():ResultBody.fail();
    }


    /***
     *
     * @param articleIds
     * @return
     */
    @ApiOperation(value = "文章批量删除",notes ="文章批量删除" )
    @PostMapping("/deleteByIds")
    public ResultBody deleteByIds(@RequestBody String[] articleIds)
    {
        List<String> ids = Arrays.asList(articleIds);
        boolean result = articleService.removeByIds(ids);
        return result==true?ResultBody.ok():ResultBody.fail();
    }


    /***    启动文章审核流程
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "文章提交审核",notes ="文章提交审核" )
    @PostMapping("/submitProcess")
    public ResultBody submitProcess(@RequestBody Article entity)
    {
        boolean result = articleService.submit(entity);

        return result==true?ResultBody.ok():ResultBody.fail();
    }

    /***    文章审核同意通过
     *
     * @param articleId
     * @return
     */
    @ApiOperation(value = "文章审核通过",notes ="文章审核通过" )
    @PostMapping("/agree")
    public ResultBody agree(@RequestParam("articleId") String articleId,
                            @RequestParam(value = "message",required = false) String message,
                            @RequestParam("result") String result)
    {

        boolean b = articleService.agree(articleId,message,result);

        return b==true?ResultBody.ok():ResultBody.fail();
    }

    /***
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "文章通用修改",notes ="文章通用修改" )
    @PostMapping("/update")
    public ResultBody update(@RequestBody Article entity)
    {
        boolean result = articleService.updateById(entity);
        return result==true?ResultBody.ok():ResultBody.fail();
    }



    /***
     *
     * @param articleId 文章ID
     * @param status 状态  0:审核中 1:审核通过 2:审核不通过
     * @return
     */
    @ApiOperation(value = "修改文章审核状态",notes ="修改文章审核状态" )
    @PostMapping("/updateSta")
    public ResultBody updateSta(@RequestParam String articleId,@RequestParam Integer status)
    {
        Article entity = new Article();
        entity.setArticleId(articleId);
        entity.setProcessStatus(status);
        boolean result = articleService.updateById(entity);
        return result==true?ResultBody.ok():ResultBody.fail();
    }

    /***
     *
     * @param articleId 文章ById查询
     * @return
     */
    @ApiOperation(value = "文章ById查询",notes ="文章ById查询" )
    @GetMapping("/selectById")
    public ResultBody updateSta(@RequestParam String articleId)
    {
        Article entity = articleService.getById(articleId);
        return ResultBody.ok().data(entity);
    }


}
