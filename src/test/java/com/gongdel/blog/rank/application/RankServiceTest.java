package com.gongdel.blog.rank.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.gongdel.blog.rank.domain.KeywordCount;
import com.gongdel.blog.rank.domain.Rank;
import com.gongdel.blog.rank.infrastructure.RankRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RankServiceTest {

  @Mock
  private RankRepository rankRepository;

  @InjectMocks
  private RankService target;

  @Test
  void findTop10OrderByCount() {
    // Given
    Rank rank = Rank.builder()
        .keyword("캠핑")
        .count(10)
        .build();

    List<Rank> rankList = List.of(rank);
    when(rankRepository.findTop10ByOrderByCountDesc()).thenReturn(rankList);

    // When
    List<KeywordCount> keywordCounts = target.findTop10OrderByCount();

    // Then
    assertThat(keywordCounts.size()).isEqualTo(rankList.size());
    assertThat(keywordCounts.get(0).getKeyword()).isEqualTo(rankList.get(0).getKeyword());
    assertThat(keywordCounts.get(0).getCount()).isEqualTo(rankList.get(0).getCount());
  }
}