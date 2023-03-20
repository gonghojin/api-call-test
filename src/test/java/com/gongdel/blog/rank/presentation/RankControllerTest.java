package com.gongdel.blog.rank.presentation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gongdel.blog.rank.application.RankService;
import com.gongdel.blog.rank.domain.KeywordCount;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RankController.class)
class RankControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RankService rankService;

  @Test
  void search() throws Exception {
    KeywordCount keywordCount = KeywordCount.builder()
        .count(10)
        .keyword("캠핑").build();
    when(rankService.findTop10OrderByCount()).thenReturn(List.of(keywordCount));

    mockMvc.perform(get("/v1/ranks"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data[0].keyword").value(keywordCount.getKeyword()))
        .andExpect(jsonPath("$.data[0].count").value(keywordCount.getCount()));
  }
}