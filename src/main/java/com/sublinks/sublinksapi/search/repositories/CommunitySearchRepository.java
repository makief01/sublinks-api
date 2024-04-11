package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.community.entities.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunitySearchRepository extends JpaRepository<Community, Long> {

  @Query(value = "SELECT c.* FROM communities c WHERE MATCH(c.title, c.title_slug, c.description) AGAINST (CONCAT('*', :keyword, '*') IN BOOLEAN MODE);",
      countQuery = "SELECT COUNT(c.id) FROM communities c WHERE MATCH(c.title, c.title_slug, c.description) AGAINST (CONCAT('*', :keyword, '*') IN BOOLEAN MODE);",
      nativeQuery = true)
  Page<Community> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
