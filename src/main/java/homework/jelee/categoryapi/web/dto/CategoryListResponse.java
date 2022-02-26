package homework.jelee.categoryapi.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "카테고리 목록 조회 ResponseDTO")
@NoArgsConstructor
@Getter
public class CategoryListResponse {

    @ApiModelProperty(value = "카테고리 리스트", required = true)
    private List<CategoryDto> categories;

    public CategoryListResponse(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
