package com.lhy.blog.service;

import com.lhy.blog.po.Blog;
import com.lhy.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Pageable pageable, Long tagId);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listBlogTop(Integer size);

    Map<Integer, List<Blog>> archiveBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void updateAdmire(Long id, Integer t);

    void deleteBlog(Long id);
}
