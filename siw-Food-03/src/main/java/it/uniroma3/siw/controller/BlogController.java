package it.uniroma3.siw.controller;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Post;
import it.uniroma3.siw.service.PostService;

@Controller
public class BlogController {
	
	@Autowired
    private PostService postService;

    /*@GetMapping("/blog")
    public String blogAdmin(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "/blog";
    }*/
	
	@GetMapping("/blog")
    public String blogAdmin(Model model) {
        List<Post> posts = postService.getAllPosts().stream().map(post -> {
            post.setFormattedDate(post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
            return post;
        }).collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "/blog";
    }

    @GetMapping("/admin/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "admin/newPost";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post) {
        postService.savePost(post);
        return "redirect:/blog";
    }

}
