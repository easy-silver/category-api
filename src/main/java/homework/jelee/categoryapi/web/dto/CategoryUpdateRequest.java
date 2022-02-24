package homework.jelee.categoryapi.web.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
public class CategoryUpdateRequest {

    @NotEmpty
    private String categoryName;

    @Min(1)
    private Long parentId;
}
