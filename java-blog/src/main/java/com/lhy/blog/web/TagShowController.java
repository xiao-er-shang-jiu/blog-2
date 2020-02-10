package com.lhy.blog.web;

import com.lhy.blog.po.Blog;
import com.lhy.blog.po.Tag;
import com.lhy.blog.service.BlogService;
import com.lhy.blog.service.TagService;
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
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags")
    public List<Tag> types() {
        List<Tag> tags = tagService.listTagTop(1000);
        for (Tag tag : tags){
            tag.setCount(tag.getBlogs().size());
            tag.setBlogs(null);
        }
        return tags;
    }

    @GetMapping("/tags/{id}")
    public Page<Blog> blogs(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                            @PathVariable Long id) {
        if (id == 0) {
            Tag tag = tagService.listTagTop(1).get(0);
            id = tag.getId();
        }
        Page<Blog> blogs = blogService.listBlog(pageable, id);
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
