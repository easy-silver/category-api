package homework.jelee.categoryapi.web.dto;

import homework.jelee.categoryapi.domain.category.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 카테고리 등록 Request DTO
 */
@Getter @Setter
public class CategoryCreateRequest {

    //카테고리 이름
    @NotEmpty
    private String categoryName;

    //상위 카테고리 ID
    @Min(1)
    private Long parentId;

    public Category toEntity() {
        return Category.builder()
                .name(categoryName)
                .build();
    }

    public CategoryCreateRequest(@NotEmpty String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryCreateRequest(@NotEmpty String categoryName, @Min(1) Long parentId) {
        this.categoryName = categoryName;
        this.parentId = parentId;
    }
}
