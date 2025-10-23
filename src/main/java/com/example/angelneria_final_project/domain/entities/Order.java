package com.example.angelneria_final_project.domain.entities;

import com.example.angelneria_final_project.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @CreationTimestamp
    private Date createdAt;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> products;

}
