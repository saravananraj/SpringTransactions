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
public class AuditHandler {

    @Autowired
    AuditRepository auditRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAuditDetails(Order order, String action) {
        AuditLog auditLog = new AuditLog();
        auditLog.setOrderId(Long.valueOf(order.getId()));
        auditLog.setTimestamp(LocalDateTime.now());
        auditLog.setAction(action);
        auditRepository.save(auditLog);
    }
}
