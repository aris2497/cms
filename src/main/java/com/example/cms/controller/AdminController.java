package com.example.cms.controller;

import com.example.cms.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    BlogPostDaoDB blogPostDaoDB;

    @Autowired
    HashtagDaoDB hashtagDaoDB;

    @Autowired
    PostHashtagDaoDB postHashtagDaoDB;

    @RequestMapping(value="/admin", method=RequestMethod.GET)
    public String displayAdminPage() {

        return "admin";
    }

    @PostMapping
    public String addBlogPost(HttpServletRequest request){
        ArrayList<Hashtag> newPostHashtags = new ArrayList<>();

        int lastId = blogPostDaoDB.getAllPosts().size();
        String title = request.getParameter("name");
        String postBody = request.getParameter("postBody");
        String hashtags = request.getParameter("hashtags");

        BlogPost newPost = new BlogPost();
        newPost.setPostId(lastId + 1);
        newPost.setPostTitle(title);
        newPost.setPostBody(postBody);

        List<Hashtag> allHashtags = hashtagDaoDB.getAllHashtags();
        List<String> allHashtagsNames = new ArrayList<>();

        for (Hashtag tag: allHashtags
             ) {
            allHashtagsNames.add(tag.getHashtagName());
        }

        int lastHashtagId = allHashtags.size();
        List<String> postHashtagsStr = seperateHashtags(hashtags);

        //creating new hashtag id doesn't exist already
        for (String tag: postHashtagsStr
             ) {
            Hashtag newHashtag = new Hashtag();
            if(!allHashtagsNames.contains(tag)){
                 //creating new Hashtag if it doesn't exist already
                newHashtag.setHashtagName(tag);
                newHashtag = hashtagDaoDB.addHashtag(newHashtag); //saving new hashtag to DB
                postHashtagDaoDB.addPostHashtag(newHashtag.getHashtagId(), newPost.getPostId()); //saving into the bridge table
                newPostHashtags.add(newHashtag); 

            } else {
                newHashtag = allHashtags.get(allHashtagsNames.indexOf(tag) + 1); //might need to be +1
                postHashtagDaoDB.addPostHashtag(newPost.getPostId(), allHashtagsNames.indexOf(tag) + 1); //saving into the bridge table
                newPostHashtags.add(newHashtag);
            }
        }
        newPost.setPostHashtags(newPostHashtags);

        return "redirect:/content";

    }

    private List<String> seperateHashtags(String hashtags) {
        String[] postHashtags;
        postHashtags = hashtags.split("#");
        ArrayList<String> postHashtagsStr = new ArrayList<>(Arrays.asList(postHashtags));
        return postHashtagsStr;
    }

}
