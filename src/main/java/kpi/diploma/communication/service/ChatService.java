package kpi.diploma.communication.service;

import kpi.diploma.communication.data.ChatRepository;
import kpi.diploma.communication.data.MessageRepository;
import kpi.diploma.communication.data.ParticipantRepository;
import kpi.diploma.communication.data.UserRepository;
import kpi.diploma.communication.dto.ChatMessage;
import kpi.diploma.communication.model.Chat;
import kpi.diploma.communication.model.Message;
import kpi.diploma.communication.model.Participant;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;
    private Optional<Chat> getChatRoom(
            String user1Email,
            String user2Email
    ) {
        return participantRepository.findChatByUsers(user1Email, user2Email)
                .or(() -> {

                    Chat chatId = createChatId(user1Email, user2Email);
                    return chatId!=null?Optional.of(chatId): Optional.empty();



                });
    }

    private Chat createChatId(String user1Email, String user2Email) {
        var chatId = String.format("%s_%s", user1Email, user2Email);
        Chat chat = Chat.builder()
                .title(chatId)
                .build();

        chatRepository.save(chat);
        chat = chatRepository.findChatByTitle(chatId).orElse(null);


        User user1 = userRepository.findById(user1Email).orElse(null);
        User user2 = userRepository.findById(user2Email).orElse(null);

        if(user1!=null && user2!=null) {
            Participant participant1 = Participant.builder()
                    .chat(chat)
                    .user(user1).build();
            Participant participant2 = Participant.builder()
                    .chat(chat)
                    .user(user2).build();
            participantRepository.save(participant1);
            participantRepository.save(participant2);

        }else{
            throw new RuntimeException("User not found");

        }
        return chat;
    }

    public ChatMessage save(ChatMessage chatMessage){
        var chat = getChatRoom(chatMessage.getSenderId(), chatMessage.getRecipientId())
                .orElseThrow();

        chatMessage.setChatId(chat.getTitle());
        User user = userRepository.findById(chatMessage.getSenderId()).orElseThrow();
        Message message = Message.builder()
                .chat(chat)
                .dateTime(chatMessage.getTimestamp())
                .text(chatMessage.getText())
                .user(user).build();
        messageRepository.save(message);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String user1Email, String user2Email){
        var chat = getChatRoom(user1Email, user2Email).orElse(null);
        if(chat != null){
            List<Message> messages = messageRepository.findMessagesByChatTitle(chat.getTitle());
            List<ChatMessage> chatMessages = new ArrayList<>();
            for(Message message:messages){
                chatMessages.add(ChatMessage
                        .builder()
                        .chatId(chat.getTitle())
                        .id(message.getId())
                        .senderId(message.getUser().getEmail())
                        .recipientId(Objects.equals(message.getUser().getEmail(), user1Email) ?user2Email:user1Email)
                        .timestamp(message.getDateTime())
                        .text(message.getText())
                        .build());
            }
            return chatMessages;
        }
        return new ArrayList<>();

    }









}
