package com.quiroz.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiroz/posts")
@CrossOrigin(origins = {"http://localhost:5173"})
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        post.setTimestamp(java.time.LocalDateTime.now());
        return postRepository.save(post);
    }

    @PostMapping("/bulk")
    public List<Post> createBulkPosts(@RequestBody List<Post> posts) {
        posts.forEach(post -> post.setTimestamp(java.time.LocalDateTime.now()));
        return postRepository.saveAll(posts);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        post.setCaption(postDetails.getCaption());
        post.setImageUrl(postDetails.getImageUrl());
        post.setTimestamp(java.time.LocalDateTime.now());
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        postRepository.delete(post);
        return "Post deleted successfully!";
    }
}