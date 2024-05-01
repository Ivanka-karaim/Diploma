package kpi.diploma.communication.controller;

import jakarta.servlet.http.HttpServletRequest;
import kpi.diploma.communication.dto.CommentDTO;
import kpi.diploma.communication.dto.GroupDTO;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.User;
import kpi.diploma.communication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private GroupService groupService;

    @GetMapping("/")
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
    public String savedPost(@PathVariable("id") Long id,  @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("saved");
        postService.savedPost(userDetails.getUsername(),id);
        return "redirect:/posts/";
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

    @PreAuthorize("hasAnyRole(T(kpi.diploma.communication.model.Role).TEACHER, T(kpi.diploma.communication.model.Role).RESPONSIBLE)")
    @PostMapping("/addPostForStudents")
    public String addPostForStudents(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("groups") List<String> groups, @RequestParam("post") PostDTO postDTO){
        postService.addPostForStudents(userDetails.getUsername(), groups, postDTO);
        return "redirect:/myPosts";
    }

    @PreAuthorize("hasAnyRole( T(kpi.diploma.communication.model.Role).RESPONSIBLE)")
    @PostMapping("/addPostForUsers")
    public String addPostForUsers(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("users") List<String> users, @RequestParam("post") PostDTO postDTO){
        postService.addPostForUsers(userDetails.getUsername(), users, postDTO);
        return "redirect:/myPosts";
    }
    // TODO перенести в інший контроллер
    @PreAuthorize("hasAnyRole(T(kpi.diploma.communication.model.Role).TEACHER, T(kpi.diploma.communication.model.Role).RESPONSIBLE)")
    @GetMapping("/myPosts")
    public String getMyPosts(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<PostDTO> postDTOS = postService.getPostsForAuthor(userDetails.getUsername());
        model.addAttribute("posts", postDTOS);
        return "myPosts";

    }
    // TODO перенести в інший контроллер
    @PreAuthorize("hasAnyRole( T(kpi.diploma.communication.model.Role).TEACHER)")
    @GetMapping("/getGroupsForTeacher")
    public String getMyGroups(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<GroupDTO> groupDTOS = groupService.getGroupsForTeacher(userDetails.getUsername());
        model.addAttribute("groups", groupDTOS);

        return "groups";
    }
    // TODO перенести в інший контроллер
    @PreAuthorize("hasAnyRole( T(kpi.diploma.communication.model.Role).TEACHER, T(kpi.diploma.communication.model.Role).RESPONSIBLE)")
    @GetMapping("/getStudentsForGroup/{groupTitle}")
    public String getStudentsForGroup( Model model,@PathVariable("groupTitle") String groupTitle){
        List<UserDTO> users = userService.getStudentForGroup(groupTitle);
        model.addAttribute("users", users);

        return "students";
    }





}
