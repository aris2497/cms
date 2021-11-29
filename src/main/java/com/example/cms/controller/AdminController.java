package com.example.cms.controller;

import com.example.cms.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @PostMapping("addBlogPost")
    public String addBlogPost(HttpServletRequest request){
        System.out.println("dsd");
        ArrayList<Hashtag> newPostHashtags = new ArrayList<>();

        int lastId = blogPostDaoDB.getAllPosts().size();
        String title = request.getParameter("title");
        String postBody = request.getParameter("postBody");
        String hashtags = request.getParameter("postHashtag");

        BlogPost newPost = new BlogPost();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        newPost.setPostDate(formatter.format(date));
        newPost.setPostTitle(title);
        newPost.setPostBody(postBody);
        newPost = blogPostDaoDB.addNewPost(newPost);
        newPost.setPostDate(formatter.format(date));
        System.out.println(newPost.toString());

        List<Hashtag> allHashtags = hashtagDaoDB.getAllHashtags();
        List<String> allHashtagsNames = new ArrayList<>();

        for (Hashtag tag: allHashtags
             ) {
            allHashtagsNames.add(tag.getHashtagName());
        }

        int lastHashtagId = allHashtags.size();
        List<String> postHashtagsStr = seperateHashtags(hashtags);
        System.out.println(postHashtagsStr);

        //creating new hashtag id doesn't exist already
        for (String tag: postHashtagsStr
             ) {
            Hashtag newHashtag = new Hashtag();
//            if(allHashtagsNames.contains(tag)){
                 //creating new Hashtag if it doesn't exist already
                newHashtag.setHashtagName(tag);
                newHashtag = hashtagDaoDB.addHashtag(newHashtag); //saving new hashtag to DB
                postHashtagDaoDB.addPostHashtag(newPost.getPostId(), newHashtag.getHashtagId()); //saving into the bridge table
                newPostHashtags.add(newHashtag);

         //}
        /* else {
                newHashtag = hashtagDaoDB.getAllHashtagsByName(tag).get(0);
                System.out.println(newHashtag.getHashtagId());
                System.out.println(newPost.getPostId());
                postHashtagDaoDB.addPostHashtag(newPost.getPostId(), newHashtag.getHashtagId()); //saving into the bridge table
                System.out.println(newHashtag.getHashtagId());
                System.out.println(newPost.getPostId());
                newPostHashtags.add(newHashtag);
            }*/
        }
        newPost.setPostHashtags(newPostHashtags);

        return "redirect:/content";

    }

    private List<String> seperateHashtags(String hashtags) {

        String[] postHashtags = new String[100];
        postHashtags = hashtags.split("#");
        ArrayList<String> postHashtagsStr = new ArrayList<>(Arrays.asList(postHashtags));
        return postHashtagsStr;
    }

}
