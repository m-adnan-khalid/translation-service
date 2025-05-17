package com.app.translationservice.repository;

import com.app.translationservice.model.Translation;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Integer> {

  Page<Translation> findByLocale(String locale, Pageable pageable);

  @Query("SELECT DISTINCT t FROM Translation t JOIN t.tags tag WHERE tag IN :tags")
  List<Translation> findByTagsIn(@Param("tags") Set<String> tags);

  List<Translation> findByKeyContainingIgnoreCase(String key);

  List<Translation> findByContentContainingIgnoreCase(String content);
}
