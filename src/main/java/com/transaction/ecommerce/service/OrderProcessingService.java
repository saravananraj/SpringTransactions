package com.transaction.ecommerce.service;

import com.transaction.ecommerce.entity.Order;
import com.transaction.ecommerce.entity.Product;
import com.transaction.ecommerce.handler.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private final InventoryHandler inventoryHandler;
    private final OrderHandler orderHandler;
    private final AuditHandler auditHandler;
    private final PaymentValidatorHandler paymentValidatorHandler;
    private final NotificationHandler notificationHandler;
    private final ProductRecommendationHandler productRecommendationHandler;

    public  OrderProcessingService(InventoryHandler inventoryHandler,
                                   OrderHandler orderHandler,
                                   AuditHandler auditHandler,
                                   PaymentValidatorHandler paymentValidatorHandler,
                                   NotificationHandler notificationHandler,
                                   ProductRecommendationHandler productRecommendationHandler) {
        this.inventoryHandler = inventoryHandler;
        this.orderHandler = orderHandler;
        this.auditHandler = auditHandler;
        this.paymentValidatorHandler = paymentValidatorHandler;
        this.notificationHandler = notificationHandler;
        this.productRecommendationHandler = productRecommendationHandler;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order placeAnOrder(Order order) {

        // fetch the product details
        Product product = inventoryHandler.getProduct(order.getProductId());

        validateOrder(order, product);

        try {
            // update the total price and save order
            order.setTotalPrice(order.getQuantity() * product.getPrice());
            orderHandler.saveOrder(order);
            // update the inventory

            //Audit log
            inventoryHandler.UpdateInventory(product, order);
            auditHandler.logAuditDetails(order, "Order success !!");
        } catch (Exception e) {
            auditHandler.logAuditDetails(order, "Order failed");
        }

        // payment validation

//         paymentValidatorHandler.validatePayment(order);

        // sendAnOrderNotification -- move the sepearate method . once all transaction done then call.

//        notificationHandler.sendOrderConfirmationNotification(order);

//        productRecommendationHandler.getRecommendations();  // if transaction is there suspend and run. or just run

        getCustomerDetails();

        return order;
    }

    // Call this method after placeAnOrder is successfully completed so it will never throw error
    public void processOrder(Order order) {
        // Step 1: Place the order
        Order savedOrder = placeAnOrder(order);

        // Step 2: Send notification (non-transactional)
        notificationHandler.sendOrderConfirmationNotification(order);
    }

    @Transactional(propagation =  Propagation.SUPPORTS)
    private static void getCustomerDetails() {
        System.out.println("Customer details fetched !!!");
    }

    private static void validateOrder(Order order, Product product) {
        // check the availability
        if (product.getStockQuantity() < order.getQuantity()) {
            throw new RuntimeException("Product available is less than ordered quantity");
        }
    }

}
