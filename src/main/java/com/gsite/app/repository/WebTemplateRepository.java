package com.gsite.app.repository;

import com.gsite.app.domain.WebTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@SuppressWarnings("unused")
public interface WebTemplateRepository extends MongoRepository<WebTemplate,String> {

    @Query("{?1: {$regex: ?0, $options: \"i\"}}")
    Page<WebTemplate> search(String query, String field, Pageable pageable);

    WebTemplate findOneBySource(String source);
}
