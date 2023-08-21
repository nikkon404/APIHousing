package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    //get all messages
    @Override
    List<ChatMessage> findAll();

     Page<ChatMessage> findAll(Pageable pageable);

    
}
