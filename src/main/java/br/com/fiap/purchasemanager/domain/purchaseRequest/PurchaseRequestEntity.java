package br.com.fiap.purchasemanager.domain.purchaseRequest;

import br.com.fiap.purchasemanager.application.dtos.PurchaseRequestResponseDto;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseBoardModel;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseRequestModel;

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

    public void approve() { changeStatus(PurchaseRequestStatus.APPROVED); }

    public void reject() {
        changeStatus(PurchaseRequestStatus.REJECTED);
    }

    public void complete() {
        changeStatus(PurchaseRequestStatus.COMPLETED);
    }

    public void moveToPending() { changeStatus(PurchaseRequestStatus.PENDING_APPROVAL); }

    private void changeStatus(PurchaseRequestStatus status) {
        this.status = status;
    }

    public void changeItemDescription(String newDescription) {
        this.itemDescription = newDescription;
    }

    public void changeQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public PurchaseRequestModel toPurchaseRequestModel(PurchaseBoardModel purchaseBoard) {
        return new PurchaseRequestModel(
                this.id,
                this.itemDescription,
                this.quantity,
                this.status.toString(),
                purchaseBoard
        );
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
