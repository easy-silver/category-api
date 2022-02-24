package homework.jelee.categoryapi.web.dto;

import homework.jelee.categoryapi.domain.category.Category;
import lombok.Getter;

/**
 * 카테고리 목록 조회에 대한 결과 DTO
 */
@Getter
public class CategoryListQueryResult {

    private Long categoryId;

    private String categoryName;

    public CategoryListQueryResult(Category entity) {
        this.categoryId = entity.getId();
        this.categoryName = entity.getName();
    }
}
