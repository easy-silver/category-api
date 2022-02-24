package homework.jelee.categoryapi.web.controller;

import homework.jelee.categoryapi.service.CategoryService;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryListResponse;
import homework.jelee.categoryapi.web.dto.CategoryUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public Long createCategory(@Valid @RequestBody CategoryCreateRequest request) {

        return service.createCategory(request);
    }

    /**
     * 카테고리 목록 조회
     */
    @GetMapping("")
    public CategoryListResponse getCategories(@RequestParam(value = "categoryId", required = false) Long categoryId) {

        return new CategoryListResponse(service.getCategories(categoryId));
    }

    /**
     * 카테고리 수정
     */
    @PutMapping("/{categoryId}")
    public void updateCategory(@PathVariable Long categoryId,
                               @Valid @RequestBody CategoryUpdateRequest request) {

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
