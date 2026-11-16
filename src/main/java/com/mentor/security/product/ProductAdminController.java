package com.mentor.security.product;

import com.mentor.security.shared.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    @GetMapping
    public PageResponse<ProductResponseDetail> list(
            @PageableDefault(size = 10) Pageable pageable
    ){
        PageResponse<ProductResponseDetail> response = productService.list(pageable);
        return response;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDetail create(@RequestBody ProductRequest request, String sku , String name){
        return productService.create(request, sku , name);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDetail getById(@PathVariable Long id){
        return productService.getById(id);
    }

    @PutMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDetail update(@RequestBody @Valid Long id, ProductUpdateRequest request){
        return productService.update(id , request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{sku}/active")
    void active (@PathVariable String sku){
        productService.active(sku);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{sku}/inactive")
    void inactive (@PathVariable String sku){
        productService.inactive(sku);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{sku}")
    void delete (@PathVariable String sku){
        productService.delete(sku);
    }
}
