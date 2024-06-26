package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHistoryRepository extends JpaRepository<PostHistory, Long>,
    PostHistoryRepositoryExtended {

  List<PostHistory> findByPostOrderByCreatedAtAsc(Post post);

  Optional<PostHistory> findFirstByPostOrderByCreatedAtDesc(Post post);

  int deleteAllByPost(Post post);
}
