package com.transaction.ecommerce.handler;

import com.transaction.ecommerce.entity.Product;
import com.transaction.ecommerce.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryHandler {
    @Autowired
    InventoryRepository inventoryRepository;

    public Product getProduct(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not available with id " + id));
    }

    public void updateProductDetails(Product product) {
        inventoryRepository.save(product);
    }
}
