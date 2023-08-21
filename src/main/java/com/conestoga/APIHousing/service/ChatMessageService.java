package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.configs.chat.ChatWebSocketHandler;
import com.conestoga.APIHousing.interfaces.ChatMessageRepository;
import com.conestoga.APIHousing.model.ChatMessage;
import com.conestoga.APIHousing.utils.Constants;
import com.conestoga.APIHousing.utils.FileUpload;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChatMessageService {
    Logger logger = Logger.getLogger(ChatMessageService.class.getName());
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessage> getAllChatMessages() {
        return chatMessageRepository.findAll();
    }


    public List<ChatMessage> getChatMessages(int page) {
        Pageable pageable = PageRequest.of(page, Constants.PAGE_SIZE, Sort.by("id").descending());
        Page<ChatMessage> chatMessagePage = chatMessageRepository.findAll(pageable);
        if (chatMessagePage.isEmpty()) {
            logger.warning("No chat messages found");
            return Collections.emptyList();
        }
        return chatMessagePage.getContent();
    }

    public ChatMessage saveChatMessage(ChatMessage chatMessage) throws IOException {
        if (chatMessage.getContentType().equals(Constants.CONTENT_IMG)) {
            chatMessage.setContent(FileUpload.convertBase64ToFile(chatMessage.getContent()));
        }
        Date date = new Date();
        chatMessage.setDate(date);
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        ChatWebSocketHandler.getInstance().broadcastMessage(savedChatMessage); // Broadcast the message
        return savedChatMessage;
    }

    public boolean hasMoreMessages(int page) {
        int totalMessages = (int) chatMessageRepository.count(); // Get the total number of chat messages
        int totalPages = (int) Math.ceil((double) totalMessages / Constants.PAGE_SIZE); // Calculate the total number of pages

        return page < totalPages; // Return true if there are more pages after the current page
    }


}
