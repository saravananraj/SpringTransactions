package com.transaction.ecommerce.handler;

import com.transaction.ecommerce.entity.Product;
import com.transaction.ecommerce.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryHandler {
    @Autowired
    InventoryRepository inventoryRepository;

    public Product saveOrder(Product product) {
        return inventoryRepository.save(product);
    }

}
