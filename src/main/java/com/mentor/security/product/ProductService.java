package com.mentor.security.product;

import com.mentor.security.shared.PageResponse;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    //Paging and sorting
    PageResponse<ProductResponseDetail> list(Pageable pageable);

    ProductResponseDetail getById(Long id);

    ProductResponseDetail create(ProductRequest request);

    ProductResponseDetail update(Long id, ProductUpdateRequest request);

    void active(String sku);

    void inactive(String sku);

    void delete(String sku);

    PageResponse<ProductResponse> listUserSide(ProductStatus status ,Pageable pageable);

    ProductResponse getById(ProductStatus status, Long id);
}
