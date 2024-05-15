package kpi.diploma.communication.controller;

import kpi.diploma.communication.dto.*;
import kpi.diploma.communication.model.*;
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
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String main(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return "redirect:/posts";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDTO userDTO = userService.getUserDTOById(userDetails.getUsername());
        model.addAttribute("user", userDTO);
        if (userDTO.getRoles().contains(Role.STUDENT)) {
            model.addAttribute("isStudent", true);
        } else if (userDTO.getRoles().contains(Role.TEACHER) || userDTO.getRoles().contains(Role.CURATOR)) {
            model.addAttribute("isTeacher", true);
        }
        if (userDTO.getRoles().contains(Role.RESPONSIBLE)) {
            model.addAttribute("isResponsible", true);
        }
        return "profile";


    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'RESPONSIBLE')")
    @GetMapping("/profile/teachers")
    public String teachers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDTO userDTO = userService.getUserDTOById(userDetails.getUsername());
        if(userDetails.getAuthorities().contains(Role.RESPONSIBLE)){
            List<UserDTO> curators = userService.getAllCurators();
            List<UserDTO> teachers = userService.getAllTeachers();
            model.addAttribute("curators", curators);
            model.addAttribute("teachers", teachers);
            model.addAttribute("isResponsible", true);
        }else {
            UserDTO curator = userService.getCuratorForUser(userDTO.getGroup());
            model.addAttribute("curator", curator);
            List<UserDTO> teachers = userService.getTeachersForUser(userDTO.getGroup());
            model.addAttribute("teachers", teachers);
            model.addAttribute("isStudent", true);
        }
        List<UserDTO> teachersWhichHasMessage = userService.userListWhichHasNotViewedMessages(userDetails.getUsername());
        model.addAttribute("user", userDTO);


        model.addAttribute("teachersMessages", teachersWhichHasMessage);
        return "teachers";

    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'CURATOR')")
    @GetMapping("/getResponsibles")
    public String getResponsibles(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<UserDTO> responsibles = userService.getResponsibles();
        model.addAttribute("users", responsibles);
        model.addAttribute("isResponsible", true);
        List<UserDTO> usersWhichHasMessage = userService.userListWhichHasNotViewedMessages(userDetails.getUsername());
        model.addAttribute("userMessages", usersWhichHasMessage);

        return "students";

    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'RESPONSIBLE', 'CURATOR')")
    @GetMapping("/myPosts")
    public String getMyPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<PostDTO> postDTOS = postService.getPostsForAuthor(userDetails.getUsername());
        model.addAttribute("posts", postDTOS);
        List<PostDTO> savedPosts = postService.getListSaved(userDetails.getUsername());
        model.addAttribute("savedPosts", savedPosts);
        model.addAttribute("isMyPosts", true);
        System.out.println(postDTOS);
        return "saved";

    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'RESPONSIBLE', 'CURATOR')")
    @GetMapping("/getUserForPost/{id}")
    public String getUserForPost(@PathVariable("id") Long id, Model model) {
        List<PostUser> users = userService.getUsersByPost(id);
        model.addAttribute("postUsers", users);
        return "users";

    }

    @PreAuthorize("hasAnyAuthority( 'TEACHER', 'CURATOR')")
    @GetMapping("/profile/groups")
    public String getMyGroups(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        GroupDTO groupDTO = groupService.getMyGroup(userDetails.getUsername());
        model.addAttribute("myGroup", groupDTO);
        List<GroupDTO> groupDTOS = groupService.getGroupsForTeacher(userDetails.getUsername(), false);
        model.addAttribute("groups", groupDTOS);
        return "groups";
    }



    @PreAuthorize("hasAnyAuthority('TEACHER','CURATOR')")
    @GetMapping("/getStudentsForGroup")
    public String getStudentsForGroup(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam("groupTitle") String groupTitle) {
        if (userService.checkAccessTeacherForGroup(userDetails.getUsername(), groupTitle)) {
            List<UserDTO> users = userService.getStudentForGroup(groupTitle);
            model.addAttribute("users", users);
            List<UserDTO> studentsWhichHasMessage = userService.userListWhichHasNotViewedMessages(userDetails.getUsername());
            model.addAttribute("userMessages", studentsWhichHasMessage);
            model.addAttribute("groupTitle", groupTitle);
            model.addAttribute("isResponsible", false);
            return "students";
        } else {
            return "error";
        }

    }

    @GetMapping("/addPost")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'CURATOR', 'RESPONSIBLE')")
    public String addPost(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam(name = "specialities", required = false) List<String> specialitiesHTML,
                          @RequestParam(name = "courses", required = false) List<Integer> coursesHTML) {
        System.out.println("CoursesHTML");
        System.out.println(coursesHTML);
        User user = userService.getUserById(userDetails.getUsername());
        List<GroupDTO> groups = new ArrayList<>();
        if (user.getRoles().contains(Role.RESPONSIBLE)) {
            groups = groupService.getAllGroups();
        } else if (user.getRoles().contains(Role.TEACHER) || user.getRoles().contains(Role.CURATOR)) {
            groups = groupService.getGroupsForTeacher(userDetails.getUsername(), true);

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

        if (specialitiesHTML != null && coursesHTML != null) {

            List<GroupDTO> filteredGroups = groups.stream()
                    .filter(group -> specialitiesHTML.contains(group.getSpeciality()) && coursesHTML.contains(group.getCourse()))
                    .toList();
            model.addAttribute("groups", filteredGroups);
            model.addAttribute("specialityCheck", specialitiesHTML);
            model.addAttribute("courseCheck", coursesHTML);
        } else {
            model.addAttribute("groups", groups);
        }

        System.out.println(groups);

        return "addPost";
    }


    @GetMapping("/profile/saved")
    public String saved(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<PostDTO> savedPosts = postService.getListSaved(userDetails.getUsername());
        model.addAttribute("posts", savedPosts);
        model.addAttribute("savedPosts", savedPosts);
        model.addAttribute("isSaved", true);
        return "saved";
    }


    @GetMapping("/openChat")
    public String openChat(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("userId") String userId, Model model) {
//        List<ChatMessage> chatMessages = chatService.findChatMessages(userDetails.getUsername(), userId);
//        model.addAttribute("messages", chatMessages);
        model.addAttribute("sender", userDetails.getUsername());
        model.addAttribute("receiver", userService.getUserById(userId));
        return "chat";

    }

    @GetMapping("/messages")
    public String messages(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<PersonalMessage> personalMessages = chatService.getMessages(userDetails.getUsername());
        System.out.println(personalMessages);
        model.addAttribute("messages", personalMessages);
        return "messages";
    }

}
