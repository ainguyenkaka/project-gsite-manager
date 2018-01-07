package com.gsite.app.web.rest;

import com.gsite.app.GsiteManagerApp;
import com.gsite.app.domain.WebTemplate;
import com.gsite.app.repository.WebTemplateRepository;
import com.gsite.app.service.WebTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsiteManagerApp.class)
public class WebTemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    @Inject
    private WebTemplateRepository webTemplateRepository;

    @Inject
    private WebTemplateService webTemplateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restWebTemplateMockMvc;

    private WebTemplate webTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebTemplateResource webTemplateResource = new WebTemplateResource();
        ReflectionTestUtils.setField(webTemplateResource, "webTemplateService", webTemplateService);
        this.restWebTemplateMockMvc = MockMvcBuilders.standaloneSetup(webTemplateResource)
            .setMessageConverters(jacksonMessageConverter).build();
    }

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
    public void createWebTemplate() throws Exception {
        int databaseSizeBeforeCreate = webTemplateRepository.findAll().size();

        restWebTemplateMockMvc.perform(post("/api/web-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webTemplate)))
            .andExpect(status().isCreated());

        List<WebTemplate> webTemplateList = webTemplateRepository.findAll();
        assertThat(webTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        WebTemplate testWebTemplate = webTemplateList.get(webTemplateList.size() - 1);
        assertThat(testWebTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWebTemplate.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    public void createWebTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webTemplateRepository.findAll().size();

        WebTemplate existingWebTemplate = new WebTemplate();
        existingWebTemplate.setId("existing_id");

        restWebTemplateMockMvc.perform(post("/api/web-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWebTemplate)))
            .andExpect(status().isBadRequest());

        List<WebTemplate> webTemplateList = webTemplateRepository.findAll();
        assertThat(webTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = webTemplateRepository.findAll().size();

        webTemplate.setName(null);


        restWebTemplateMockMvc.perform(post("/api/web-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webTemplate)))
            .andExpect(status().isBadRequest());

        List<WebTemplate> webTemplateList = webTemplateRepository.findAll();
        assertThat(webTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = webTemplateRepository.findAll().size();

        webTemplate.setSource(null);


        restWebTemplateMockMvc.perform(post("/api/web-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webTemplate)))
            .andExpect(status().isBadRequest());

        List<WebTemplate> webTemplateList = webTemplateRepository.findAll();
        assertThat(webTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllWebTemplates() throws Exception {

        webTemplateService.save(webTemplate);


        restWebTemplateMockMvc.perform(get("/api/web-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webTemplate.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())));
    }

    @Test
    public void getWebTemplate() throws Exception {

        webTemplateRepository.save(webTemplate);


        restWebTemplateMockMvc.perform(get("/api/web-templates/{id}", webTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webTemplate.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    public void getNonExistingWebTemplate() throws Exception {

        restWebTemplateMockMvc.perform(get("/api/web-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWebTemplate() throws Exception {

        webTemplateService.save(webTemplate);

        int databaseSizeBeforeUpdate = webTemplateRepository.findAll().size();


        WebTemplate updatedWebTemplate = webTemplateRepository.findOne(webTemplate.getId());
        updatedWebTemplate
            .name(UPDATED_NAME)
            .source(UPDATED_SOURCE);

        restWebTemplateMockMvc.perform(put("/api/web-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWebTemplate)))
            .andExpect(status().isOk());


        List<WebTemplate> webTemplateList = webTemplateRepository.findAll();
        assertThat(webTemplateList).hasSize(databaseSizeBeforeUpdate);
        WebTemplate testWebTemplate = webTemplateList.get(webTemplateList.size() - 1);
        assertThat(testWebTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWebTemplate.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    public void updateNonExistingWebTemplate() throws Exception {
        int databaseSizeBeforeUpdate = webTemplateRepository.findAll().size();


        restWebTemplateMockMvc.perform(put("/api/web-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webTemplate)))
            .andExpect(status().isCreated());


        List<WebTemplate> webTemplateList = webTemplateRepository.findAll();
        assertThat(webTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteWebTemplate() throws Exception {

        webTemplateService.save(webTemplate);

        int databaseSizeBeforeDelete = webTemplateRepository.findAll().size();


        restWebTemplateMockMvc.perform(delete("/api/web-templates/{id}", webTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());


        List<WebTemplate> webTemplateList = webTemplateRepository.findAll();
        assertThat(webTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
