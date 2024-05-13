package kpi.diploma.communication.controller;

import jakarta.servlet.http.HttpServletRequest;
import kpi.diploma.communication.dto.CommentDTO;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.User;
import kpi.diploma.communication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private AIService aiService;

    @Value("${aiID}")
    private String aiID;

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
            model.addAttribute("userId", userDetails.getUsername());
        }
        return "posts";
    }

    @GetMapping("/{id}")
    public String getPost(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id") Long id, @RequestParam(name = "errorMessage", required = false) String errorMessage,  Model model){
        PostDTO post = postService.getPostById(id);
        boolean checkAccess = postService.checkUserHasAccessForPost(userDetails.getUsername(), id);
        if(checkAccess) {
            model.addAttribute("post", post);
            List<CommentDTO> commentDTOS = commentService.getCommentsForPost(post.id);
            model.addAttribute("comments", commentDTOS);

            List<PostDTO> savedPosts = postService.getListSaved(userDetails.getUsername());
            model.addAttribute("savedPosts", savedPosts);

            model.addAttribute("aiID", aiID);
            model.addAttribute("errorMessage", errorMessage);
            return "post";
        }else{
            return "error";
        }
    }
    @PreAuthorize("hasAnyAuthority('TEACHER','CURATOR', 'RESPONSIBLE')")
    @PostMapping("/removePost")
    public String removePost(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "postId") Long postId){
        postService.removePost(postId, userDetails.getUsername());
        return "redirect:/myPosts";

    }

    @GetMapping("/saved/{id}")
    public String savedPost(@PathVariable("id") Long id,  @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request){
        System.out.println("saved");
        postService.savedPost(userDetails.getUsername(),id);


        String referer = request.getHeader("referer");

        return "redirect:" + referer;

    }

    @PostMapping("/writeComment")
    public String writeComment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("comment") String comment, @RequestParam("postId") Long postId, RedirectAttributes redirectAttributes){
        System.out.println(1111111);
        boolean answer = aiService.saveComment(postId,comment,userDetails.getUsername());
        System.out.println(answer);
        if(!answer){
            redirectAttributes.addAttribute("errorMessage", "Ви вживаєте ненормативну лексику, тому ваш коментар не було додано!!!");
        }
        return "redirect:/posts/"+postId;
    }

    @PostMapping("/writeResponse")
    public String writeResponse(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("response") String response, @RequestParam("commentId") Long commentId, @RequestParam("postId") Long postId, RedirectAttributes redirectAttributes){
        boolean answer = aiService.saveResponse(commentId,response,userDetails.getUsername());
        System.out.println(answer);
        if(!answer){
            redirectAttributes.addAttribute("errorMessage", "Ви вживаєте ненормативну лексику, тому ваш коментар не було додано!!!");
        }
        return "redirect:/posts/"+postId;
    }





    @PreAuthorize("hasAnyAuthority('TEACHER', 'CURATOR', 'RESPONSIBLE')")
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
    @GetMapping("/{postId}/viewed/{userId}")
    @ResponseBody
    public void markMessageAsViewed(@PathVariable("postId") Long postId , @PathVariable(name="userId") String userId) {
        postService.viewPost(postId, userId);
    }
}
