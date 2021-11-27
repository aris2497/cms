package com.example.cms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BlogPostDaoDB implements BlogPostDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<BlogPost> getAllPosts() {
        String SELECT_ALL_POSTS = "SELECT * FROM blogPost";
        return this.jdbc.query("SELECT * FROM blogPost", new BlogPostDaoDB.BlogPostMapper());
    }

    @Override
    public List<BlogPost> getAllPostsByHashtag(Hashtag hashtag) {
        String SELECT_Post_BY_HASHTAG = "SELECT p.*\n" +
                "FROM blogPost p\n" +
                "JOIN postHashtag ph\n" +
                "  ON p.postId = ph.postId\n" +
                "JOIN hashtag h\n" +
                "  ON h.hashtagId = ph.hashtagId\n" +
                "WHERE ph.hashtagId = ?";
        return this.jdbc.query(SELECT_Post_BY_HASHTAG,
                new BlogPostDaoDB.BlogPostMapper(), new Object[]{hashtag.getHashtagId()});
    }

    public static final class BlogPostMapper implements RowMapper<BlogPost> {
        public BlogPostMapper() {
        }

        public BlogPost mapRow(ResultSet rs, int index) throws SQLException {
            BlogPost post = new BlogPost();
            post.setPostId(rs.getInt("postId"));
            post.setPostDate(rs.getString("postDate"));
            post.setPostTitle(rs.getString("postTitle"));
            post.setPostBody(rs.getString("postBody"));
            return post;
        }
    }
}
