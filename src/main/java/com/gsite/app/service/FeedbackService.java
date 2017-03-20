package com.gsite.app.service;

import com.gsite.app.domain.Feedback;
import com.gsite.app.repository.FeedbackRepository;
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
public class FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackService.class);

    @Inject
    private FeedbackRepository feedbackRepository;




    public Feedback save(Feedback feedback) {
        log.debug("Request to save Feedback : {}", feedback);
        Feedback result = feedbackRepository.save(feedback);
        return result;
    }


    public List<Feedback> findAll() {
        log.debug("Request to get all Feedbacks");
        List<Feedback> result = feedbackRepository.findAll(SortUntil.CREATED_SORT);
        return result;
    }

    public Feedback findOne(String id) {
        log.debug("Request to get Feedback : {}", id);
        Feedback feedback = feedbackRepository.findOne(id);
        return feedback;
    }


    public void delete(String id) {
        log.debug("Request to delete Feedback : {}", id);
        feedbackRepository.delete(id);
    }

    public List<Feedback> findAllByUser(String userId) {
        log.debug("Request to get all Feedbacks with user: {}",userId);
        List<Feedback> result = feedbackRepository.findAllByUserId(userId, SortUntil.CREATED_SORT);
        return result;
    }
}
