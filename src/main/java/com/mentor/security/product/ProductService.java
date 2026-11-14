package com.mentor.security.product;

import com.mentor.security.shared.PageResponse;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    //Paging and sorting
    PageResponse<ProductResponseDetail> list(Pageable pageable);

    ProductResponseDetail getById(Long id);

    ProductResponseDetail create(ProductRequest request, String sku , String name);

    ProductResponseDetail update(Long id, ProductUpdateRequest request);
}
