package br.com.fiap.purchasemanager.domain.purchaseRequest;

import br.com.fiap.purchasemanager.application.dtos.PurchaseRequestResponseDto;

import java.util.UUID;

public class PurchaseRequestEntity {

    private UUID id;
    private String itemDescription;
    private int quantity;
    private PurchaseRequestStatus status;

    public PurchaseRequestEntity() {}

    public PurchaseRequestEntity(UUID id, String itemDescription, int quantity, PurchaseRequestStatus status) {
        this.id = id;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.status = status;
    }

    public PurchaseRequestEntity(String itemDescription, int quantity, PurchaseRequestStatus status) {
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.status = status;
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

    public PurchaseRequestStatus getStatus() {
        return status;
    }

    public void approve() {
        changeStatus(PurchaseRequestStatus.APPROVED);
    }

    public void reject() {
        changeStatus(PurchaseRequestStatus.REJECTED);
    }

    public void complete() {
        changeStatus(PurchaseRequestStatus.COMPLETED);
    }

    private void changeStatus(PurchaseRequestStatus status) {
        this.status = status;
    }

    public PurchaseRequestResponseDto toPurchaseRequestResponseDto(UUID purchaseBoardId) {
        return new PurchaseRequestResponseDto(
                this.id,
                this.itemDescription,
                this.quantity,
                this.status.toString(),
                purchaseBoardId
        );
    }
}
