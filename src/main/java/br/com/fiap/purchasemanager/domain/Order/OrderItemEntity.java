package br.com.fiap.purchasemanager.domain.Order;

import br.com.fiap.purchasemanager.application.dtos.OrderItemDto;
import br.com.fiap.purchasemanager.infrastructure.models.OrderItemModel;
import br.com.fiap.purchasemanager.infrastructure.models.OrderModel;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseRequestModel;

import java.util.UUID;

public class OrderItemEntity {

    private Long id;
    private String description;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private UUID purchaseRequestId;

    public OrderItemEntity(Long id, String description, int quantity, double unitPrice, double totalPrice, UUID purchaseRequestId) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.purchaseRequestId = purchaseRequestId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public UUID getPurchaseRequestId() {
        return purchaseRequestId;
    }

    public OrderItemModel toOrderItemModel(PurchaseRequestModel purchaseRequest, OrderModel order) {
        return new OrderItemModel(
                this.description,
                this.quantity,
                this.unitPrice,
                this.totalPrice,
                purchaseRequest,
                order
        );
    }

    public OrderItemDto toOrderItemDto() {
        return new OrderItemDto(
                this.id,
                this.description,
                this.quantity,
                this.unitPrice,
                this.totalPrice,
                this.purchaseRequestId
        );
    }
}
