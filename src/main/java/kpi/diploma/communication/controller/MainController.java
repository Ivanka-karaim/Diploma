package kpi.diploma.communication.controller;

import kpi.diploma.communication.dto.ChatMessage;
import kpi.diploma.communication.dto.GroupDTO;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.Role;
import kpi.diploma.communication.model.User;
import kpi.diploma.communication.service.ChatService;
import kpi.diploma.communication.service.GroupService;
import kpi.diploma.communication.service.PostService;
import kpi.diploma.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private PostService postService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal UserDetails userDetails, Model model){

        return "redirect:/posts";

    }


    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model){
        UserDTO userDTO = userService.getUserDTOById(userDetails.getUsername());
        model.addAttribute("user", userDTO);
        if(userDTO.getRoles().contains(Role.STUDENT)){
            model.addAttribute("isStudent", true);
        }else{
            model.addAttribute("isStudent", false);
        }
        return "profile";


    }

    @PreAuthorize("hasRole(T(kpi.diploma.communication.model.Role).STUDENT)")
    @GetMapping("/profile/teachers")
    public String teachers(@AuthenticationPrincipal UserDetails userDetails, Model model){
        UserDTO userDTO = userService.getUserDTOById(userDetails.getUsername());
        UserDTO curator = userService.getCuratorForUser(userDTO.getGroup());
        List<UserDTO> teachers = userService.getTeachersForUser(userDTO.getGroup());

        model.addAttribute("user", userDTO);
        model.addAttribute("curator", curator);
        model.addAttribute("teachers", teachers);
        return "teachers";

    }

    @PreAuthorize("hasAnyRole(T(kpi.diploma.communication.model.Role).TEACHER, T(kpi.diploma.communication.model.Role).RESPONSIBLE, T(kpi.diploma.communication.model.Role).CURATOR)")
    @GetMapping("/myPosts")
    public String getMyPosts(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<PostDTO> postDTOS = postService.getPostsForAuthor(userDetails.getUsername());
        model.addAttribute("savedPosts", postDTOS);
        model.addAttribute("isMyPosts", true);
        System.out.println(postDTOS);
        return "saved";

    }
    @PreAuthorize("hasAnyRole( T(kpi.diploma.communication.model.Role).TEACHER,T(kpi.diploma.communication.model.Role).CURATOR)")
    @GetMapping("/profile/groups")
    public String getMyGroups(@AuthenticationPrincipal UserDetails userDetails, Model model){
        GroupDTO groupDTO = groupService.getMyGroup(userDetails.getUsername());

        model.addAttribute("myGroup", groupDTO);


        List<GroupDTO> groupDTOS = groupService.getGroupsForTeacher(userDetails.getUsername());
        model.addAttribute("groups", groupDTOS);

        return "groups";
    }
    @PreAuthorize("hasAnyRole( T(kpi.diploma.communication.model.Role).TEACHER, T(kpi.diploma.communication.model.Role).CURATOR, T(kpi.diploma.communication.model.Role).RESPONSIBLE)")
    @GetMapping("/getStudentsForGroup")
    public String getStudentsForGroup( Model model,@RequestParam("groupTitle") String groupTitle){
        List<UserDTO> users = userService.getStudentForGroup(groupTitle);
        model.addAttribute("users", users);
        model.addAttribute("groupTitle", groupTitle);
        return "students";
    }

    @GetMapping("/addPost")
    @PreAuthorize("hasAnyRole(T(kpi.diploma.communication.model.Role).TEACHER,  T(kpi.diploma.communication.model.Role).RESPONSIBLE)")
    public String addPost(@AuthenticationPrincipal UserDetails userDetails, Model model,@RequestParam(name = "specialities", required = false) List<String> specialitiesHTML,
                          @RequestParam(name = "courses", required = false) List<Integer> coursesHTML){
        System.out.println(coursesHTML);
        User user = userService.getUserById(userDetails.getUsername());
        List<GroupDTO> groups;
        if(user.getRoles().contains(Role.RESPONSIBLE)){
            groups = groupService.getAllGroups();
        }else {
            groups = groupService.getGroupsForTeacher(userDetails.getUsername());

        }
        List<Integer> courses = groups.stream()
                .map(GroupDTO::getCourse)
                .distinct()
                .sorted(Integer::compareTo)
                .toList();

        model.addAttribute("courses", courses);
        List<String> specialities = groups.stream()
                .map(GroupDTO::getSpeciality)
                .distinct()
                .sorted(String::compareTo)
                .toList();
        model.addAttribute("specialities", specialities);

        if(specialitiesHTML != null && coursesHTML != null){

            List<GroupDTO> filteredGroups = groups.stream()
                    .filter(group -> specialitiesHTML.contains(group.getSpeciality()) && coursesHTML.contains(group.getCourse()))
                    .toList();
            model.addAttribute("groups", filteredGroups);
            model.addAttribute("specialityCheck", specialitiesHTML);
            model.addAttribute("courseCheck", coursesHTML);
        }else {
            model.addAttribute("groups", groups);
        }




        System.out.println(groups);

        return "addPost";
    }

//    @PostMapping("/submitFilters")
//    public String submitFilters(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "specialities", required = false) List<String> specialities,
//                                @RequestParam(name = "courses", required = false) List<String> courses) {
//
//        User user = userService.getUserById(userDetails.getUsername());
//        List<GroupDTO> groups;
//        if(user.getRoles().contains(Role.RESPONSIBLE)){
//            groups = groupService.getAllGroups();
//        }else {
//            groups = groupService.getGroupsForTeacher(userDetails.getUsername());
//
//        }
//
//        List<GroupDTO> filteredGroups = groups.stream()
//                .filter(group -> specialities.contains(group.getSpeciality()) && courses.contains(String.valueOf(group.getCourse())))
//                .toList();
//
//
//        System.out.println("Specialities: " + specialities);
//        System.out.println("Courses: " + courses);
//
//        return "addPost";
//    }


    @GetMapping("/profile/saved")
    public String saved(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<PostDTO> savedPosts = postService.getListSaved(userDetails.getUsername());
        model.addAttribute("savedPosts", savedPosts);
        model.addAttribute("isSaved", true);
        return "saved";
    }




    @GetMapping("/openChat")
    public String openChat(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("userId") String userId, Model model){
//        List<ChatMessage> chatMessages = chatService.findChatMessages(userDetails.getUsername(), userId);
//        model.addAttribute("messages", chatMessages);
        model.addAttribute("sender", userDetails.getUsername());
        model.addAttribute("receiver", userService.getUserById(userId));
        return "chat";

    }

}
