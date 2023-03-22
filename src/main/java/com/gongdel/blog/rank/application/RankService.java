package com.gongdel.blog.rank.application;

import com.gongdel.blog.rank.domain.KeywordCount;
import com.gongdel.blog.rank.domain.Rank;
import com.gongdel.blog.rank.infrastructure.RankRepository;
import com.gongdel.blog.search.domain.event.SearchKeyword;
import java.util.List;
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
    rankRepository.findByKeywordWithPessimisticLock(searchKeyword.getKeyword())
        .ifPresentOrElse(
            Rank::increaseCount,
            () -> rankRepository.save(Rank.create(searchKeyword.getKeyword()))
        );
  }

  @Transactional(readOnly = true)
  public List<KeywordCount> findTop10OrderByCount() {
    return rankRepository.findTop10ByOrderByCountDesc().stream().map(KeywordCount::of).collect(
        Collectors.toList());
  }
}
