package com.dailyCodeBuffer.ProductBuffer.service;

import com.dailyCodeBuffer.ProductBuffer.model.ProductRequest;
import com.dailyCodeBuffer.ProductBuffer.model.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long id);

    void reduceQuantity(long productId, long quantity);
}
