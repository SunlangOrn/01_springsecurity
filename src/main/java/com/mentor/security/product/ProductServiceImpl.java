package com.mentor.security.product;

import com.mentor.security.category.CategoryRepository;
import com.mentor.security.shared.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public PageResponse<ProductResponseDetail> list(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByDeletedAtIsNull(pageable);
        return PageResponse.of(productPage, productMapper::toProductResponseDetail);
    }

    @Override
    public ProductResponseDetail create(ProductRequest request, String sku , String name) {

        //1 . L01 , 2 . LO1

        Product newProduct = productMapper.toEntity(request);

        if(productRepository.existsBySku(sku)){
            throw new ResponseStatusException(HttpStatus.CONFLICT , "Sku is already exits");
        }
        if(productRepository.existsByNameIgnoreCase(name)){
            throw new ResponseStatusException(HttpStatus.CONFLICT ,"product name already exits");
        }

        Product savedProduct = productRepository.save(newProduct);
        return productMapper.toProductResponseDetail(savedProduct);
    }

    @Override
    public ProductResponseDetail getById(Long id) {
        Product data = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Product not found id : " + id));
        return productMapper.toProductResponseDetail(data);
    }
}
