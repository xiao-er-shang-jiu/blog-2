package com.lhy.blog.web;

import com.lhy.blog.po.Music;
import com.lhy.blog.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/music")
    public List<Music> music() {
        return musicService.musicListAll();
    }
}
