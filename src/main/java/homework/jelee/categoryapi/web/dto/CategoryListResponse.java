package homework.jelee.categoryapi.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CategoryListResponse {

    private List<CategoryDto> categories;

    public CategoryListResponse(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
