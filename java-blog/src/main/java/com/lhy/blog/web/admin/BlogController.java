package com.lhy.blog.web.admin;

import com.lhy.blog.po.Blog;
import com.lhy.blog.po.User;
import com.lhy.blog.service.BlogService;
import com.lhy.blog.service.TagService;
import com.lhy.blog.service.TypeService;
import com.lhy.blog.vo.BlogQuery;
import com.lhy.blog.vo.ResponseTipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @PostMapping("/blogs/search")
    public Page<Blog> search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             BlogQuery blogQuery){
        Page<Blog> page = blogService.listBlog(pageable, blogQuery);
        for(Blog blog : page.getContent()) {
            blog.setContent(null);
            blog.setType(null);
            blog.setTags(null);
            blog.getUser().setBlogs(null);
            blog.setComments(null);
        }
        return page;
    }

    @GetMapping("/blogs/{id}/input")
    public Blog editInput(@PathVariable Long id){
        Blog blog = blogService.getBlog(id);
        blog.init();
        blog.getType().setBlogs(null);
        blog.setTags(null);
        blog.setComments(null);
        blog.setUser(null);
        blog.setDescription(null);
        return blog;
    }

    @PostMapping("/blogs")
    public ResponseTipVO post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if(b == null){
            return new ResponseTipVO(0, "操作失败!");
        } else {
           return new ResponseTipVO(1, "操作成功!");
        }
    }

    @GetMapping("/blogs/{id}/delete")
    public ResponseTipVO delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        return new ResponseTipVO(1, "操作成功!");
    }
}
