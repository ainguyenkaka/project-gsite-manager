package com.gsite.app.cache;

import com.gsite.app.GsiteManagerApp;
import com.gsite.app.domain.WebTemplate;
import com.gsite.app.repository.WebTemplateRepository;
import com.gsite.app.service.WebTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsiteManagerApp.class)
public class WebTemplateServiceCacheIntTest {

    private static final String DEFAULT_NAME = "CCCCCCCCC";

    private static final String DEFAULT_IMAGE = "CCCCCCCCC";

    private static final String DEFAULT_CATEGORY = "CCCCCCCCC";

    private static final String DEFAULT_SOURCE = "CCCCCCCCC";

    private static final String DEFAULT_SEARCH_QUERY = "CCCCC";

    private static final String DEFAULT_SEARCH_FIELD = "name";


    @Inject
    private WebTemplateService webTemplateService;

    @Inject
    private WebTemplateRepository webTemplateRepository;

    private WebTemplate webTemplate;

    public static WebTemplate createEntity() {
        WebTemplate webTemplate = new WebTemplate(DEFAULT_NAME, DEFAULT_SOURCE, DEFAULT_CATEGORY, DEFAULT_IMAGE);
        return webTemplate;
    }

    @Before
    public void initTest() {
        webTemplateRepository.deleteAll();
        webTemplate = createEntity();
    }


    @Test
    public void testCacheAllWebTemplates() throws Exception{
        assertThat(webTemplateService.findAll().isEmpty()).isTrue();

        webTemplate = webTemplateRepository.save(webTemplate);
        assertThat(webTemplateRepository.findAll().isEmpty()).isFalse();
        assertThat(webTemplateService.findAll().isEmpty()).isTrue();

        webTemplate = webTemplateService.save(webTemplate);
        assertThat(webTemplateService.findAll().isEmpty()).isFalse();

        webTemplateRepository.delete(webTemplate.getId());
        assertThat(webTemplateRepository.findAll().isEmpty()).isTrue();
        assertThat(webTemplateService.findAll().isEmpty()).isFalse();

        webTemplateService.delete(webTemplate.getId());
        assertThat(webTemplateService.findAll().isEmpty()).isTrue();
    }


    @Test
    public void testCacheFindOneWebTemplate() throws Exception {
        webTemplate = webTemplateRepository.save(webTemplate);
        assertThat(webTemplateRepository.findOne(webTemplate.getId())).isNotNull();
        assertThat(webTemplateService.findOne(webTemplate.getId())).isNotNull();

        webTemplateRepository.delete(webTemplate.getId());
        assertThat(webTemplateRepository.findOne(webTemplate.getId())).isNull();
        assertThat(webTemplateService.findOne(webTemplate.getId())).isNotNull();

    }

    @Test
    public void testCacheAllWebTemplatesByUser() throws Exception {
        assertThat(webTemplateService.search(DEFAULT_SEARCH_QUERY,DEFAULT_SEARCH_FIELD).isEmpty()).isTrue();

        webTemplate = webTemplateRepository.save(webTemplate);
        assertThat(webTemplateRepository.findAll().isEmpty()).isFalse();
        assertThat(webTemplateService.search(DEFAULT_SEARCH_QUERY,DEFAULT_SEARCH_FIELD).isEmpty()).isTrue();

        webTemplate = webTemplateService.save(webTemplate);
        assertThat(webTemplateService.search(DEFAULT_SEARCH_QUERY,DEFAULT_SEARCH_FIELD).isEmpty()).isFalse();

        webTemplateRepository.delete(webTemplate.getId());
        assertThat(webTemplateRepository.findAll().isEmpty()).isTrue();
        assertThat(webTemplateService.search(DEFAULT_SEARCH_QUERY,DEFAULT_SEARCH_FIELD).isEmpty()).isFalse();

        webTemplateService.delete(webTemplate.getId());
        assertThat(webTemplateService.search(DEFAULT_SEARCH_QUERY,DEFAULT_SEARCH_FIELD).isEmpty()).isTrue();
    }


}
