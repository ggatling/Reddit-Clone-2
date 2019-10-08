package com.project.notreddit.Controllers;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.notreddit.Config.IAuthentication;
import com.project.notreddit.Config.JwtResponse;
import com.project.notreddit.Models.Comment;
import com.project.notreddit.Models.Post;
import com.project.notreddit.Models.User;
import com.project.notreddit.Services.UserService;
import org.hibernate.hql.internal.ast.tree.ResolvableNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private IAuthentication authImpl;


    @GetMapping("/hello")
    public String helloWorld(){ return "Hello World!"; }

    @GetMapping("/username")
    @ResponseBody
    public String getCurrentUsername(){
        Authentication auth = authImpl.getAuthentication();
        return auth.getName();

    }

    @GetMapping("/user/list")
    public Iterable<User> listUsers(){ return userService.listUsers(); }

    @GetMapping("/user/{username}/posts")
    public Iterable<Post> listUserPosts(@PathVariable String username){ return userService.listUserPosts(username); }

    @GetMapping("/user/{username}/comments")
    public Iterable<Comment> listUserComments(@PathVariable String username){ return userService.listUserComments(username); }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        return ResponseEntity.ok(new JwtResponse(userService.createUser(newUser)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
    }

}
