package homework.jelee.categoryapi.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class CategoryUpdateRequest {

    @NotEmpty
    private String categoryName;

    @Min(1)
    private Long parentId;
}
