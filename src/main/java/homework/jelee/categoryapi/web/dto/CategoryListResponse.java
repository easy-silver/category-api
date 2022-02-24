package homework.jelee.categoryapi.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryListResponse {

    private final List<CategoryDto> categories;

    public CategoryListResponse(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
