package com.city.online.api.repository;

import com.city.online.api.model.Cart;
import com.city.online.api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{vendorId:?0}")
    List<Product> findByVendorId(String vendorId);
    @Query("{'$and':[{ 'status':?0}, {'productType':?1}]}")
    Page<Product> findAllProductsByStatusAndType(String status, String productType, Pageable pageable);
}
