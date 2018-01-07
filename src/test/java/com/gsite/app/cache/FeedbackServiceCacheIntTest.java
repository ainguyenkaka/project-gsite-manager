package com.gsite.app.cache;

import com.gsite.app.GsiteManagerApp;
import com.gsite.app.domain.Feedback;
import com.gsite.app.repository.FeedbackRepository;
import com.gsite.app.service.FeedbackService;
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
public class FeedbackServiceCacheIntTest {

    private static final String DEFAULT_TITLE = "CCCCCCCCC";
    private static final String DEFAULT_CONTENT = "CCCCCCCCC";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);

    private static final String DEFAULT_USER_ID = "CCCCCCCCC";


    @Inject
    private FeedbackService feedbackService;

    @Inject
    private FeedbackRepository feedbackRepository;

    private Feedback feedback;

    public static Feedback createEntity() {
        Feedback feedback = new Feedback()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .created(DEFAULT_CREATED)
            .user_id(DEFAULT_USER_ID);
        return feedback;
    }
    @Before
    public void initTest() {
        feedbackRepository.deleteAll();
        feedback = createEntity();
    }


    @Test
    public void testCacheAllFeedbacks() throws Exception{
        assertThat(feedbackService.findAll().isEmpty()).isTrue();

        feedback = feedbackRepository.save(feedback);
        assertThat(feedbackRepository.findAll().isEmpty()).isFalse();
        assertThat(feedbackService.findAll().isEmpty()).isTrue();

        feedback = feedbackService.save(feedback);
        assertThat(feedbackService.findAll().isEmpty()).isFalse();

        feedbackRepository.delete(feedback.getId());
        assertThat(feedbackRepository.findAll().isEmpty()).isTrue();
        assertThat(feedbackService.findAll().isEmpty()).isFalse();

        feedbackService.delete(feedback.getId());
        assertThat(feedbackService.findAll().isEmpty()).isTrue();
    }


    @Test
    public void testCacheFindOneFeedback() throws Exception {
        feedback = feedbackRepository.save(feedback);
        assertThat(feedbackRepository.findOne(feedback.getId())).isNotNull();
        assertThat(feedbackService.findOne(feedback.getId())).isNotNull();

        feedbackRepository.delete(feedback.getId());
        assertThat(feedbackRepository.findOne(feedback.getId())).isNull();
        assertThat(feedbackService.findOne(feedback.getId())).isNotNull();

    }

    @Test
    public void testCacheAllFeedbacksByUser() throws Exception {
        assertThat(feedbackService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isTrue();

        feedback = feedbackRepository.save(feedback);
        assertThat(feedbackRepository.findAll().isEmpty()).isFalse();
        assertThat(feedbackService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isTrue();

        feedback = feedbackService.save(feedback);
        assertThat(feedbackService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isFalse();

        feedbackRepository.delete(feedback.getId());
        assertThat(feedbackRepository.findAll().isEmpty()).isTrue();
        assertThat(feedbackService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isFalse();

        feedbackService.delete(feedback.getId());
        assertThat(feedbackService.findAllByUser(DEFAULT_USER_ID).isEmpty()).isTrue();
    }


}
