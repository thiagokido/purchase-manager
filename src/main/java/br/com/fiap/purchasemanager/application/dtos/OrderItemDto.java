package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.order.OrderItemEntity;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record OrderItemDto(
        
        Long id,
        @NotBlank
        String description,
        @NotBlank
        int quantity,
        @NotBlank
        double unitPrice,
        @NotBlank
        double totalPrice,
        @NotBlank
        UUID purchaseRequestId
) {
    public OrderItemEntity toOrderItemEntity() {
        return new OrderItemEntity(
                null,
                this.description(),
                this.quantity(),
                this.unitPrice(),
                this.totalPrice(),
                this.purchaseRequestId()
        );
    }
}
