package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.Order.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderResponseDto(

        UUID id,
        PaymentConditionsDto payment,
        SupplierDto supplier,
        DeliveryAddressDto deliveryAddress,
        List<OrderItemDto> items,
        OrderStatus status
) {
}
