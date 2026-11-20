package com.mentor.security.category;

import com.mentor.security.category.dto.CategoryRequest;
import com.mentor.security.category.dto.CategoryResponseDetail;

public interface CategoryService {

    //create
    CategoryResponseDetail create(CategoryRequest request);

    void delete(Long id);

    CategoryResponseDetail update(Long id, CategoryRequest request);

    void active(Long id);

    void inactive(Long id);

}
