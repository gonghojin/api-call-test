package com.gongdel.blog.rank.application;

import com.gongdel.blog.rank.domain.KeywordCount;
import com.gongdel.blog.rank.domain.Rank;
import com.gongdel.blog.rank.infrastructure.RankRepository;
import com.gongdel.blog.search.domain.event.SearchKeyword;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankService {

  private final RankRepository rankRepository;

  @Transactional
  public void createSearchKeywordHistory(SearchKeyword searchKeyword) {
    Optional<Rank> rankEntity = rankRepository
        .findByKeyword(searchKeyword.getKeyword());

    if (rankEntity.isEmpty()) {
      rankRepository.save(Rank.create(searchKeyword.getKeyword()));
      return;
    }

    Rank rank = rankEntity.get();
    rank.increaseCount();
  }

  @Transactional(readOnly = true)
  public List<KeywordCount> findTop10OrderByCount() {
    return rankRepository.findTop10ByOrderByCountDesc().stream().map(KeywordCount::of).collect(
        Collectors.toList());
  }
}
