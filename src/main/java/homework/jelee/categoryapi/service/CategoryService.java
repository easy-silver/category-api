package homework.jelee.categoryapi.service;

import homework.jelee.categoryapi.domain.category.Category;
import homework.jelee.categoryapi.domain.category.CategoryRepository;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryUpdateRequest;
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
        Long parentId = request.getParentId();

        if (parentId != null) {
            Category parent = repository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 부모 카테고리 ID=" + parentId));
            category.setParent(parent);
        }

        return repository.save(category).getId();
    }

    /**
     * 카테고리 수정
     */
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리 ID=" + categoryId));

        category.changeName(request.getCategoryName());
        Long parentId = request.getParentId();

        if (parentId != null) {
            Category parent = repository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 부모 카테고리 ID=" + parentId));
            category.setParent(parent);
        }
    }

    /**
     * 카테고리 삭제
     */
    public void deleteCategory(Long categoryId) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 ID=" + categoryId));

        repository.delete(category);
    }
}
