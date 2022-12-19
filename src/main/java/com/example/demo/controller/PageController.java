package com.example.demo.controller;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.models.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class PageController {
    private final PostRepository postRepository;

    @Autowired
    public PageController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/page")
    public String page() {
        return "page";
    }

    @GetMapping("/page/blogAdd")
    public String pageAdd() {
        return "blogAdd";
    }

    @PostMapping("/page/blogAdd")
    public String blogPostAdd(@RequestParam String title, @RequestParam String email,
                              @RequestParam String fullText){
        Post post = new Post(title, email, fullText);
        postRepository.save(post);
        return "redirect:/AllArticles";
    }
    @GetMapping("/page/blogAdd/{id}")
    public String blogDetails(@PathVariable(value = "id") Integer id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/AllArticles";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("postDetails", result);
        return "blogDetails";
    }

    @GetMapping("/page/blogAdd/{id}/edit")
    public String blogEdit(@PathVariable( value = "id") Integer id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/AllArticles";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("postEdit", result);
        return "blogEdit";
    }

    @PostMapping("/page/blogAdd/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") Integer id, @RequestParam String title,
                                 @RequestParam String email, @RequestParam String fullText){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setEmail(email);
        post.setFullText(fullText);
        postRepository.save(post);
        return "redirect:/AllArticles";
    }

    @DeleteMapping("/page/blogAdd/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") Integer id){
        Post post = postRepository.findById(id).orElseThrow(NotFoundException::new);
        postRepository.delete(post);
        return "redirect:/AllArticles";
    }
}
