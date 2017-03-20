package com.gsite.app.service;

import com.gsite.app.domain.Notification;
import com.gsite.app.repository.NotificationRepository;
import com.gsite.app.service.util.SortUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    public Notification save(Notification notification) {
        log.debug("Request to save Notification : {}", notification);
        Notification result = notificationRepository.save(notification);
        return result;
    }

    public List<Notification> findAll() {
        log.debug("Request to get all Notifications");
        List<Notification> result = notificationRepository.findAll(SortUntil.CREATED_SORT);
        return result;
    }


    public Notification findOne(String id) {
        log.debug("Request to get Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        return notification;
    }

    public void delete(String id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepository.delete(id);
    }

    public List<Notification> findAllBySentUser(String userId) {
        log.debug("Request to get all Notifications with user: {}" + userId);
        List<Notification> result = notificationRepository.findAllBySentUser(userId, SortUntil.CREATED_SORT);
        return result;
    }
}
