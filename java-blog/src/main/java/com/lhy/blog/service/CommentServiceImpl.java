package com.lhy.blog.service;

import com.lhy.blog.dao.CommentRepository;
import com.lhy.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment){
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }
        else {
            comment.setParentComment(null);
        }
        return commentRepository.save(comment);
    }

    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replays = comment.getReplayComments();
            for (Comment replay : replays) {
                recursively(replay);
            }
            comment.setReplayComments(tempReplays);
            tempReplays = new ArrayList<>();
        }
    }

    private List<Comment> tempReplays = new ArrayList<>();

    private void recursively(Comment comment){
        tempReplays.add(comment);
        if (comment.getReplayComments().size() > 0) {
            List<Comment> replays = comment.getReplayComments();
            for (Comment replay : replays) {
                recursively(replay);
            }
        }
    }
}
