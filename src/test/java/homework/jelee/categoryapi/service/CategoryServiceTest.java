package homework.jelee.categoryapi.service;

import homework.jelee.categoryapi.domain.category.Category;
import homework.jelee.categoryapi.domain.category.CategoryRepository;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("최상위 카테고리 등록")
    void createRootCategory() {
        //given
        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setCategoryName("상의");
        Category categoryEntity = request.toEntity();

        Long fakeCategoryId = 1L;
        ReflectionTestUtils.setField(categoryEntity, "id", fakeCategoryId);

        //mocking
        given(categoryRepository.save(Mockito.any()))
                .willReturn(categoryEntity);
        given(categoryRepository.findById(fakeCategoryId))
                .willReturn(Optional.of(categoryEntity));

        //when
        Long newCategoryId = categoryService.createCategory(request);

        //then
        Category findCategory = categoryRepository.findById(newCategoryId).get();

        assertThat(findCategory.getName()).isEqualTo(categoryEntity.getName());
        assertThat(findCategory.getId()).isEqualTo(categoryEntity.getId());
    }

}