package com.gsite.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gsite.app.domain.Feedback;
import com.gsite.app.service.FeedbackService;
import com.gsite.app.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    @Inject
    private FeedbackService feedbackService;


    @PostMapping("/feedbacks")
    @Timed
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody Feedback feedback) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedback);
        if (feedback.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("feedback", "idexists", "A new feedback cannot already have an ID")).body(null);
        }
        Feedback result = feedbackService.save(feedback);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("feedback", result.getId().toString()))
            .body(result);
    }


    @PutMapping("/feedbacks")
    @Timed
    public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}", feedback);
        if (feedback.getId() == null) {
            return createFeedback(feedback);
        }
        Feedback result = feedbackService.save(feedback);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("feedback", feedback.getId().toString()))
            .body(result);
    }


    @GetMapping("/feedbacks")
    @Timed
    public ResponseEntity<List<Feedback>> getAllFeedbacks()
        throws URISyntaxException {
        log.debug("REST request to get a page of Feedbacks");
        List<Feedback> list = feedbackService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/feedbacks/{id}")
    @Timed
    public ResponseEntity<Feedback> getFeedback(@PathVariable String id) {
        log.debug("REST request to get Feedback : {}", id);
        Feedback feedback = feedbackService.findOne(id);
        return Optional.ofNullable(feedback)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/feedbacks/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feedback", id.toString())).build();
    }


    @GetMapping("/myfeedbacks")
    @Timed
    public ResponseEntity<List<Feedback>> getAllFeedbacks(@ApiParam String userId)
        throws URISyntaxException {
        log.debug("REST request to get a page of Feedbacks with user: {}",userId);
        List<Feedback> list = feedbackService.findAllByUser(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
