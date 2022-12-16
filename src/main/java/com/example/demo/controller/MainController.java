package com.example.demo.controller;


import com.example.demo.models.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller //
public class MainController {
    @Autowired
private PostRepository postRepository;

    @GetMapping("/")
    public String greeting( Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Main page");
        return "home";
    }
    @GetMapping("/AllArticles")
    public String world( Model model) {
        List<Post> post = postRepository.findAll();
        model.addAttribute("post", post);
        model.addAttribute("title", "All articles");
        return "allArticles";
    }
}
