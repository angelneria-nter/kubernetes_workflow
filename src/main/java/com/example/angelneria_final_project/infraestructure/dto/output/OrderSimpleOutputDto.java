package com.example.angelneria_final_project.infraestructure.dto.output;

import com.example.angelneria_final_project.domain.enums.OrderStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSimpleOutputDto {

    private Long id;
    private OrderStatus status;
    private Date createdAt;
}
