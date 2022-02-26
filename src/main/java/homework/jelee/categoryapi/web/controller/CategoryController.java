package homework.jelee.categoryapi.web.controller;

import homework.jelee.categoryapi.service.CategoryService;
import homework.jelee.categoryapi.web.dto.CategoryCreateRequest;
import homework.jelee.categoryapi.web.dto.CategoryListResponse;
import homework.jelee.categoryapi.web.dto.CategoryUpdateRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "카테고리 신규 등록", notes = "새 카테고리를 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Long createCategory(@Valid @RequestBody CategoryCreateRequest request) {

        return service.createCategory(request);
    }

    /**
     * 카테고리 목록 조회
     */
    @ApiOperation(value = "카테고리 목록 조회", notes = "카테고리 목록을 조회합니다.\n 아이디가 있을 경우 해당 아이디의 하위 카테고리를 조회합니다.")
    @GetMapping("")
    public CategoryListResponse getCategories(
            @ApiParam(value = "카테고리 아이디", example = "1")
            @RequestParam(value = "categoryId", required = false) Long categoryId) {

        return new CategoryListResponse(service.getCategories(categoryId));
    }

    /**
     * 카테고리 수정
     */
    @ApiOperation(value = "카테고리 수정", notes = "카테고리 정보를 수정합니다.")
    @PutMapping("/{categoryId}")
    public void updateCategory(
            @ApiParam(value = "카테고리 아이디", required = true, example = "1")
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryUpdateRequest request) {

        service.updateCategory(categoryId, request);
    }

    /**
     * 카테고리 삭제
     */
    @ApiOperation(value = "카테고리 삭제", notes = "해당 아이디의 카테고리와 하위 카테고리를 전부 삭제합니다.")
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(
            @ApiParam(value = "카테고리 아이디", required = true, example = "1")
            @PathVariable Long categoryId) {

        service.deleteCategory(categoryId);
    }
}
