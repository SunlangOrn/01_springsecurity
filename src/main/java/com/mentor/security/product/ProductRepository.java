package com.mentor.security.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long > {

    Page<Product> findAllByDeletedAtIsNull(Pageable pageable);

    Optional<Product> findByIdAndDeletedAtIsNull(Long id);

    Optional<Product> findBySkuAndDeletedAtIsNull(String sku);

    boolean existsBySku(String sku);

    boolean existsByNameIgnoreCase(String name);

    boolean existsBySkuAndIdNotAndDeletedAtIsNull(String sku, Long id);
}
