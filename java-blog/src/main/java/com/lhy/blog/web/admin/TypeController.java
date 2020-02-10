package com.lhy.blog.web.admin;

import com.lhy.blog.po.Type;
import com.lhy.blog.service.TypeService;
import com.lhy.blog.vo.ResponseTipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public Page<Type> types(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable){
        Page<Type> page = typeService.listType(pageable);
        for(Type type : page.getContent()) {
            type.setCount(type.getBlogs().size());
            type.setBlogs(null);
        }
        return page;
    }

    @GetMapping("/types/getAll")
    public List<Type> allTypes(){
        List<Type> types = typeService.listType();
        for(Type type : types)
            type.setBlogs(null);
        return types;
    }

    @PostMapping("/types/addOrUpdate")
    public ResponseTipVO post(@Valid Type type, BindingResult result){
        if(result.hasErrors()){
            return new ResponseTipVO(0, result.getAllErrors().get(0).getDefaultMessage());
        }
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            return new ResponseTipVO(0, "分类名重复!");
        }
        Type t = typeService.saveType(type);
        if(t == null){
            return new ResponseTipVO(0, "操作失败!");
        }else {
            return new ResponseTipVO(1, "操作成功!");
        }
    }

    @GetMapping("/types/{id}/delete")
    public ResponseTipVO delete(@PathVariable Long id){
        typeService.deleteType(id);
        return new ResponseTipVO(1, "删除成功!");
    }
}
