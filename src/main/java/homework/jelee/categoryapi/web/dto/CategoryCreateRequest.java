package homework.jelee.categoryapi.web.dto;

import homework.jelee.categoryapi.domain.category.Category;
import lombok.Getter;

/**
 * 카테고리 등록 Request DTO
 */
@Getter
public class CategoryCreateRequest {

    //카테고리 이름
    private String categoryName;

    //상위 카테고리 ID
    private Long parentId;

    public Category toEntity() {
        return Category.builder()
                .name(categoryName)
                .build();
    }

}
