package com.conestoga.APIHousing.configs.chat;

import com.conestoga.APIHousing.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    Logger logger = Logger.getLogger(ChatWebSocketHandler.class.getName());

  // make it singleton
  private static ChatWebSocketHandler instance = null;

  public static ChatWebSocketHandler getInstance() {
    if (instance == null) {
      instance = new ChatWebSocketHandler();
    }
    return instance;
  }

  private List<WebSocketSession> sessions = new ArrayList<>();

  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  public ChatWebSocketHandler() {}

  // get all sessions
  private List<WebSocketSession> getSessions() {
    return sessions;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    // Handle a new WebSocket connection
    logger.info("WebSocket connection established: " + session.getId());
    sessions.add(session);

    // print number of sessions in the server
    logger.info("Number of sessions: " + sessions.size());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    //      String payload = message.getPayload();
    // System.out.println("Received message: " + payload);

    // // Convert the JSON payload to a ChatMessage object
    // ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

    // // Save the chat message using the ChatMessageService
    // chatMessageService.saveChatMessage(chatMessage);

    //     // Save the chat message using the ChatMessageService
    //     chatMessageService.saveChatMessage(chatMessage);

    //     // Broadcast the message to other connected clients if needed
    //     // Example:
    //     broadcastMessage(message);
  }

  public void broadcastMessage(ChatMessage chatMessage) {
    try {
      TextMessage message = new TextMessage(objectMapper.writeValueAsString(chatMessage));
      for (WebSocketSession session : getSessions()) {
        if (session.isOpen()) {
            logger.info("Session is open: " + session.getId());
            logger.info("Sending message to session: " + session.getId());
          session.sendMessage(message);
        }
      }
    } catch (Exception e) {
      logger.warning(
          "Exception occurred while sending message to WebSocket client: " + e.getMessage());
      // Handle exception if message sending fails
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    // Handle WebSocket connection closed
    logger.info(
        "WebSocket connection closed: " + session.getId() + ", Status: " + status.getCode());
    sessions.remove(session);
    logger.info("Number of sessions: " + sessions.size());
  }
}
