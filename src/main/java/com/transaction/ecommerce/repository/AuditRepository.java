package com.transaction.ecommerce.repository;

import com.transaction.ecommerce.entity.AuditLog;
import com.transaction.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditLog, Integer> {

}
