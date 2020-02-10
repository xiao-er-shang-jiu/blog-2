package com.lhy.blog.web;

import com.lhy.blog.po.Blog;
import com.lhy.blog.po.Tag;
import com.lhy.blog.po.Type;
import com.lhy.blog.service.BlogService;
import com.lhy.blog.service.TagService;
import com.lhy.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true")
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogsIndex")
    public Page<Blog> blogsIndex(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable){
        Page<Blog> blogs = blogService.listBlog(pageable);
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

    @PostMapping("/search")
    public Page<Blog> search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam String query){
        Page<Blog> blogs = blogService.listBlog("%" + query + "%", pageable);
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

    @GetMapping("/typesIndex")
    public List<Type> typesIndex(){
        List<Type> types = typeService.listTypeTop(6);
        for (Type type : types){
            type.setCount(type.getBlogs().size());
            type.setBlogs(null);
        }
        return types;
    }

    @GetMapping("/tagsIndex")
    public List<Tag> tagIndex(){
        List<Tag> tags = tagService.listTagTop(10);
        for (Tag tag : tags){
            tag.setCount(tag.getBlogs().size());
            tag.setBlogs(null);
        }
        return tags;
    }

    @GetMapping("/newBlogsIndex")
    public List<Blog> newBlogsIndex(){
        List<Blog> blogs = blogService.listBlogTop(8);
        for (Blog blog : blogs){
            blog.setContent(null);
            blog.setType(null);
            blog.setTags(null);
            blog.setUser(null);
            blog.setComments(null);
        }
        return blogs;
    }

    @GetMapping("/blog/{id}")
    public Blog blog(@PathVariable Long id) {
        Blog blog = blogService.getBlog(id);
        blog.getType().setBlogs(null);
        for(Tag tag : blog.getTags())
            tag.setBlogs(null);
        blog.setComments(null);
        blog.getUser().setBlogs(null);
        return blog;
    }

    @GetMapping("/blog/admire/{id}/{admire}")
    public void admire(@PathVariable Long id, @PathVariable Integer admire){
        blogService.updateAdmire(id, admire);
    }

    @GetMapping("/newBlogs")
    public List<Blog> newBlogs(){
        List<Blog> blogs = blogService.listBlogTop(3);
        for (Blog blog : blogs) {
            blog.setContent(null);
            blog.setType(null);
            blog.setTags(null);
            blog.setComments(null);
            blog.setUser(null);
        }
        return blogs;
    }
}
