package com.lhy.blog.web.admin;

import com.lhy.blog.po.User;
import com.lhy.blog.service.UserService;
import com.lhy.blog.vo.ResponseTipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseTipVO login(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session){
        User user = userService.checkUser(username, password);
        if(user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return new ResponseTipVO(1, "登陆成功!");
        } else {
            return new ResponseTipVO(0, "用户名或密码错误!");
        }
    }

    @GetMapping("/login/info")
    public String info(HttpSession session){
        User user = (User) session.getAttribute("user");
        return "{\"nickname\":\"" + user.getNickname() + "\", \"avatar\":\"" + user.getAvatar() + "\"}";
    }

    @GetMapping("/logout")
    public ResponseTipVO logout(HttpSession session){
        session.removeAttribute("user");
        return new ResponseTipVO(1, "成功退出登陆!");
    }
}
