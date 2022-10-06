package com.city.online.api.dto.request;

import com.city.online.api.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductUpdateRequestDto {
    List<Product> productList;
}
