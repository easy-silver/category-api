package homework.jelee.categoryapi.service;

import homework.jelee.categoryapi.domain.category.Category;
import homework.jelee.categoryapi.domain.category.CategoryRepository;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository repository;

    /**
     * 카테고리 등록
     */
    public Long createCategory(CategoryCreateRequest request) {
        Category category = request.toEntity();
        Long parentCategoryId = request.getParentCategoryId();

        if (parentCategoryId != null) {
            Category parent = repository.findById(parentCategoryId)
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 부모 카테고리 ID=" + parentCategoryId));
            category.setParent(parent);
        }

        return repository.save(category).getId();
    }

}
