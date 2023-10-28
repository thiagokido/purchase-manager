package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.Order.OrderItemEntity;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record OrderItemDto(

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
                this.description(),
                this.quantity(),
                this.unitPrice(),
                this.totalPrice(),
                this.purchaseRequestId()
        );
    }
}
