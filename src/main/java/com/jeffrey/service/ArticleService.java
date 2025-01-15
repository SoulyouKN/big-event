package com.jeffrey.service;

import com.jeffrey.pojo.Article;
import com.jeffrey.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);

    void delete(Integer id);

    void update(Article article);

    Article detail(Integer id);
}
