package homework.jelee.categoryapi.service;

import homework.jelee.categoryapi.domain.category.Category;
import homework.jelee.categoryapi.domain.category.CategoryRepository;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryListQueryResult;
import homework.jelee.categoryapi.web.dto.CategoryListResponse;
import homework.jelee.categoryapi.web.dto.CategoryUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
        setParentCategory(category, request.getParentId());

        return repository.save(category).getId();
    }

    /**
     * 전체 카테고리 목록 조회
     * @param parentId
     */
    public CategoryListResponse getCategories(Long parentId) {

        //ToDo. Depth 구조로 포맷해서 반환하도록 처리 필요

        //전체 카테고리 목록 조회
        if (parentId == null) {
            List<CategoryListQueryResult> categories = findAllCategories();
            return new CategoryListResponse(categories.size(), categories);
        }

        //하위 카테고리 목록 조회
        List<CategoryListQueryResult> subCategories = findSubCategories(parentId);
        return new CategoryListResponse(subCategories.size(), subCategories);
    }

    /**
     * 전체 카테고리 엔티티 DTO 리스트로 변환
     */
    private List<CategoryListQueryResult> findAllCategories() {
        return repository.findAll()
                .stream()
                .map(CategoryListQueryResult::new)
                .collect(Collectors.toList());
    }

    /**
     * 하위 카테고리 엔티티 DTO 리스트로 변환
     */
    private List<CategoryListQueryResult> findSubCategories(Long parentId) {
        Category parent = getCategoryEntity(parentId);

        return repository.findAllByParent(parent)
                .stream()
                .map(CategoryListQueryResult::new)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리 수정
     */
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {
        Category category = getCategoryEntity(categoryId);

        category.changeName(request.getCategoryName());
        setParentCategory(category, request.getParentId());
    }

    /**
     * 카테고리 삭제
     */
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
            category.setParent(parent);
        }
    }

}
