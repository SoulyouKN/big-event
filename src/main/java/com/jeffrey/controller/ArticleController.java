package com.jeffrey.controller;

import com.jeffrey.pojo.Article;
import com.jeffrey.pojo.Category;
import com.jeffrey.pojo.PageBean;
import com.jeffrey.pojo.Result;
import com.jeffrey.service.ArticleService;
import com.jeffrey.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article as = articleService.detail(id);
        return Result.success(as);
    }

    @PutMapping
    public Result update(@Validated(Article.Update.class) @RequestBody Article article) {
        articleService.update(article);
        return Result.success();

    }

    @DeleteMapping
    public Result delete(Integer id) {
        articleService.delete(id);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize,
                                  @RequestParam(required = false) String categoryId,
                                  @RequestParam(required = false) String state) {
        PageBean<Article> pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @PostMapping
    public Result add(@RequestBody @Validated(Article.Add.class) Article article) {
        articleService.add(article);
        return Result.success();
    }

}
