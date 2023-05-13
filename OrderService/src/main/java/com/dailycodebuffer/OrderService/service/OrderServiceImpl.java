package com.dailycodebuffer.OrderService.service;

import com.dailycodebuffer.OrderService.Exception.CustomException;
import com.dailycodebuffer.OrderService.entity.Order;
import com.dailycodebuffer.OrderService.external.Request.PaymentRequest;
import com.dailycodebuffer.OrderService.external.client.PaymentService;
import com.dailycodebuffer.OrderService.external.client.ProductService;
import com.dailycodebuffer.OrderService.model.OrderRequest;
import com.dailycodebuffer.OrderService.model.OrderResponse;
import com.dailycodebuffer.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // order service
        // product service - update
        // payment service - status
        // cancel
        log.info("Placing Order ...");

        // reducing the quantity before placing an order
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .build();

        order = orderRepository.save(order);
        log.info("Order Created successfully with id "+ order.getId());

        log.info("Payment in process...");

        PaymentRequest paymentRequest
                = new PaymentRequest().builder()
                .orderId(order.getId())
                .paymentMethod(orderRequest.getPaymentMethod())
                .amount((int) orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment SUCCESSFUL, changing payment status to SUCCESSFUL");
            orderStatus = "PLACED";
        }
        catch (Exception e){
            log.info("Payment Failed, Changing payment status to Payment Failed");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);
        log.info("Order placed with order id "+order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long id) {
        log.info("Getting order details for the order with id "+ id);

        Order order
                = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order Details not found for order with id "+id,
                        404, "NOT_FOUND"));

        log.info("Invoking product service to fetch the product details for product with id "+order.getProductId());

        OrderResponse orderResponse
                = OrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .orderId(order.getId())
                .amount(order.getAmount())
                .date(order.getOrderDate())
                .build();

        return orderResponse;
    }
}
