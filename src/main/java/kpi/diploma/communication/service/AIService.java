package kpi.diploma.communication.service;

import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class AIService {



    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseService responseService;



    public boolean saveComment(Long postId, String comment, String userEmail){
        PostDTO post = postService.getPostById(postId);
        String profanity = checkProfanity(comment);
        if(!profanity.trim().equals("Bad")){
            String ai = findAnswer(post.title+" "+post.description, comment);
            if(ai.trim().equals("False")){
                commentService.saveComment(userEmail, comment, postId);
            }else {
                Comment commentObject = commentService.saveComment(userEmail, comment, postId);
                User AI = userService.getAI();
                responseService.saveResponse(AI.getEmail(), ai.trim(), commentObject.getId());
            }
            return true;
        }else{
            return false;
        }

    }

    public boolean saveResponse(Long commentId, String text, String userEmail){
        String ai = checkProfanity(text);
        if(!ai.trim().equals("Bad")){
            System.out.println("Good");
            responseService.saveResponse(userEmail, text, commentId);
            return true;
        }else{
            return false;
        }

    }

    public String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "sk-proj-bw1DLgMyVRgMUmQeK6n1T3BlbkFJz3cXSCGoMgH3Go4wAzxY";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();
            System.out.println(connection.getResponseCode());


            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }

    public String findAnswer(String post, String question){
        return chatGPT("Виконай завдання, я надсилаю текст і питання. Якщо ти знаходиш відповідь на це питання " +
                "в тексті, то надсилаєш відповідь, якщо не знаходиш, то просто надсилай ключове слово False. " +
                "Текст:  "+post+"Питання: "+question);

    }

    private String checkProfanity(String response){
        return chatGPT("Виконай завдання, я надсилаю текст  Якщо ти знаходиш ненормативну лесику, то надсилай ключове слово Bad. Якщо все добре, то надсилай ключове слово Good." +
                "Текст:  "+response);

    }

//    public static void main(String[] args) {
//
//        System.out.println(chatGPT("Виконай завдання, я надсилаю текст і питання. Якщо ти знаходиш відповідь на це питання в тексті, то надсилаєш відповідь, якщо не знаходиш, то просто надсилай ключове слово False, якщо у запитанні знаходиш ненормативну лесику, то надсилай ключове слово Bad.Текст:  Ректорський контроль відбудеється 25.04.2024 о 20:00 на сайті кпі. Просимо всі учасників приєднатись і не забути натиснути кнопку надіслати. Успіхів!Питання: Коли це буде?Блять"));
//
//    }
}
