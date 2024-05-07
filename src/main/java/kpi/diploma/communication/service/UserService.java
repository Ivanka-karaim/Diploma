package kpi.diploma.communication.service;

import kpi.diploma.communication.data.UserGroupRepository;
import kpi.diploma.communication.data.UserRepository;
import kpi.diploma.communication.dto.GroupDTO;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.Role;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kpi.diploma.communication.dto.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;


    public List<UserDTO> getAllCurators(){
        List<User> users = userRepository.findByRolesContaining(Role.CURATOR);
        return parseUserListDTO(users);
    }

    public List<UserDTO> getAllTeachers(){
        List<User> users = userRepository.findByRolesContaining(Role.TEACHER);
        return parseUserListDTO(users);
    }

    private List<UserDTO> parseUserListDTO(List<User> users) {
        return users.stream()
                .map(this::getUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserDTO(User user){
        return UserDTO.builder().email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .group(user.getGroup()!=null?user.getGroup().getTitle():"")
                .speciality(user.getGroup()!=null?user.getGroup().getSpeciality():"")
                .course(user.getGroup()!=null?user.getGroup().getCourse():0)
                .roles(user.getRoles())
                .build();

    }
    public UserDTO getUserDTOById(String email){
        User user = getUserById(email);
        return getUserDTO(user);

    }

    public User getUserById(String email){
        return userRepository.findById(email).orElse(null);

    }

    public UserDTO getCuratorForUser(String groupTitle){
        return getUserDTO(userRepository.findByGroupTitleAndRolesContaining(groupTitle, Role.CURATOR));
    }

    public List<UserDTO> getStudentForGroup(String groupTitle){
        return parseUserListDTO(userRepository.findByGroupTitle(groupTitle));
    }

    public List<UserDTO> getTeachersForUser(String groupTitle){
        List<User> teachers = userGroupRepository.findUsersByGroupTitle(groupTitle, Role.TEACHER);
        List<UserDTO> teacherDTOs = new ArrayList<>();
        for(User teacher: teachers){
            teacherDTOs.add(getUserDTO(teacher));
        }
        return teacherDTOs;
    }

    public List<Error> validationData(User user, String passwordRepeat){
        List<Error> errors = new ArrayList<>();
        String regexAlpha = "^[A-Za-zА-ЩЬЮЯҐЄІЇа-щьюяґєії]+$";
        String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String regexPhoneNumber = "^[+]?[0-9]{1,3}-?[0-9]{3,14}$";
        if (!Pattern.compile(regexAlpha).matcher(user.getName()).find()){
            errors.add(new Error("name", "The name must consist only of letters of the Ukrainian and English alphabets"));
        }
        if (!Pattern.compile(regexAlpha).matcher(user.getSurname()).find()){
            errors.add(new Error("surname", "The name must consist only of letters of the Ukrainian and English alphabets"));
        }
        if (!Pattern.compile(regexEmail).matcher(user.getSurname()).find()){
            errors.add(new Error("email", "The email was entered incorrectly"));
        }
        errors.addAll(validationPassword(user.getPassword(), passwordRepeat));

        return errors;
    }

    private List<Error> validationPassword(String password1, String password2){
        List<Error> errors = new ArrayList<>();
        if (!password1.equals(password2)){
            errors.add(new Error("password2", "Passwords do not match"));
        }
        String lowercaseRegex = "(?=.*[a-z])";
        String uppercaseRegex = "(?=.*[A-Z])";
        String digitRegex = "(?=.*\\d)";

        if (!Pattern.compile(lowercaseRegex).matcher(password1).find()){
            errors.add(new Error("password", "Password must contain at least one small letter (a-z)"));
        }
        if (!Pattern.compile(uppercaseRegex).matcher(password1).find()){
            errors.add(new Error("password", "Password must contain at least one big letter (A-Z)"));
        }
        if (!Pattern.compile(digitRegex).matcher(password1).find()){
            errors.add(new Error("password", "Password must contain at least one number (0-9)"));
        }
        if (password1.length()<8){
            errors.add(new Error("password", "Password must contain at least 8 digits"));
        }
        return errors;
    }


}
