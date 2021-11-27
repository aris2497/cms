package com.example.cms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HashtagDaoDB implements HashtagDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Hashtag> getAllHashtags() {
        String SELECT_ALL_HASHTAGS = "SELECT * FROM blogPost";
        return this.jdbc.query("SELECT * FROM hashtag", new HashtagDaoDB.HashtagMapper());
    }

    @Override
    public List<Hashtag> getAllHashtagsByPost(BlogPost post) {
        String SELECT_HASHTAG_BY_POST = "SELECT h.*\n" +
                "FROM hashtag h\n" +
                "JOIN postHashtag ph\n" +
                "  ON h.hashtagId = ph.hashtagId\n" +
                "JOIN blogPost p\n" +
                "  ON p.postId = ph.postId\n" +
                "WHERE ph.postId = ?";
        return this.jdbc.query(SELECT_HASHTAG_BY_POST,
                new HashtagDaoDB.HashtagMapper(), new Object[]{post.getPostId()});
    }

    @Override
    public Hashtag addHashtag(Hashtag tag) {
        String INSERT_HASHTAG = "INSERT INTO hashtag(hashtagName) VALUES(?)";
        this.jdbc.update(INSERT_HASHTAG,
                tag.getHashtagName();
        int newId = (Integer)this.jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        tag.setHashtagId(newId);
        return tag;

    }

    public static final class HashtagMapper implements RowMapper<Hashtag> {
        public HashtagMapper() {
        }

        public Hashtag mapRow(ResultSet rs, int index) throws SQLException {
            Hashtag tag = new Hashtag();
            tag.setHashtagId(rs.getInt("hashtagId"));
            tag.setHashtagName(rs.getString("hashtagName"));

            return tag;
        }
    }
}
