package kpi.diploma.communication.controller;

import kpi.diploma.communication.dto.ChatMessage;
import kpi.diploma.communication.dto.ChatNotification;
import kpi.diploma.communication.model.Message;
import kpi.diploma.communication.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        Message savedMsg = chatService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getUser().getEmail(),
                        savedMsg.getRecipient().getEmail(),
                        savedMsg.getText(),
                        savedMsg.isViewed()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId,
                                                              @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/chat/{messageId}/viewed/{userId}")
    @ResponseBody
    public void markMessageAsViewed(@PathVariable("messageId") long messageId , @PathVariable(name="userId") String userId) {
        System.out.println(userId);
        System.out.println("KEYWORD");
        chatService.markMessageAsViewed(messageId, userId);
    }
}
