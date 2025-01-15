package com.jeffrey.service;

import com.jeffrey.pojo.Category;

import java.util.List;

public interface CategoryService {


    void update(Category category);

    void add(Category category);

    List<Category> list();

    Category detail(Integer id);

    void delete(Integer id);
}
