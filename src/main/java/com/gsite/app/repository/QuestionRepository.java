package com.gsite.app.repository;

import com.gsite.app.domain.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@SuppressWarnings("unused")
public interface QuestionRepository extends MongoRepository<Question,String> {


    @Query("{user_id : ?0}")
    List<Question> findOneByUserId(String userId,Sort sort);

    @Query("{is_frequent : \"true\"}")
    List<Question> findAllByIsFrequent(Sort sort);

}
