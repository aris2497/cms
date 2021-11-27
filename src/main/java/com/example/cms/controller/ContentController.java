package com.example.cms.controller;

import com.example.cms.dao.BlogPost;
import com.example.cms.dao.BlogPostDaoDB;
import com.example.cms.dao.Hashtag;
import com.example.cms.dao.HashtagDaoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ContentController {

    @Autowired
    BlogPostDaoDB blogPostDaoDB;

    @Autowired
    HashtagDaoDB hashtagDaoDB;

    @RequestMapping(value="/content", method=RequestMethod.GET)
    public String displayContentPage(Model model)
    {
       List<BlogPost> posts = blogPostDaoDB.getAllPosts();
       List<String> hashtags = new ArrayList<>();
        for (BlogPost p :
             posts) {
            List<Hashtag> postHashtags = hashtagDaoDB.getAllHashtagsByPost(p);
            p.setPostHashtags(postHashtags);
        }
        model.addAttribute("posts", posts);
        return "content";
    }
}
