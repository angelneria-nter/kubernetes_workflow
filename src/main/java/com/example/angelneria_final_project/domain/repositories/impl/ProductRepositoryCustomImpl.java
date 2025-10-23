package com.example.angelneria_final_project.domain.repositories.impl;

import com.example.angelneria_final_project.domain.entities.Product;
import com.example.angelneria_final_project.domain.repositories.ProductRepositoryCustom;
import com.example.angelneria_final_project.mapper.ProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findFilteredProducts(HashMap<String, Object> data) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        data.forEach((field, value) ->{
            switch (field){
                case "name":
                    predicates.add(cb.like(root.get(field), "%" + (String) value + "%"));
                    break;

                case "price":
                    predicates.add(cb.equal(root.get(field), value));
                    break;

                case "initDate":
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), (LocalDate) value));
                    break;

                case "finishDate":
                    predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), (LocalDate) value));
                    break;
            }
        });

        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));

        return entityManager.createQuery(query).getResultList();

    }
}
