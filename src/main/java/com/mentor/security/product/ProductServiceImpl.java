package com.mentor.security.product;

import com.mentor.security.category.CategoryRepository;
import com.mentor.security.shared.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
