package com.transaction.ecommerce.handler;

import com.transaction.ecommerce.entity.AuditLog;
import com.transaction.ecommerce.entity.Order;
import com.transaction.ecommerce.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentValidatorHandler {

    @Autowired
    AuditRepository auditLogRepository;

  @Transactional(propagation = Propagation.MANDATORY)
//    @Transactional(propagation = Propagation.NESTED)
    public void validatePayment(Order order) {

        boolean paymentSuccessful = false;

        if (!paymentSuccessful) {
            AuditLog paymentFailureLog = new AuditLog();
            paymentFailureLog.setOrderId(Long.valueOf(order.getId()));
            paymentFailureLog.setAction("Payment Failed for Order");
            paymentFailureLog.setTimestamp(LocalDateTime.now());

            // Testing make payment validation fails
/*
            if (order.getTotalPrice() > 100) {
                throw new RuntimeException("Payment validation failed");
            }*/

            // Save the payment failure log
            auditLogRepository.save(paymentFailureLog);
        }
    }
}
;