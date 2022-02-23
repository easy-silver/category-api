package homework.jelee.categoryapi.web.dto;

import lombok.Getter;

@Getter
public class CategoryUpdateRequest {

    private String categoryName;

    private Long parentId;
}
