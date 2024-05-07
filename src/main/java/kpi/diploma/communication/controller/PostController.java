package kpi.diploma.communication.controller;

import jakarta.servlet.http.HttpServletRequest;
import kpi.diploma.communication.dto.CommentDTO;
import kpi.diploma.communication.dto.GroupDTO;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.Role;
import kpi.diploma.communication.model.User;
import kpi.diploma.communication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private GroupService groupService;

    @GetMapping("")
    public String getPosts(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam(value = "page", required = false) Long page){
        User user = userService.getUserById(userDetails.getUsername());
        if(user!=null){
            List<PostDTO> posts = postService.getPostsForUser(user, Math.toIntExact((page == null) ? 1 : page));
            model.addAttribute("posts", posts);
            System.out.println(posts);
            model.addAttribute("countPage", (int) Math.ceil((float) posts.size()/ postService.COUNT_POSTS));
            System.out.println( (int) Math.ceil((float) posts.size()/ postService.COUNT_POSTS));


            List<PostDTO> savedPosts = postService.getListSaved(userDetails.getUsername());

            System.out.println(savedPosts);
            model.addAttribute("savedPosts", savedPosts);
            model.addAttribute("request", request);
        }
        return "posts";
    }

    @GetMapping("/{id}")
    public String getPost(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id") Long id,  Model model){
        PostDTO post = postService.getPostById(id);
        model.addAttribute("post",post);
        List<CommentDTO> commentDTOS = commentService.getCommentsForPost(post.id);
        model.addAttribute("comments", commentDTOS);

        List<PostDTO> savedPosts = postService.getListSaved(userDetails.getUsername());
        model.addAttribute("savedPosts", savedPosts);
        return "post";
    }

    @GetMapping("/saved/{id}")
    public String savedPost(@PathVariable("id") Long id,  @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request){
        System.out.println("saved");
        postService.savedPost(userDetails.getUsername(),id);


        String referer = request.getHeader("referer");

        return "redirect:" + referer;

    }

    @PostMapping("/writeComment")
    public String writeComment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("comment") String comment, @RequestParam("postId") Long postId){
        System.out.println(1111111);
        commentService.saveComment(userDetails.getUsername(), comment, postId);
        return "redirect:/posts/"+postId;
    }

    @PostMapping("/writeResponse")
    public String writeResponse(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("response") String response, @RequestParam("commentId") Long commentId, @RequestParam("postId") Long postId){
        System.out.println(1111111);
        boolean saveComment = responseService.saveResponse(userDetails.getUsername(), response, commentId);
        return "redirect:/posts/"+postId;
    }





    @PreAuthorize("hasAnyRole(T(kpi.diploma.communication.model.Role).TEACHER,T(kpi.diploma.communication.model.Role).CURATOR, T(kpi.diploma.communication.model.Role).RESPONSIBLE)")
    @PostMapping("/addPost")
    public String addPostForStudents(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("groups") List<String> groups, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam(name="isLeaderGroup", required = false) boolean isLeaderGroup, @RequestParam(name="isCurator", required = false) boolean isCurator){
        postService.addPostForStudents(userDetails.getUsername(), groups, title, description, isLeaderGroup, isCurator);
        return "redirect:/myPosts";
    }

    @PreAuthorize("hasAuthority( 'RESPONSIBLE')")
    @GetMapping("/addPostForUsers")
    public String addPostForUsersGet(Model model){
        List<UserDTO> curators = userService.getAllCurators();
        List<UserDTO> teachers = userService.getAllTeachers();

        model.addAttribute("curators", curators);
        model.addAttribute("teachers", teachers);
        return "addPostForUsers";
    }

    @PreAuthorize("hasAuthority( 'RESPONSIBLE')")
    @PostMapping("/addPostForUsers")
    public String addPostForUsers(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("curators") List<String> curators,@RequestParam("teachers") List<String> teachers,@RequestParam("title") String title, @RequestParam("description") String description){
        postService.addPostForUsers(userDetails.getUsername(), Stream.concat(curators.stream(), teachers.stream()).distinct().collect(Collectors.toList()), title, description);
        return "redirect:/myPosts";
    }
}
