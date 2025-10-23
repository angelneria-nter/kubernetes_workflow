package com.example.angelneria_final_project.infraestructure.dto.input;

import com.example.angelneria_final_project.domain.enums.OrderStatus;
import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInputDto {

    @NotNull(groups = CreateGroup.class, message = "Status must be present.")
    private OrderStatus status;
    @NotNull(groups = CreateGroup.class, message = "User must be present.")
    private Long userId;
    @NotEmpty(groups = CreateGroup.class, message = "The order must contains products.")
    @Valid
    private List<ProductAmountInputDto> products;

    @AssertTrue(groups = UpdateGroup.class, message = "You cannot update with an empty list of products.")
    private boolean isProductsValid() {
        return Objects.isNull(this.products) || !this.products.isEmpty();
    }

}
