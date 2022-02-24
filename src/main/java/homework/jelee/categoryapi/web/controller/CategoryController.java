package homework.jelee.categoryapi.web.controller;

import homework.jelee.categoryapi.service.CategoryService;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryListResponse;
import homework.jelee.categoryapi.web.dto.CategoryUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {

    private final CategoryService service;

    /**
     * 카테고리 등록
     * 성공 시 204 코드와 생성된 ID 반환
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Long createCategory(@RequestBody CategoryCreateRequest request) {

        return service.createCategory(request);
    }

    /**
     * 카테고리 목록 조회
     * 상위 카테고리 아이디가 없다면 전체 조회,
     * 있다면 해당 카테고리의 하위 카테고리만 조회
     */
    @GetMapping("")
    public CategoryListResponse getCategories(@RequestParam(value = "parentId", required = false) Long parentId) {
        if (parentId == null) {
            return service.getCategories();
        }
        return service.getCategoriesByParent(parentId);
    }

    /**
     * 카테고리 수정
     */
    @PutMapping("/{categoryId}")
    public void updateCategory(@PathVariable Long categoryId,
                               @RequestBody CategoryUpdateRequest request) {

        service.updateCategory(categoryId, request);
    }

    /**
     * 카테고리 삭제
     */
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {

        service.deleteCategory(categoryId);
    }
}
