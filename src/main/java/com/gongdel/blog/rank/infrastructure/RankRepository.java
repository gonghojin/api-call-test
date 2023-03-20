package com.gongdel.blog.rank.infrastructure;

import com.gongdel.blog.rank.domain.Rank;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> {

  Optional<Rank> findByKeyword(String keyword);

  /**
   * 많은 검색 횟수의 키워드를 상위 10 개 조회
   *
   * @return 검색 횟수가 많은 순의 Rank 목록
   */
  List<Rank> findTop10ByOrderByCountDesc();
}
