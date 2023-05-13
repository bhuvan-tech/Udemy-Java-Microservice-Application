package com.dailyCodeBuffer.ProductBuffer.model;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private long quantity;
    private long price;
}
