package br.com.fiap.purchasemanager.infrastructure.models;

import br.com.fiap.purchasemanager.domain.Order.OrderEntity;
import br.com.fiap.purchasemanager.domain.Order.OrderStatus;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name="orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "cnpj", column = @Column(name = "supplier_cnpj")),
            @AttributeOverride( name = "name", column = @Column(name = "supplier_name"))
    })
    private SupplierModel supplier;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "street", column = @Column(name = "delivery_address_street")),
            @AttributeOverride( name = "number", column = @Column(name = "delivery_address_number")),
            @AttributeOverride( name = "zipcode", column = @Column(name = "delivery_address_zipcode")),
            @AttributeOverride( name = "city", column = @Column(name = "delivery_address_city")),
            @AttributeOverride( name = "state", column = @Column(name = "delivery_address_state"))
    })
    private DeliveryAddressModel deliveryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "dueTo", column = @Column(name = "payment_due_to")),
            @AttributeOverride( name = "installments", column = @Column(name = "payment_installments")),
            @AttributeOverride( name = "totalValue", column = @Column(name = "payment_total_value"))
    })
    private PaymentConditionsModel paymentConditions;
    private String status;
    @OneToMany(mappedBy = "order")
    private List<OrderItemModel> orderItems;

    public OrderModel() {}

    public OrderModel(UUID id, SupplierModel supplier, DeliveryAddressModel deliveryAddress, PaymentConditionsModel paymentConditions, String status) {
        this.id = id;
        this.supplier = supplier;
        this.deliveryAddress = deliveryAddress;
        this.paymentConditions = paymentConditions;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public SupplierModel getSupplier() {
        return supplier;
    }

    public DeliveryAddressModel getDeliveryAddress() {
        return deliveryAddress;
    }

    public PaymentConditionsModel getPaymentConditions() {
        return paymentConditions;
    }

    public List<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderItems(List<OrderItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderEntity toOrderEntity() {
        return new OrderEntity(
            this.id,
            this.paymentConditions.toPaymentConditionsVO(),
            this.supplier.toSupplierVO(),
            this.deliveryAddress.toDeliveryAddressVO(),
            orderItems == null
                ? Collections.emptyList()
                : this.orderItems
                    .stream()
                    .map(OrderItemModel::toOrderItemEntity)
                    .collect(Collectors.toList()),
            OrderStatus.valueOf(this.status)
        );
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", supplier=" + supplier +
                ", deliveryAddress=" + deliveryAddress +
                ", paymentConditions=" + paymentConditions +
                ", orderItems=" + orderItems +
                '}';
    }
}
