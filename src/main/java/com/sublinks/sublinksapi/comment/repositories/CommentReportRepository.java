package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentReport;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long>,
    CommentReportRepositorySearch {

  @Modifying
  @Query("UPDATE CommentReport cr SET cr.resolved = true, cr.resolver = :resolver WHERE cr.resolved = false AND cr.comment = :comment")
  void resolveAllReportsByComment(@Param("comment") Comment comment,
      @Param("resolver") Person resolver);

  @Modifying
  @Query("UPDATE CommentReport cr SET cr.resolved = true, cr.resolver = :resolver WHERE cr.resolved = false AND cr.comment.person = :creator")
  void resolveAllReportsByCommentCreator(@Param("creator") Person creator,
      @Param("resolver") Person resolver);

  @Modifying
  @Query("UPDATE CommentReport cr SET cr.resolved = true, cr.resolver = :resolver WHERE cr.resolved = false AND cr.comment.person = :creator AND cr.comment.community = :community")
  void resolveAllReportsByCommentCreatorAndCommunity(@Param("creator") Person creator,
      @Param("community") Community community, @Param("resolver") Person resolver);
}

