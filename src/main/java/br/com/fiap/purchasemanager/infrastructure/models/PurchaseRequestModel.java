package br.com.fiap.purchasemanager.infrastructure.models;

import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestEntity;
import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="purchase_requests")
public class PurchaseRequestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "item_description")
    private String itemDescription;
    private int quantity;
    private String status;
    @ManyToOne
    @JoinColumn(name = "purchase_board_id", nullable = false)
    private PurchaseBoardModel purchaseBoard;
    @OneToOne(mappedBy = "purchaseRequest")
    private OrderItemModel orderItem;

    public PurchaseRequestModel() {}

    public PurchaseRequestModel(UUID id, String itemDescription, int quantity, String status, PurchaseBoardModel purchaseBoard) {
        this.id = id;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.status = status;
        this.purchaseBoard = purchaseBoard;
    }

    public UUID getId() {
        return id;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public PurchaseBoardModel getPurchaseBoard() {
        return purchaseBoard;
    }

    public OrderItemModel getOrderItem() {
        return orderItem;
    }

    public PurchaseRequestEntity toPurchaseRequestEntity() {
        return new PurchaseRequestEntity(
                this.id,
                this.itemDescription,
                this.quantity,
                PurchaseRequestStatus.valueOf(this.status)
        );
    }

    @Override
    public String toString() {
        return "PurchaseRequestModel{" +
                "id=" + id +
                ", itemDescription='" + itemDescription + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", purchaseBoard=" + purchaseBoard +
                '}';
    }
}
