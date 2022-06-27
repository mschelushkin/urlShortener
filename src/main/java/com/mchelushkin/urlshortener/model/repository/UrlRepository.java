package com.mchelushkin.urlshortener.model.repository;

import javax.transaction.Transactional;

import com.mchelushkin.urlshortener.model.dto.UrlStatistics;
import com.mchelushkin.urlshortener.model.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {

    @Modifying
    @Transactional
    @Query("update Url set redirectCounter = redirectCounter + 1 where url = :id")
    void incrementRedirectsNumber(@Param("id") String id);

}
