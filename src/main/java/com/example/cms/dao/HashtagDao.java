package com.example.cms.dao;

import java.util.List;

public interface HashtagDao {
    List<Hashtag> getAllHashtags();
    List<Hashtag> getAllHashtagsByPost(BlogPost post);
    Hashtag addHashtag(Hashtag tag);
}
