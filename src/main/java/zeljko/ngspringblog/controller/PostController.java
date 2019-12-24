package zeljko.ngspringblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zeljko.ngspringblog.model.PostDto;
import zeljko.ngspringblog.service.PostService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * PostController
 */

@RestController
@RequestMapping("/api/post")
 public class PostController {

    @GetMapping ("/hello")
    public String hello (){
        return "welcome to post";
    }

    
    // dodajem 3 endpointa za unos , listsvih i listby id postova - autovire post service + postdto 

    @Autowired
    private PostService postService;

    @PostMapping(value="/addpost")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return new ResponseEntity<>(HttpStatus.OK);  
    
        
    }

    @GetMapping(value="/getposts")
    public ResponseEntity<List<PostDto>> getAllPost () {
        return new ResponseEntity<>( postService.getAllPost(), HttpStatus.OK );        
    }

    @GetMapping(value="/getpost/{id}")
    public ResponseEntity<PostDto> getPost (@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPost(id) , HttpStatus.OK);
    }
    
    
}