package homework.jelee.categoryapi;

import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

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
    
    @Test
    @DisplayName("최상위 카테고리 등록")
    void createCategory() {
        //given
        CategoryCreateRequest request = new CategoryCreateRequest("상의");

        //when
        Long categoryId = testRestTemplate
                .postForObject("/categories", request, Long.class);

        //then
        assertThat(categoryId).isEqualTo(1L);
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
