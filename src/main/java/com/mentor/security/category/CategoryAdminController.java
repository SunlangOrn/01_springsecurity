package com.mentor.security.category;

import com.mentor.security.category.dto.CategoryRequest;
import com.mentor.security.category.dto.CategoryResponseDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.http.converter.autoconfigure.ServerHttpMessageConvertersCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;
    private final ServerHttpMessageConvertersCustomizer serverConvertersCustomizer;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CategoryResponseDetail get(@PathVariable Long id){
        return categoryService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CategoryResponseDetail create(@Valid@RequestBody CategoryRequest categoryRequest) {
        return categoryService.create(categoryRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDetail update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PatchMapping("/{id}/active")
    @ResponseStatus(HttpStatus.OK)
    public void active(@PathVariable Long id) {
        categoryService.active(id);
    }

    @PatchMapping("/{id}/inactive")
    @ResponseStatus(HttpStatus.OK)
    public void inactive(@PathVariable Long id) {
        categoryService.inactive(id);
    }
}
