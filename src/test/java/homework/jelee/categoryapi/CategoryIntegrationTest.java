package homework.jelee.categoryapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 스프링 컨테이너를 이용한 통합 테스트
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("최상위 카테고리 등록")
    void crateCategoryOK() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest("아우터");

        mvc.perform(MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("전체 카테고리 조회")
    void getCategories() {
        //when
        CategoryListResponse response = testRestTemplate
                .getForObject("/categories", CategoryListResponse.class);

        //then
        assertThat(response.getCategories().size()).isEqualTo(0);

    }
}
