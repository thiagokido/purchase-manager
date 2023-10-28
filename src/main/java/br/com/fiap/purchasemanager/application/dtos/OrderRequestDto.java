package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.Order.OrderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.stream.Collectors;

public record OrderRequestDto(

        @NotBlank
        @JsonProperty("payment")
        PaymentConditionsDto paymentConditions,
        @NotBlank
        @JsonProperty("supplier")
        SupplierDto supplier,
        @NotBlank
        @JsonProperty("address")
        DeliveryAddressDto deliveryAddress,
        @NotBlank
        @JsonProperty("items")
        List<OrderItemDto> orderItems
) {
    public OrderEntity toOrderEntity() {
        return new OrderEntity(
                this.paymentConditions().toPaymentConditionsVO(),
                this.supplier().toSupplierVO(),
                this.deliveryAddress().toDeliveryAddressVO(),
                this.orderItems().stream().map(OrderItemDto::toOrderItemEntity).collect(Collectors.toList())
        );
    }
}

