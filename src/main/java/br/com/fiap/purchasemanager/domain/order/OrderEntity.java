package br.com.fiap.purchasemanager.domain.order;

import br.com.fiap.purchasemanager.application.dtos.OrderResponseDto;
import br.com.fiap.purchasemanager.domain.order.valueObjects.DeliveryAddressVO;
import br.com.fiap.purchasemanager.domain.order.valueObjects.PaymentConditionsVO;
import br.com.fiap.purchasemanager.domain.order.valueObjects.SupplierVO;
import br.com.fiap.purchasemanager.infrastructure.models.OrderModel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderEntity {

    private UUID id;
    private PaymentConditionsVO paymentConditionsVO;
    private SupplierVO supplierVO;
    private DeliveryAddressVO deliveryAddressVO;
    private OrderStatus status;
    private List<OrderItemEntity> orderItems;

    public OrderEntity(UUID id, PaymentConditionsVO paymentConditionsVO, SupplierVO supplierVO, DeliveryAddressVO deliveryAddressVO, List<OrderItemEntity> orderItems, OrderStatus status) {
        this.id = id;
        this.paymentConditionsVO = paymentConditionsVO;
        this.supplierVO = supplierVO;
        this.deliveryAddressVO = deliveryAddressVO;
        this.orderItems = orderItems;
        this.status = status;
    }

    public void approve() {
        changeStatus(OrderStatus.APPROVED);
    }

    public void reject() {
        changeStatus(OrderStatus.REJECTED);
    }

    private void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderModel toOrderModel() {
        return new OrderModel(
                this.id,
                this.supplierVO.toSupplierModel(),
                this.deliveryAddressVO.toDeliveryAddressModel(),
                this.paymentConditionsVO.toPaymentConditionsModel(),
                this.status.toString()
        );
    }

    public OrderResponseDto toOrderResponseDto() {
        return new OrderResponseDto(
                this.id,
                this.paymentConditionsVO.toPaymentConditionsDto(),
                this.supplierVO.toSupplierDto(),
                this.deliveryAddressVO.toDeliveryAddressDto(),
                this.orderItems.stream().map(OrderItemEntity::toOrderItemDto).collect(Collectors.toList()),
                this.status
        );
    }
}
