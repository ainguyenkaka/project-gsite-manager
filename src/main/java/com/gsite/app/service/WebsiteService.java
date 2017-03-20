package com.gsite.app.service;

import com.gsite.app.domain.Website;
import com.gsite.app.repository.WebsiteRepository;
import com.gsite.app.service.util.SortUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class WebsiteService {

    private final Logger log = LoggerFactory.getLogger(WebsiteService.class);

    @Inject
    private WebsiteRepository websiteRepository;


    /**
     * Save a website.
     *
     * @param website the entity to save
     * @return the persisted entity
     */
    public Website save(Website website) {
        log.debug("Request to save Website : {}", website);
        Website result = websiteRepository.save(website);
        return result;
    }

    /**
     *  Get all the websites.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Website> findAll(Pageable pageable) {
        log.debug("Request to get all Websites");
        Page<Website> result = websiteRepository.findAll(pageable);
        return result;
    }

    public List<Website> findAll() {
        log.debug("Request to get all Websites");

        List<Website> result = websiteRepository.findAll(SortUntil.CREATED_SORT);
        return result;
    }

    /**
     *  Get one website by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Website findOne(String id) {
        log.debug("Request to get Website : {}", id);
        Website website = websiteRepository.findOne(id);
        return website;
    }

    public Website findOneByDomain(String domain) {
        log.debug("Request to get Website with domain : {}", domain);
        Website website = websiteRepository.findOneByDomain(domain);
        return website;
    }

    /**
     *  Delete the  website by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Website : {}", id);
        websiteRepository.delete(id);
    }

    public List<Website> findAllByUserID(String userId) {
        log.debug("Request to get user's Websites");
        return websiteRepository.findAllByUserID(userId,SortUntil.CREATED_SORT);
    }

    public List<Website> findAllSharedByUserEmail(String user_email) {
        log.debug("Request to get shared Websites");
        return websiteRepository.findAllSharedByUserEmail(user_email);
    }


    public List<Website> findAllPaidByUserID(String userId) {
        log.debug("Request to get user's paid Websites");
        return websiteRepository.findAllPaidByUserID(userId);
    }

    public List<Website> findAllUnpaidByUserID(String userId) {
        log.debug("Request to get user's un paid Websites");
        return websiteRepository.findAllUnPaidByUserID(userId);
    }
}
