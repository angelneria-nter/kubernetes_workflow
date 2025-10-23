package com.example.angelneria_final_project.domain.repositories;

import com.example.angelneria_final_project.domain.entities.OrderProduct;
import com.example.angelneria_final_project.domain.entities.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    void deleteAllByOrderId(Long id);
    List<OrderProduct> findByProductId(Long id);
}
