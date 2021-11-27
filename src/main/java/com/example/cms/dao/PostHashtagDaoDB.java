package com.example.cms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostHashtagDaoDB implements PostHashtagDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public void addPostHashtag(int postId, int hashtagId) {
        String INSERT_POSTHASHTAG = "INSERT INTO postHashtag(postId, hashtagId) VALUES(?,?)";
        this.jdbc.update(INSERT_POSTHASHTAG,
                postId,
                hashtagId);
    }
}
