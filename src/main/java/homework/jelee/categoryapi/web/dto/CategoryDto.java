package homework.jelee.categoryapi.web.dto;

import homework.jelee.categoryapi.domain.category.Category;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 카테고리 목록 조회에 대한 결과 DTO
 */
@Getter
public class CategoryDto {

    private Long categoryId;

    private String categoryName;

    private Long parentId;

    private List<CategoryDto> children;

    public CategoryDto(Category entity) {
        categoryId = entity.getId();
        categoryName = entity.getName();
        parentId = entity.getParent() != null ? entity.getParent().getId() : null;
        children = entity.getChildren().stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
    }
}
