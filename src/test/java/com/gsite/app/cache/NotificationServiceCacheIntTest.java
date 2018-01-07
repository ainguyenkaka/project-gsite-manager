package com.gsite.app.cache;

import com.gsite.app.GsiteManagerApp;
import com.gsite.app.domain.Notification;
import com.gsite.app.repository.NotificationRepository;
import com.gsite.app.service.NotificationService;
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
public class NotificationServiceCacheIntTest {

    private static final String DEFAULT_TITLE = "CCCCCCCCC";
    private static final String DEFAULT_CONTENT = "CCCCCCCCC";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);

    private static final String DEFAULT_USER_ID = "CCCCCCCCC";

    @Inject
    private NotificationService notificationService;

    @Inject
    private NotificationRepository notificationRepository;

    private Notification notification;

    public static Notification createEntity() {
        Notification notification = new Notification()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .created(DEFAULT_CREATED);
        notification.getSentUsers().add(DEFAULT_USER_ID);
        return notification;
    }

    @Before
    public void initTest() {
        notificationRepository.deleteAll();
        notification = createEntity();
    }


    @Test
    public void testCacheAllNotifications() throws Exception{
        assertThat(notificationService.findAll().isEmpty()).isTrue();

        notification = notificationRepository.save(notification);
        assertThat(notificationRepository.findAll().isEmpty()).isFalse();
        assertThat(notificationService.findAll().isEmpty()).isTrue();

        notification = notificationService.save(notification);
        assertThat(notificationService.findAll().isEmpty()).isFalse();

        notificationRepository.delete(notification.getId());
        assertThat(notificationRepository.findAll().isEmpty()).isTrue();
        assertThat(notificationService.findAll().isEmpty()).isFalse();

        notificationService.delete(notification.getId());
        assertThat(notificationService.findAll().isEmpty()).isTrue();
    }


    @Test
    public void testCacheFindOneNotification() throws Exception {
        notification = notificationRepository.save(notification);
        assertThat(notificationRepository.findOne(notification.getId())).isNotNull();
        assertThat(notificationService.findOne(notification.getId())).isNotNull();

        notificationRepository.delete(notification.getId());
        assertThat(notificationRepository.findOne(notification.getId())).isNull();
        assertThat(notificationService.findOne(notification.getId())).isNotNull();

    }

    @Test
    public void testCacheAllNotificationsByUser() throws Exception {
        assertThat(notificationService.findAllBySentUser(DEFAULT_USER_ID).isEmpty()).isTrue();

        notification = notificationRepository.save(notification);
        assertThat(notificationRepository.findAll().isEmpty()).isFalse();
        assertThat(notificationService.findAllBySentUser(DEFAULT_USER_ID).isEmpty()).isTrue();

        notification = notificationService.save(notification);
        assertThat(notificationService.findAllBySentUser(DEFAULT_USER_ID).isEmpty()).isFalse();

        notificationRepository.delete(notification.getId());
        assertThat(notificationRepository.findAll().isEmpty()).isTrue();
        assertThat(notificationService.findAllBySentUser(DEFAULT_USER_ID).isEmpty()).isFalse();

        notificationService.delete(notification.getId());
        assertThat(notificationService.findAllBySentUser(DEFAULT_USER_ID).isEmpty()).isTrue();
    }


}
