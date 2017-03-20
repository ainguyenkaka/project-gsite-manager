package com.gsite.app.repository;

import com.gsite.app.domain.Notification;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@SuppressWarnings("unused")
public interface NotificationRepository extends MongoRepository<Notification,String> {

    @Query("{sent_users : ?0}")
    List<Notification> findAllBySentUser(String userId, Sort sort);
}
