package homework.jelee.categoryapi.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class CategoryUpdateRequest {

    @ApiModelProperty(value = "카테고리 이름", required = true, example = "긴팔 셔츠")
    @NotEmpty
    private String categoryName;

    @ApiModelProperty(value = "상위 카테고리 아이디", example = "1")
    @Min(1)
    private Long parentId;
}
