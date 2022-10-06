package com.city.online.api.service;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.ProductUpdateRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.Product;
import com.city.online.api.model.enums.Status;
import com.city.online.api.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Page<Product> getAllProductsByStatusAndType(String status, String productType, Pageable pageable) {
        try {
            return productRepository.findAllProductsByStatusAndType(status, productType, pageable);
        } catch (Exception e) {
            throwException(AppConstants.PRODUCT_GET_ERROR_CODE,AppConstants.PRODUCT_GET_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    /*
    * Updates the status of all of the products for a restaurant ID
    * */
    public void updateProductStatus(Status status, String vendorId){
        try {
            List<Product> productList = productRepository.findByVendorId(vendorId);
            productList.parallelStream().forEach(product -> {
                product.setStatus(status);
                productRepository.save(product);
            });
        } catch (Exception e) {
            throwException(AppConstants.PRODUCT_GET_ERROR_CODE,AppConstants.PRODUCT_GET_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Product updateProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throwException(AppConstants.PRODUCT_GET_ERROR_CODE, AppConstants.PRODUCT_GET_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
