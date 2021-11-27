package com.example.cms.dao;

import java.util.List;

public interface BlogPostDao {
    List<BlogPost> getAllPosts();
    List<BlogPost> getAllPostsByHashtag(Hashtag hashtag);


}
