package zeljko.ngspringblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}