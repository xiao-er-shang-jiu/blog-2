package com.lhy.blog.web;

import com.lhy.blog.po.Blog;
import com.lhy.blog.po.Tag;
import com.lhy.blog.po.Type;
import com.lhy.blog.service.BlogService;
import com.lhy.blog.service.TypeService;
import com.lhy.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true")
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types")
    public List<Type> types() {
        List<Type> types = typeService.listTypeTop(1000);
        for (Type type : types){
            type.setCount(type.getBlogs().size());
            type.setBlogs(null);
        }
        return types;
    }

    @GetMapping("/types/{id}")
    public Page<Blog> blogs(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                            @PathVariable Long id) {
        if (id == 0) {
            Type type = typeService.listTypeTop(1).get(0);
            id = type.getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        Page<Blog> blogs = blogService.listBlog(pageable, blogQuery);
        for (Blog blog : blogs){
            blog.setContent(null);
            blog.getType().setBlogs(null);
            for (Tag tag : blog.getTags()) {
                tag.setBlogs(null);
            }
            blog.getUser().setBlogs(null);
            blog.setComments(null);
        }
        return blogs;
    }
}
