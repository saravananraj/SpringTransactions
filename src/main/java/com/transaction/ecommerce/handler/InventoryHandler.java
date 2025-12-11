package com.transaction.ecommerce.handler;

import com.transaction.ecommerce.entity.Order;
import com.transaction.ecommerce.entity.Product;
import com.transaction.ecommerce.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryHandler {
    @Autowired
    InventoryRepository inventoryRepository;

    public Product getProduct(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not available with id " + id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void UpdateInventory(Product product, Order order) {
        int availableStock = product.getStockQuantity() - order.getQuantity();
        product.setStockQuantity(availableStock);
        if (product.getPrice() > 5000) {
            throw new RuntimeException("DB Crases.....");   // made to check rollback occur or not
        }
        inventoryRepository.save(product);
    }
}
