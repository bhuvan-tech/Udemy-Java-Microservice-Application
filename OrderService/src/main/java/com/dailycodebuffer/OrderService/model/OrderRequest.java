package com.dailycodebuffer.OrderService.model;

import lombok.Data;

@Data
public class OrderRequest {
    private long productId;
    private long totalAmount;
    private long quantity;
    private PaymentMethod paymentMethod;
}
