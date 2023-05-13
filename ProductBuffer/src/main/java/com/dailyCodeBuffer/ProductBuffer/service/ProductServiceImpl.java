package com.dailyCodeBuffer.ProductBuffer.service;

import com.dailyCodeBuffer.ProductBuffer.entity.Product;
import com.dailyCodeBuffer.ProductBuffer.exception.ProductServiceCustomException;
import com.dailyCodeBuffer.ProductBuffer.model.ProductRequest;
import com.dailyCodeBuffer.ProductBuffer.model.ProductResponse;
import com.dailyCodeBuffer.ProductBuffer.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product.. ");
        Product product
                = Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();

        productRepository.save(product);
        log.info("Product created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Product not found with id "+id, "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce quantiry "+quantity+" for productId "+productId);

        Product product =
                productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product not found with id "+productId, "PRODUCT_NOT_FOUND"));

        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product doesn't have enough quantity", "INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        log.info("Product with Id's "+productId+ " Quantity has been reduced by "+ quantity);
    }
}
