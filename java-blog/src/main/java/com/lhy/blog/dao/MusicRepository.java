package com.lhy.blog.dao;

import com.lhy.blog.po.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
