package com.lhy.blog.service;

import com.lhy.blog.dao.MusicRepository;
import com.lhy.blog.po.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public List<Music> musicListAll() {
        return musicRepository.findAll();
    }
}
