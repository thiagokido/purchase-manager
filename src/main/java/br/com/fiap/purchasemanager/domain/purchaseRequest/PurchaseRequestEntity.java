package br.com.fiap.purchasemanager.domain.purchaseRequest;

import java.util.UUID;

public class PurchaseRequestEntity {

    private UUID id;
    private String itemDescription;
    private int quantity;
    private PurchaseRequestStatus status;

    public PurchaseRequestEntity(UUID id, String itemDescription, int quantity, PurchaseRequestStatus status) {
        this.id = id;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.status = status;
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
}
