package com.mentor.security.product;

import com.mentor.security.shared.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductUserController {

    private final ProductService productService;

    @GetMapping
    public PageResponse<ProductResponse> list(
            ProductStatus status,
            @PageableDefault(size = 10) Pageable pageable
    ){
        PageResponse<ProductResponse> response = productService.listUserSide(status ,pageable);
        return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getById(@PathVariable Long id, ProductStatus status){
        return productService.getById(status ,id);
    }
}
