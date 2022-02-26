package homework.jelee.categoryapi.service;

import homework.jelee.categoryapi.domain.category.Category;
import homework.jelee.categoryapi.domain.category.CategoryRepository;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryDto;
import homework.jelee.categoryapi.web.dto.CategoryUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Service Layer Unit Test
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("최상위 카테고리 등록")
    void createRootCategoryOK() {
        //given
        CategoryCreateRequest request = new CategoryCreateRequest("상의");
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

    @Test
    @DisplayName("하위 카테고리 등록")
    void createSubCategoryOK() {
        //given
        Category parentCategory = Category.builder()
                .name("상의")
                .build();
        Long parentId = 1L;
        ReflectionTestUtils.setField(parentCategory, "id", parentId);

        CategoryCreateRequest request = new CategoryCreateRequest("반팔티셔츠", parentId);
        Category subCategory = request.toEntity();
        Long fakeId = 2L;
        ReflectionTestUtils.setField(subCategory, "id", fakeId);

        //mocking
        given(categoryRepository.findById(parentId))
                .willReturn(Optional.of(parentCategory));
        given(categoryRepository.save(Mockito.any()))
                .willReturn(subCategory);
        given(categoryRepository.findById(fakeId))
                .willReturn(Optional.of(subCategory));

        //when
        Long categoryId = categoryService.createCategory(request);

        //then
        Category findCategory = categoryRepository.findById(fakeId).get();
        assertThat(categoryId).isEqualTo(subCategory.getId());
        assertThat(findCategory.getName()).isEqualTo(subCategory.getName());
    }

    @Test
    @DisplayName("하위 카테고리 등록 시 상위 카테고리 아이디가 유효하지 않을 경우 EntityNotFountException이 발생한다.")
    void createSubCategoryFail() {
        //given
        Long parentId = 999L;
        CategoryCreateRequest request = new CategoryCreateRequest("반팔티셔츠", parentId);

        //mocking
        given(categoryRepository.findById(parentId))
                .willReturn(Optional.empty());

        //when, then
        assertThrows(EntityNotFoundException.class, () -> categoryService.createCategory(request));
    }

    @Test
    @DisplayName("전체 카테고리 조회")
    void getCategories() {
        //given
        Category category = Category.builder()
                .name("하의")
                .build();
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        //mocking
        given(categoryRepository.findAllByParentIsNull())
                .willReturn(categories);

        //when
        Long parentId = null;
        List<CategoryDto> categoryDtos = categoryService.getCategories(parentId);

        //then
        assertThat(categoryDtos.size()).isEqualTo(1);
        assertThat(categoryDtos.get(0).getCategoryName()).isEqualTo(category.getName());
    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() {
        //give
        Category category = Category.builder()
                .name("겉옷")
                .build();

        Long categoryId = 1L;
        ReflectionTestUtils.setField(category, "id", categoryId);

        CategoryUpdateRequest request = new CategoryUpdateRequest();
        String newName = "아우터";
        request.setCategoryName(newName);

        //mocking
        given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(category));

        //when
        categoryService.updateCategory(categoryId, request);

        //then
        Category findCategory = categoryRepository.findById(categoryId).get();
        assertThat(findCategory.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() {
        //given
        Category category = Category.builder()
                .name("액세서리")
                .build();
        Long categoryId = 1L;
        ReflectionTestUtils.setField(category, "id", categoryId);

        //mocking
        given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(category));

        //when
        categoryService.deleteCategory(categoryId);

        //then
        verify(categoryRepository, times(1)).delete(any());
    }

}