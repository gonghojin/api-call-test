package com.gongdel.blog.search.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gongdel.blog.search.application.SearchService;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Info.Context;
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
@WebMvcTest(SearchController.class)
class SearchControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SearchService searchService;

  @Test
  void search() throws Exception {
    Context context = Context.builder()
        .title("제목")
        .contents("내용")
        .url("URL")
        .blogName("이름")
        .thumbNail("미리보기")
        .dateTime("2023-03-20T17:17:30.000+09:00")
        .build();
    Info info = Info.builder()
        .context(List.of(context))
        .pageSize(1)
        .totalCount(10)
        .build();

    when(searchService.search(any())).thenReturn(info);

    mockMvc.perform(get("/v1/search")
        .param("keyword", "캠핑")
        .param("page", "1")
        .param("sort", "accuracy")
        .param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data.content[0].title").value(context.getTitle()))
        .andExpect(jsonPath("$.data.content[0].contents").value(context.getContents()))
        .andExpect(jsonPath("$.data.content[0].url").value(context.getUrl()))
        .andExpect(jsonPath("$.data.content[0].blogName").value(context.getBlogName()))
        .andExpect(jsonPath("$.data.content[0].thumbNail").value(context.getThumbNail()))
        .andExpect(jsonPath("$.data.content[0].dateTime").value(context.getDateTime()));
  }
}