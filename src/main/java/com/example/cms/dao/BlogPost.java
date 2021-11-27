package com.example.cms.dao;

import java.util.List;

public class BlogPost {
    private int postId;
    private String postDate;
    private String postTitle;
    private String postBody;
    public List<Hashtag> getPostHashtags() {
        return postHashtags;
    }

    public void setPostHashtags(List<Hashtag> postHashtags) {
        this.postHashtags = postHashtags;
    }

    private List<Hashtag> postHashtags;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
