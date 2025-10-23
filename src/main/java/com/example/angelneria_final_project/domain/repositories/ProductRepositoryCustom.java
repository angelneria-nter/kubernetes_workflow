package com.example.angelneria_final_project.domain.repositories;

import com.example.angelneria_final_project.domain.entities.Product;

import java.util.HashMap;
import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findFilteredProducts(HashMap<String, Object> data);
}
