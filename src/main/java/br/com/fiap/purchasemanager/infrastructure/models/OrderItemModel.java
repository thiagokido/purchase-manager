package br.com.fiap.purchasemanager.infrastructure.models;

import br.com.fiap.purchasemanager.domain.order.OrderItemEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int quantity;
    @Column(name = "unit_price")
    private double unitPrice;
    @Column(name = "total_price")
    private double totalPrice;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_request_id", referencedColumnName = "id")
    private PurchaseRequestModel purchaseRequest;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderModel order;

    public OrderItemModel() {}

    public OrderItemModel(String description, int quantity, double unitPrice, double totalPrice, PurchaseRequestModel purchaseRequest, OrderModel order) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.purchaseRequest = purchaseRequest;
        this.order = order;
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

    public PurchaseRequestModel getPurchaseRequest() {
        return purchaseRequest;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPurchaseRequest(PurchaseRequestModel purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public OrderItemEntity toOrderItemEntity() {
        return new OrderItemEntity(
            this.id,
            this.description,
            this.quantity,
            this.unitPrice,
            this.totalPrice,
            this.purchaseRequest.getId()
        );
    }
}
