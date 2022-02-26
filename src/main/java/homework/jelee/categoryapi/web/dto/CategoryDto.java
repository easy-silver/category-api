package homework.jelee.categoryapi.web.dto;

import homework.jelee.categoryapi.domain.category.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 카테고리 목록 조회에 대한 결과 DTO
 */
@ApiModel(description = "카테고리 정보")
@Getter
public class CategoryDto {

    @ApiModelProperty(position = 1, value = "카테고리 아이디", required = true)
    private Long categoryId;

    @ApiModelProperty(position = 2, value = "카테고리 이름", required = true)
    private String categoryName;

    @ApiModelProperty(position = 3, value = "상위 카테고리 아이디")
    private Long parentId;

    @ApiModelProperty(position = 4, value = "하위 카테고리 리스트")
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
