package com.jeffrey.controller;

import com.jeffrey.pojo.Category;
import com.jeffrey.pojo.Result;
import com.jeffrey.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @DeleteMapping
    public Result delete(Integer id) {
        categoryService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category cs = categoryService.detail(id);
        return Result.success(cs);
    }

    @PostMapping
    public Result add(@RequestBody @Validated({Category.Add.class}) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }

}
