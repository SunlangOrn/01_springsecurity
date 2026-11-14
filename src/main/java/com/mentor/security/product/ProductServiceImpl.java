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
    public ProductResponseDetail getById(Long id) {
        Product data = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Product not found id : " + id));
        return productMapper.toProductResponseDetail(data);
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
    @Transactional
    public ProductResponseDetail update(Long id, ProductUpdateRequest request){
        /*
           1.Product olddata = findById
           2
        * */
        Product oldData = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "product not found"));

        if (request.sku() != null ){
            if(productRepository.existsBySkuAndIdNotAndDeletedAtIsNull(request.sku(), id)){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "product sku already exits");
            }
            oldData.setSku(request.sku());
        }
        if (request.name() != null ) oldData.setName(request.name());
        if (request.price() != null) oldData.setPrice(request.price());
        if (request.stock() != null) oldData.setStock(request.stock());
        if (request.status() != null) oldData.setStatus(ProductStatus.valueOf(request.status()));
        return productMapper.toProductResponseDetail(oldData);
    }


}
