package com.city.online.api.controller;

import com.city.online.api.dto.request.ProductUpdateRequestDto;
import com.city.online.api.model.BusinessContactUser;
import com.city.online.api.model.Product;
import com.city.online.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    /* API to fetch business user details by status */
    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<Product>> getAllProductsByStatusAndType(@RequestParam String status,@RequestParam String productType, @RequestParam Integer page, @RequestParam Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAllProductsByStatusAndType(status, productType, pageable);
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    /*API tpo update the products in bulk , something like status*/
    @PutMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product product1 = productService.updateProduct(product);
        return new ResponseEntity<>(product1, HttpStatus.OK);
    }
}
