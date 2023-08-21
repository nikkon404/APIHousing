package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    ///findByUserId
        List<Notification> findByUserIdOrderByIdDesc(Long userId);

}