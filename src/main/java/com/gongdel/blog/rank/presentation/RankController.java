package com.gongdel.blog.rank.presentation;

import com.gongdel.blog.common.dto.CommonResponse;
import com.gongdel.blog.rank.application.RankService;
import com.gongdel.blog.rank.domain.KeywordCount;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ranks")
@RequiredArgsConstructor
public class RankController {

  private final RankService rankService;

  @GetMapping
  public CommonResponse<List<KeywordCount>> findTop10Keyword() {
    return CommonResponse.success(rankService.findTop10OrderByCount());
  }
}
