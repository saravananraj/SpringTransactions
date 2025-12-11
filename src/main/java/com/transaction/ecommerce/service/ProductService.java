package com.transaction.ecommerce.service;

import com.transaction.ecommerce.entity.Product;
import com.transaction.ecommerce.repository.InventoryRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductService {

    @Autowired
    private InventoryRepository inventoryRepository;

    //Hibernate might cache certain queries.
    // Flush after each query using entityManager.flush().

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void updateStock(int productId, int stock) throws InterruptedException {

        Product product = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStockQuantity(stock);

        inventoryRepository.save(product);

        entityManager.flush();// Ensure the update is sent to the DB

        // Simulate a long-running transaction (does not commit yet)
        System.out.println("Transaction A: Stock updated to " + stock);
        Thread.sleep(5000);

//        System.out.println("Transaction A: Rolling back the update");
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//explicit rollback
        System.out.println("Transaction A: Committed the update");


    }

    @Transactional
    public int checkStock(int productId) {
        Product product = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        System.out.println("Transaction B: Read stock as " + product.getStockQuantity());
        return product.getStockQuantity();
    }

    public void fetchStock(int productId) {
        // First read
        Product product = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("Transaction B: First read stock as " + product.getStockQuantity());//40
    }
}
