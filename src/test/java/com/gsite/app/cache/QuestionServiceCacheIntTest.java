package com.gsite.app.cache;

import com.gsite.app.GsiteManagerApp;
import com.gsite.app.domain.Question;
import com.gsite.app.repository.QuestionRepository;
import com.gsite.app.service.QuestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsiteManagerApp.class)
public class QuestionServiceCacheIntTest {

    private static final String DEFAULT_CONTENT = "CCCCCCCCC";

    private static final String DEFAULT_ANSWER = "CCCCCCCCC";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);

    private static final String DEFAULT_USER_ID = "CCCCCCCCC";


    @Inject
    private QuestionService questionService;

    @Inject
    private QuestionRepository questionRepository;

    private Question question;

    public static Question createEntity() {
        Question question = new Question()
            .content(DEFAULT_CONTENT)
            .answer(DEFAULT_ANSWER)
            .created(DEFAULT_CREATED)
            .user_id(DEFAULT_USER_ID);
        return question;
    }

    @Before
    public void initTest() {
        questionRepository.deleteAll();
        question = createEntity();
    }


    @Test
    public void testCacheAllQuestions() throws Exception{
        assertThat(questionService.findAll().isEmpty()).isTrue();

        question = questionRepository.save(question);
        assertThat(questionRepository.findAll().isEmpty()).isFalse();
        assertThat(questionService.findAll().isEmpty()).isTrue();

        question = questionService.save(question);
        assertThat(questionService.findAll().isEmpty()).isFalse();

        questionRepository.delete(question.getId());
        assertThat(questionRepository.findAll().isEmpty()).isTrue();
        assertThat(questionService.findAll().isEmpty()).isFalse();

        questionService.delete(question.getId());
        assertThat(questionService.findAll().isEmpty()).isTrue();
    }


    @Test
    public void testCacheFindOneQuestion() throws Exception {
        question = questionRepository.save(question);
        assertThat(questionRepository.findOne(question.getId())).isNotNull();
        assertThat(questionService.findOne(question.getId())).isNotNull();

        questionRepository.delete(question.getId());
        assertThat(questionRepository.findOne(question.getId())).isNull();
        assertThat(questionService.findOne(question.getId())).isNotNull();

    }

    @Test
    public void testCacheAllQuestionsByUser() throws Exception {
        assertThat(questionService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isTrue();

        question = questionRepository.save(question);
        assertThat(questionRepository.findAll().isEmpty()).isFalse();
        assertThat(questionService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isTrue();

        question = questionService.save(question);
        assertThat(questionService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isFalse();

        questionRepository.delete(question.getId());
        assertThat(questionRepository.findAll().isEmpty()).isTrue();
        assertThat(questionService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isFalse();

        questionService.delete(question.getId());
        assertThat(questionService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isTrue();
    }


}
