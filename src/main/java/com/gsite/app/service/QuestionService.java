package com.gsite.app.service;

import com.gsite.app.domain.Question;
import com.gsite.app.repository.QuestionRepository;
import com.gsite.app.service.util.SortUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionService.class);

    @Inject
    private QuestionRepository questionRepository;




    public Question save(Question question) {
        log.debug("Request to save Question : {}", question);
        Question result = questionRepository.save(question);
        return result;
    }


    public List<Question> findAll() {
        log.debug("Request to get all Questions");
        List<Question> result = questionRepository.findAll(SortUntil.CREATED_SORT);
        return result;
    }

    public Question findOne(String id) {
        log.debug("Request to get Question : {}", id);
        Question question = questionRepository.findOne(id);
        return question;
    }


    public void delete(String id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.delete(id);
    }

    public List<Question> findAllByUser(String userId) {
        log.debug("Request to get all Questions by userId: {}", userId);
        return questionRepository.findOneByUserId(userId, SortUntil.CREATED_SORT);
    }

    public List<Question> findAllByIsFrequent() {
        log.debug("Request to get all frequent Questions");
        return questionRepository.findAllByIsFrequent(SortUntil.CREATED_SORT);
    }
}
