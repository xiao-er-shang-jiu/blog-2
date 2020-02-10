package com.lhy.blog.web;

import com.lhy.blog.po.Blog;
import com.lhy.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true")
public class ArchiveController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public Map<Integer, List<Blog>> archives() {
        return blogService.archiveBlog();
    }
}
