package com.mentor.security.product;

import com.mentor.security.shared.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public PageResponse<ProductResponseDetail> list(
            @PageableDefault(size = 10) Pageable pageable
    ){
        PageResponse<ProductResponseDetail> response = productService.list(pageable);
        return response;
    }
}
