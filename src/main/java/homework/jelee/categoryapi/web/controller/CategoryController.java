package homework.jelee.categoryapi.web.controller;

import homework.jelee.categoryapi.service.CategoryService;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
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

    @PutMapping("/{categoryId}")
    public void updateCategory(@PathVariable Long categoryId,
                               @RequestBody CategoryUpdateRequest request) {

        service.updateCategory(categoryId, request);
    }
}
