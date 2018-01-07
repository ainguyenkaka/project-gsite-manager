package com.gsite.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gsite.app.domain.WebTemplate;
import com.gsite.app.service.WebTemplateService;
import com.gsite.app.web.rest.util.HeaderUtil;
import com.gsite.app.web.rest.util.PaginationUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WebTemplateResource {

    private final Logger log = LoggerFactory.getLogger(WebTemplateResource.class);

    @Inject
    private WebTemplateService webTemplateService;


    @PostMapping("/web-templates")
    @Timed
    public ResponseEntity<WebTemplate> createWebTemplate(@Valid @RequestBody WebTemplate webTemplate) throws URISyntaxException {
        log.debug("REST request to save WebTemplate : {}", webTemplate);
        if (webTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("webTemplate", "idexists", "A new webTemplate cannot already have an ID")).body(null);
        }
        WebTemplate result = webTemplateService.save(webTemplate);
        return ResponseEntity.created(new URI("/api/web-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("webTemplate", result.getId().toString()))
            .body(result);
    }


    @PutMapping("/web-templates")
    @Timed
    public ResponseEntity<WebTemplate> updateWebTemplate(@Valid @RequestBody WebTemplate webTemplate) throws URISyntaxException {
        log.debug("REST request to update WebTemplate : {}", webTemplate);
        if (webTemplate.getId() == null) {
            return createWebTemplate(webTemplate);
        }
        WebTemplate result = webTemplateService.save(webTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("webTemplate", webTemplate.getId().toString()))
            .body(result);
    }


    @GetMapping("/web-templates")
    @Timed
    public ResponseEntity<List<WebTemplate>> getAllWebTemplates()
        throws URISyntaxException {
        log.debug("REST request to get a page of WebTemplates");
        List<WebTemplate> list = webTemplateService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/web-templates/{id}")
    @Timed
    public ResponseEntity<WebTemplate> getWebTemplate(@PathVariable String id) {
        log.debug("REST request to get WebTemplate : {}", id);
        WebTemplate webTemplate = webTemplateService.findOne(id);
        return Optional.ofNullable(webTemplate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/web-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebTemplate(@PathVariable String id) {
        log.debug("REST request to delete WebTemplate : {}", id);
        webTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("webTemplate", id.toString())).build();

    }


    @GetMapping("/_search/web-templates")
    @Timed
    public ResponseEntity<List<WebTemplate>> searchWebTemplates(@RequestParam String query, @RequestParam String field)
        throws URISyntaxException {
        log.debug("REST request to search of WebTemplates");
        List<WebTemplate> list = webTemplateService.search(query, field);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
