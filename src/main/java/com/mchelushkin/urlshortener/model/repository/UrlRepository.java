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

    @Query(value = "select * from (select Url, ROW_NUMBER() from url order by redirect_counter) where url = :id",
            nativeQuery = true)
    UrlStatistics getUrlById(String id);

//    @Query(value = "select url, ROW_NUMBER() over(order by redirect_counter) from url",
//            countQuery = "select COUNT(*) from url",
//            nativeQuery = true)
//    Page<ProjectionTry> getUrlPaged(Pageable pageable);
//
//    @Query(value = "select url, ROW_NUMBER() over(order by redirect_counter) from url",
//            countQuery = "select COUNT(*) from url",
//            nativeQuery = true)
//    Page<ProjectionTry> getUrlPaged(Pageable pageable);

}
