package com.mentor.security.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryIds", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductResponseDetail toProductResponseDetail(Product product);

    @Mapping(target = "category.id", source = "categoryIds")
    Product toEntity(ProductRequest request);
}
