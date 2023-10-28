package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestEntity;
import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestStatus;

import java.util.UUID;

public record PurchaseRequestRequestDto(

            String itemDescription,
            int quantity
) {

    public PurchaseRequestEntity toPurchaseRequestEntity() {
        return new PurchaseRequestEntity(
                null,
                this.itemDescription(),
                this.quantity(),
                PurchaseRequestStatus.PENDING_APPROVAL
        );
    }
}

