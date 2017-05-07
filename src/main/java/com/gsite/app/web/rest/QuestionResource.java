package com.gsite.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gsite.app.domain.Question;
import com.gsite.app.service.QuestionService;
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
public class QuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResource.class);

    @Inject
    private QuestionService questionService;

    @PostMapping("/questions")
    @Timed
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) throws URISyntaxException {
        log.debug("REST request to save Question : {}", question);
        if (question.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("question", "idexists", "A new question cannot already have an ID")).body(null);
        }
        Question result = questionService.save(question);
        return ResponseEntity.created(new URI("/api/questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("question", result.getId().toString()))
            .body(result);
    }


    @PutMapping("/questions")
    @Timed
    public ResponseEntity<Question> updateQuestion(@Valid @RequestBody Question question) throws URISyntaxException {
        log.debug("REST request to update Question : {}", question);
        if (question.getId() == null) {
            return createQuestion(question);
        }
        Question result = questionService.save(question);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("question", question.getId().toString()))
            .body(result);
    }

    @GetMapping("/questions")
    @Timed
    public ResponseEntity<List<Question>> getAllQuestions()
        throws URISyntaxException {
        log.debug("REST request to get a page of Questions");
        List<Question> list = questionService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/myquestions")
    @Timed
    public ResponseEntity<List<Question>> getAllQuestionsByUser(@ApiParam String userId)
        throws URISyntaxException {
        log.debug("REST request to get all question of user: {}",userId);
        List<Question> list = questionService.findAllByUser(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/faq")
    @Timed
    public ResponseEntity<List<Question>> getAllFrequentQuestions()
        throws URISyntaxException {
        log.debug("REST request to get all frequent question");
        List<Question> list = questionService.findAllByIsFrequent();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/questions/{id}")
    @Timed
    public ResponseEntity<Question> getQuestion(@PathVariable String id) {
        log.debug("REST request to get Question : {}", id);
        Question question = questionService.findOne(id);
        return Optional.ofNullable(question)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/questions/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        log.debug("REST request to delete Question : {}", id);
        questionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("question", id.toString())).build();
    }

}
