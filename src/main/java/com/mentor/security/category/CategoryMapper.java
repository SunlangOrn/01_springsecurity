package com.mentor.security.category;

import com.mentor.security.category.dto.CategoryRequest;
import com.mentor.security.category.dto.CategoryResponse;
import com.mentor.security.category.dto.CategoryResponseDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponseDetail toResponseDetail(Category category);

    CategoryResponse toResponse(Category category);

}
