package com.example.angelneria_final_project.domain.repositories;

import com.example.angelneria_final_project.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
