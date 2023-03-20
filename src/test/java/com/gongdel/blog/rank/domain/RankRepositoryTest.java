package com.gongdel.blog.rank.domain;

import com.gongdel.blog.rank.infrastructure.RankRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RankRepositoryTest {

  @Autowired
  RankRepository target;

  @Test
  void findByKeyword() {
    // Given
    String expectKey = "캠핑";
    Rank rank = Rank.create(expectKey);
    target.save(rank);

    // When
    Rank findRank = target.findByKeyword(expectKey).get();

    // Then
    Assertions.assertThat(findRank.getKeyword()).isEqualTo(expectKey);
    Assertions.assertThat(findRank.getCount()).isEqualTo(1);
  }

  @Test
  @DisplayName("조회가 많은 키워드별로 정렬 ")
  void findTop10ByKeywordOrderByCount() {
    // Given
    int top1RankIndex = 4;
    List<Rank> ranks = IntStream.range(0, 10)
        .mapToObj(value -> Rank.create("캠핑" + value)).collect(Collectors.toList());

    ranks.get(top1RankIndex).increaseCount();

    target.saveAll(ranks);

    // When
    List<Rank> top10OrderByCount = target.findTop10ByOrderByCountDesc();

    // Then
    Assertions.assertThat(top10OrderByCount).hasSize(10);
    Assertions.assertThat(top10OrderByCount.get(0).getKeyword()).isEqualTo("캠핑" + top1RankIndex);
  }
}