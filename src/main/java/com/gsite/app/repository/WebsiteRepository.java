package com.gsite.app.repository;

import com.gsite.app.domain.Website;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@SuppressWarnings("unused")
public interface WebsiteRepository extends MongoRepository<Website,String> {

    @Query("{user_id : ?0}")
    List<Website> findAllByUserID(String userId, Sort sort);

    @Query("{shared_users : ?0}")
    List<Website> findAllSharedByUserEmail(String user_email);

    @Query("{user_id : ?0, is_paid: true}")
    List<Website> findAllPaidByUserID(String user_id);

    @Query("{user_id : ?0, is_paid: false}")
    List<Website> findAllUnPaidByUserID(String user_id);

    Website findOneByDomain(String domain);
}
