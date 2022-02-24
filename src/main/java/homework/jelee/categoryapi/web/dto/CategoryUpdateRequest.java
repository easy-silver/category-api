package homework.jelee.categoryapi.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CategoryUpdateRequest {

    @NotEmpty
    private String categoryName;

    private Long parentId;
}
