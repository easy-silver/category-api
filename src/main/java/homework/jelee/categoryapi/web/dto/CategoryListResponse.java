package homework.jelee.categoryapi.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryListResponse {

    private long totalCount;

    private List<CategoryListQueryResult> categories;

    public CategoryListResponse(long totalCount, List<CategoryListQueryResult> categories) {
        this.totalCount = totalCount;
        this.categories = categories;
    }
}
