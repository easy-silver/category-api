package homework.jelee.categoryapi.service;

import homework.jelee.categoryapi.domain.category.Category;
import homework.jelee.categoryapi.domain.category.CategoryRepository;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryListQueryResult;
import homework.jelee.categoryapi.web.dto.CategoryUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = {"categoryCache"})
@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository repository;

    /**
     * 카테고리 등록
     */
    @CacheEvict(allEntries = true)
    public Long createCategory(CategoryCreateRequest request) {
        Category category = request.toEntity();
        setParentCategory(category, request.getParentId());

        return repository.save(category).getId();
    }

    /**
     * 카테고리 목록 조회
     */
    @Cacheable(value = "categoryCache")
    @Transactional(readOnly = true)
    public List<CategoryListQueryResult> getCategories(Long categoryId) {
        if (categoryId == null) {
            return getAllCategories();
        }

        Category parent = getCategoryEntity(categoryId);
        return getSubCategories(parent);
    }

    //루트 카테고리를 조회하며 전체 카테고리 조회
    private List<CategoryListQueryResult> getAllCategories() {
        return repository.findAllByParentIsNull()
                .stream()
                .map(CategoryListQueryResult::new)
                .collect(Collectors.toList());
    }

    //해당 카테고리의 하위 카테고리 조회
    private List<CategoryListQueryResult> getSubCategories(Category parent) {
        return repository.findAllByParent(parent)
                .stream()
                .map(CategoryListQueryResult::new)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리 수정
     */
    @CacheEvict(allEntries = true)
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {
        Category category = getCategoryEntity(categoryId);

        category.changeName(request.getCategoryName());
        setParentCategory(category, request.getParentId());
    }

    /**
     * 카테고리 삭제
     */
    @CacheEvict(allEntries = true)
    public void deleteCategory(Long categoryId) {
        Category category = getCategoryEntity(categoryId);

        repository.delete(category);
    }

    /**
     * 카테고리 ID로 카테고리 엔티티 조회
     */
    private Category getCategoryEntity(Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리 ID=" + categoryId));
    }

    /**
     * 상위 카테고리 등록
     */
    private void setParentCategory(Category category, Long parentId) {
        if (parentId != null) {
            Category parent = getCategoryEntity(parentId);
            category.changeParent(parent);
        }
    }

}
