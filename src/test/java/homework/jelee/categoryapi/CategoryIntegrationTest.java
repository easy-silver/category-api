package homework.jelee.categoryapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.jelee.categoryapi.domain.category.Category;
import homework.jelee.categoryapi.domain.category.CategoryRepository;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 스프링 컨테이너를 이용한 통합 테스트
 */
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryRepository categoryRepository;
    final String URI = "/categories";

    @Test
    @DisplayName("최상위 카테고리 등록")
    void crateCategoryOK() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest("아우터");

        mvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 등록 시 카테고리 이름이 비어있으면 400 상태코드를 반환한다.")
    void crateCategoryFail() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest("");

        mvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 등록 시 상위 카테고리 ID가 유효하지 않은 경우 404 상태코드를 반환한다.")
    void crateCategoryNotFoundParent() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest("반팔티셔츠", 1L);

        mvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("전체 카테고리 조회")
    void getCategories() throws Exception {
        //given
        categoryRepository.save(Category.builder()
                .name("아우터")
                .build());

        //when, then
        mvc.perform(get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories").exists())
                .andExpect(jsonPath("$.categories.length()", is(1)))
                .andExpect(jsonPath("$.categories[0].categoryName", is("아우터")))
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() throws Exception {
        //given
        Long categoryId = categoryRepository.save(Category.builder()
                .name("겉옷")
                .build()).getId();
        CategoryUpdateRequest request = new CategoryUpdateRequest();
        String newCategoryName = "아우터";
        request.setCategoryName(newCategoryName);

        //when
        mvc.perform(put(URI + "/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        Category findCategory = categoryRepository.findById(categoryId).get();
        assertThat(findCategory.getName()).isEqualTo(newCategoryName);
    }

    @Test
    @DisplayName("카테고리 수정 시 필수 값이 없을 경우 400 상태 코드를 반환한다.")
    void updateCategoryBadRequest() throws Exception {
        //given(name null)
        CategoryUpdateRequest request = new CategoryUpdateRequest();

        //when, then
        mvc.perform(put(URI + "/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("카테고리 수정 시 유효하지 않은 카테고리 아이디일 경우에는 404 상태 코드를 반환한다.")
    void updateCategoryNotFound() throws Exception {
        //given
        CategoryUpdateRequest request = new CategoryUpdateRequest();
        String newCategoryName = "아우터";
        request.setCategoryName(newCategoryName);

        //when, then
        mvc.perform(put(URI + "/" + 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() throws Exception {
        //given
        Long categoryId = categoryRepository.save(Category.builder()
                .name("액세서리")
                .build()).getId();

        //when
        mvc.perform(delete(URI + "/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        assertThat(categoryRepository.count()).isEqualTo(0);
    }

}
