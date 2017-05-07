package com.gsite.app.cache;

import com.gsite.app.GsiteManagerApp;
import com.gsite.app.domain.Website;
import com.gsite.app.repository.WebsiteRepository;
import com.gsite.app.service.WebsiteService;
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
public class WebsiteServiceCacheIntTest {

    private static final String DEFAULT_NAME = "CCCCCCCCC";
    private static final String DEFAULT_TEMPLATE_ID = "CCCCCCCCC";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);

    private static final String DEFAULT_DOMAIN = "CCCCCCCCC";
    private static final String DEFAULT_USER_ID = "CCCCCCCCC";


    @Inject
    private WebsiteService websiteService;

    @Inject
    private WebsiteRepository websiteRepository;

    private Website website;

    public static Website createEntity() {
        Website website = new Website()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .user_id(DEFAULT_USER_ID)
            .template(DEFAULT_TEMPLATE_ID)
            .domain(DEFAULT_DOMAIN);
        website.setPaid(true);
        return website;
    }

    @Before
    public void initTest() {
        websiteRepository.deleteAll();
        website = createEntity();
    }


    @Test
    public void testCacheAllWebsites() throws Exception{
        assertThat(websiteService.findAll().isEmpty()).isTrue();

        website = websiteRepository.save(website);
        assertThat(websiteRepository.findAll().isEmpty()).isFalse();
        assertThat(websiteService.findAll().isEmpty()).isTrue();

        website = websiteService.save(website);
        assertThat(websiteService.findAll().isEmpty()).isFalse();

        websiteRepository.delete(website.getId());
        assertThat(websiteRepository.findAll().isEmpty()).isTrue();
        assertThat(websiteService.findAll().isEmpty()).isFalse();

        websiteService.delete(website.getId());
        assertThat(websiteService.findAll().isEmpty()).isTrue();
    }


    @Test
    public void testCacheFindOneWebsite() throws Exception {
        website = websiteRepository.save(website);
        assertThat(websiteRepository.findOne(website.getId())).isNotNull();
        assertThat(websiteService.findOne(website.getId())).isNotNull();

        websiteRepository.delete(website.getId());
        assertThat(websiteRepository.findOne(website.getId())).isNull();
        assertThat(websiteService.findOne(website.getId())).isNotNull();

    }

    @Test
    public void testCacheAllWebsitesByUser() throws Exception {
        assertThat(websiteService.findAllByUserID(DEFAULT_USER_ID).isEmpty()).isTrue();

        website = websiteRepository.save(website);
        assertThat(websiteRepository.findAll().isEmpty()).isFalse();
        assertThat(websiteService.findAllByUserID(DEFAULT_USER_ID).isEmpty()).isTrue();

        website = websiteService.save(website);
        assertThat(websiteService.findAllByUserID(DEFAULT_USER_ID).isEmpty()).isFalse();

        websiteRepository.delete(website.getId());
        assertThat(websiteRepository.findAll().isEmpty()).isTrue();
        assertThat(websiteService.findAllByUserID(DEFAULT_USER_ID).isEmpty()).isFalse();

        websiteService.delete(website.getId());
        assertThat(websiteService.findAllByUserID(DEFAULT_USER_ID).isEmpty()).isTrue();
    }


}
