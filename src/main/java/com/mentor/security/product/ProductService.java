package com.mentor.security.product;

import com.mentor.security.shared.PageResponse;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    //Paging and sorting
    PageResponse<ProductResponseDetail> list(Pageable pageable);
}
