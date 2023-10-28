package br.com.fiap.purchasemanager.domain.Order;

import br.com.fiap.purchasemanager.application.dtos.OrderResponseDto;
import br.com.fiap.purchasemanager.domain.Order.valueObjects.DeliveryAddressVO;
import br.com.fiap.purchasemanager.domain.Order.valueObjects.PaymentConditionsVO;
import br.com.fiap.purchasemanager.domain.Order.valueObjects.SupplierVO;
import br.com.fiap.purchasemanager.infrastructure.models.OrderModel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderEntity {

    private UUID id;
    private PaymentConditionsVO paymentConditionsVO;
    private SupplierVO supplierVO;
    private DeliveryAddressVO deliveryAddressVO;
    private List<OrderItemEntity> orderItems;

    public OrderEntity(PaymentConditionsVO paymentConditionsVO, SupplierVO supplierVO, DeliveryAddressVO deliveryAddressVO, List<OrderItemEntity> orderItems) {
        this.paymentConditionsVO = paymentConditionsVO;
        this.supplierVO = supplierVO;
        this.deliveryAddressVO = deliveryAddressVO;
        this.orderItems = orderItems;
    }

    public OrderEntity(UUID id, PaymentConditionsVO paymentConditionsVO, SupplierVO supplierVO, DeliveryAddressVO deliveryAddressVO, List<OrderItemEntity> orderItems) {
        this.id = id;
        this.paymentConditionsVO = paymentConditionsVO;
        this.supplierVO = supplierVO;
        this.deliveryAddressVO = deliveryAddressVO;
        this.orderItems = orderItems;
    }

    public OrderModel toOrderModel() {
        return new OrderModel(
                this.supplierVO.toSupplierModel(),
                this.deliveryAddressVO.toDeliveryAddressModel(),
                this.paymentConditionsVO.toPaymentConditionsModel()
        );
    }

    public OrderResponseDto toOrderResponseDto() {
        return new OrderResponseDto(
                this.id,
                this.paymentConditionsVO.toPaymentConditionsDto(),
                this.supplierVO.toSupplierDto(),
                this.deliveryAddressVO.toDeliveryAddressDto(),
                this.orderItems.stream().map(OrderItemEntity::toOrderItemDto).collect(Collectors.toList())
        );
    }
}