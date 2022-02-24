package homework.jelee.categoryapi.web.dto;

import homework.jelee.categoryapi.domain.category.Category;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/**
 * 카테고리 등록 Request DTO
 */
@Getter
public class CategoryCreateRequest {

    //카테고리 이름
    @NotEmpty
    private String categoryName;

    //상위 카테고리 ID
    private Long parentId;

    public Category toEntity() {
        return Category.builder()
                .name(categoryName)
                .build();
    }

}
