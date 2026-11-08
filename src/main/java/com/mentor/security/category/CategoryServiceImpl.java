package com.mentor.security.category;

import com.mentor.security.category.dto.CategoryRequest;
import com.mentor.security.category.dto.CategoryResponse;
import com.mentor.security.category.dto.CategoryResponseDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse getCategoryPublic(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponseDetail create(CategoryRequest request) {
        String name = request.name().toLowerCase().trim();

        if (categoryRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
        }

        //get from request to map to entity for save into db
        Category entity = categoryMapper.toEntity(request);
        categoryRepository.save(entity);

        return categoryMapper.toResponseDetail(entity);
    }

    @Override
    public CategoryResponseDetail getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));
        return categoryMapper.toResponseDetail(category);
    }

    @Override
    @Transactional
    public CategoryResponseDetail update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));

        category.setName(request.name());
        if (request.status() != null) {
            CategoryStatus status = category.getStatus();
        }

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponseDetail(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));

        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public void active(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));
        category.setStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(category);

    }

    @Override
    @Transactional
    public void inactive(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));
        category.setStatus(CategoryStatus.INACTIVE);
        categoryRepository.save(category);

    }

}
