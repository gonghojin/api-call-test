package com.gongdel.blog.rank.domain;

import com.gongdel.blog.common.dto.exception.InvalidParamException;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Entity
@Table(indexes = {
    @Index(name = "idx_keyword", columnList = "keyword")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Rank {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, updatable = false)
  private String keyword;

  @Column(columnDefinition = "integer default 1")
  private Integer count;

  @Column(updatable = false)
  private LocalDateTime createdDate;

  private Rank(String keyword, Integer count) {
    if (!StringUtils.hasText(keyword)) {
      throw new InvalidParamException("Rank.keyword is null");
    }

    if (count == null) {
      throw new InvalidParamException("Rank.count is null");
    }

    this.keyword = keyword;
    this.count = count;
  }

  public static Rank create(String keyword) {
    return new Rank(keyword, 1);
  }

  public void increaseCount() {
    this.count = this.count + 1;
  }

  @PrePersist
  protected void prePersist() {
    this.createdDate = LocalDateTime.now();
  }
}
