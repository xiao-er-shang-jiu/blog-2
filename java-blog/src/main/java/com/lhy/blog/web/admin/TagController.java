package com.lhy.blog.web.admin;

import com.lhy.blog.po.Tag;
import com.lhy.blog.service.TagService;
import com.lhy.blog.vo.ResponseTipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public Page<Tag> tags(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable){
        Page<Tag> page = tagService.listTag(pageable);
        for(Tag tag : page.getContent()) {
            tag.setCount(tag.getBlogs().size());
            tag.setBlogs(null);
        }
        return page;
    }

    @GetMapping("/tags/getAll")
    public List<Tag> allTags(){
        List<Tag> tags = tagService.listTag();
        for (Tag tag : tags)
            tag.setBlogs(null);
        return tags;
    }

    @PostMapping("/tags/addOrUpdate")
    public ResponseTipVO post(@Valid Tag tag, BindingResult result){
        if(result.hasErrors()){
            return new ResponseTipVO(0, result.getAllErrors().get(0).getDefaultMessage());
        }
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            return new ResponseTipVO(0, "标签名重复!");
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            return new ResponseTipVO(0, "操作失败!");
        }else {
            return new ResponseTipVO(1, "操作成功!");
        }
    }

    @GetMapping("/tags/{id}/delete")
    public ResponseTipVO delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        return new ResponseTipVO(1, "删除成功!");
    }
}
