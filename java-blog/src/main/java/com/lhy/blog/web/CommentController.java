package com.lhy.blog.web;

import com.lhy.blog.po.Comment;
import com.lhy.blog.po.User;
import com.lhy.blog.service.BlogService;
import com.lhy.blog.service.CommentService;
import com.lhy.blog.service.UserService;
import com.lhy.blog.vo.ResponseTipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/comments/{blogId}")
    public List<Comment> comments(@PathVariable Long blogId){
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        for (Comment comment : comments) {
            comment.setBlog(null);
            for (Comment c : comment.getReplayComments()){
                c.setBlog(null);
                c.setReplayComments(null);
                c.setNickname(c.getNickname() + "@" + c.getParentComment().getNickname().split("@")[0]);
                c.setParentComment(null);
            }
        }
        return comments;
    }

    @PostMapping("/comments")
    public ResponseTipVO post(Comment comment){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        if (comment.isAdminComment()) {
            User user = userService.getUser();
            comment.setAvatar(user.getAvatar());
        }
        commentService.saveComment(comment);
        return new ResponseTipVO(1, "发布成功!");
    }
}
