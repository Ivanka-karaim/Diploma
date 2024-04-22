package kpi.diploma.communication.controller;

import kpi.diploma.communication.dto.CommentDTO;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.model.User;
import kpi.diploma.communication.service.CommentService;
import kpi.diploma.communication.service.PostService;
import kpi.diploma.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {


    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String getPosts(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam(value = "page", required = false) Long page){
        User user = userService.getUserById(userDetails.getUsername());
        if(user!=null){
            List<PostDTO> posts = postService.getPostsForUser(user, Math.toIntExact((page == null) ? 1 : page));
            model.addAttribute("posts", posts);
            model.addAttribute("countPage", (int) Math.ceil((float) posts.size()/ postService.COUNT_POSTS));
        }
        return "posts";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails, Model model){
        PostDTO post = postService.getPostById(id);
        model.addAttribute("post",post);
        List<CommentDTO> commentDTOS = commentService.getCommentsForPost(post.id);
        model.addAttribute("comments", commentDTOS);
        return "post";
    }


}
