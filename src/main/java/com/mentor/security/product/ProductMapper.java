package com.mentor.security.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryIds", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "sku",  source = "sku")
    ProductResponseDetail toProductResponseDetail(Product product);

    ProductResponse toResponse(Product product);

    @Mapping(target = "category.id", source = "categoryIds")
    @Mapping(target = "sku", source = "sku")
    Product toEntity(ProductRequest request);
}
