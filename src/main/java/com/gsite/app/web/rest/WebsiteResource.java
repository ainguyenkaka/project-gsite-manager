package com.gsite.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gsite.app.domain.Website;
import com.gsite.app.service.WebsiteService;
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
public class WebsiteResource {

    private final Logger log = LoggerFactory.getLogger(WebsiteResource.class);

    @Inject
    private WebsiteService websiteService;

    @PostMapping("/websites")
    @Timed
    public ResponseEntity<Website> createWebsite(@Valid @RequestBody Website website) throws URISyntaxException {
        log.debug("REST request to save Website : {}", website);
        if (website.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("website", "idexists", "A new website cannot already have an ID")).body(null);
        }
        if(websiteService.findOneByDomain(website.getDomain()) != null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("website", "idexists", "A new website domain is already existed!")).body(website);
        }
        Website result = websiteService.save(website);
        return ResponseEntity.created(new URI("/api/websites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("website", result.getId().toString()))
            .body(result);
    }

    @PutMapping("/websites")
    @Timed
    public ResponseEntity<Website> updateWebsite(@Valid @RequestBody Website website) throws URISyntaxException {
        log.debug("REST request to update Website : {}", website);
        if (website.getId() == null) {
            return createWebsite(website);
        }
        Website result = websiteService.save(website);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("website", website.getId().toString()))
            .body(result);
    }


    @GetMapping("/websites")
    @Timed
    public List<Website> getAllWebsites() {
        log.debug("REST request to get all Websites");
        List<Website> websites = websiteService.findAll();
        return websites;
    }


    @GetMapping("/websites/{id}")
    @Timed
    public ResponseEntity<Website> getWebsite(@PathVariable String id) {
        log.debug("REST request to get Website : {}", id);
        Website website = websiteService.findOne(id);
        return Optional.ofNullable(website)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/websites/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebsite(@PathVariable String id) {
        log.debug("REST request to delete Website : {}", id);
        websiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("website", id.toString())).build();
    }

    @GetMapping("/websites/domain")
    @Timed
    public ResponseEntity<Website> getWebsiteByDomain(@ApiParam String domain) {
        log.debug("REST request to get Website with domain : {}", domain);
        Website website = websiteService.findOneByDomain(domain);
        return Optional.ofNullable(website)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
