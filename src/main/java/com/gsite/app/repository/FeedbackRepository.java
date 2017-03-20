package com.gsite.app.repository;

import com.gsite.app.domain.Feedback;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@SuppressWarnings("unused")
public interface FeedbackRepository extends MongoRepository<Feedback,String> {

    @Query("{user_id : ?0}")
    List<Feedback> findAllByUserId(String userId, Sort sort);
}
