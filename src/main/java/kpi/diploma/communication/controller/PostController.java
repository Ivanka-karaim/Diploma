package kpi.diploma.communication.controller;

import jakarta.servlet.http.HttpServletRequest;
import kpi.diploma.communication.dto.CommentDTO;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.model.User;
import kpi.diploma.communication.service.CommentService;
import kpi.diploma.communication.service.PostService;
import kpi.diploma.communication.service.ResponseService;
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
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ResponseService responseService;
    @GetMapping("/")
    public String getPosts(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam(value = "page", required = false) Long page){
        User user = userService.getUserById(userDetails.getUsername());
        if(user!=null){
            List<PostDTO> posts = postService.getPostsForUser(user, Math.toIntExact((page == null) ? 1 : page));
            model.addAttribute("posts", posts);
            System.out.println(posts);
            model.addAttribute("countPage", (int) Math.ceil((float) posts.size()/ postService.COUNT_POSTS));
            System.out.println( (int) Math.ceil((float) posts.size()/ postService.COUNT_POSTS));
            model.addAttribute("request", request);
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

    @PostMapping("/writeComment")
    public String writeComment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("comment") String comment, @RequestParam("postId") Long postId){
        System.out.println(1111111);
        boolean saveComment = commentService.saveComment(userDetails.getUsername(), comment, postId);
        return "redirect:/posts/"+postId;
    }

    @PostMapping("/writeResponse")
    public String writeResponse(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("response") String response, @RequestParam("commentId") Long commentId, @RequestParam("postId") Long postId){
        System.out.println(1111111);
        boolean saveComment = responseService.saveResponse(userDetails.getUsername(), response, commentId);
        return "redirect:/posts/"+postId;
    }




}
