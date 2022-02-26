package homework.jelee.categoryapi.web.dto;

import homework.jelee.categoryapi.domain.category.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@ApiModel(description = "카테고리 등록 Request DTO")
@NoArgsConstructor
@Getter @Setter
public class CategoryCreateRequest {

    @ApiModelProperty(value = "카테고리 이름", required = true, example = "아우터")
    @NotEmpty
    private String categoryName;

    @ApiModelProperty(value = "상위 카테고리 아이디", example = "1")
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
