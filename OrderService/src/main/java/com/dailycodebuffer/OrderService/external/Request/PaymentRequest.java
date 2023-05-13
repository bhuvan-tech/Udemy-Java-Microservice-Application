package com.dailycodebuffer.OrderService.external.Request;

import com.dailycodebuffer.OrderService.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private long orderId;
    private int amount;
    private String referenceNumber;
    private PaymentMethod paymentMethod;
}
