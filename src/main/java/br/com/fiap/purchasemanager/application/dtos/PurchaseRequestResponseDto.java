package br.com.fiap.purchasemanager.application.dtos;

import java.util.UUID;

public record PurchaseRequestResponseDto(

        UUID id,
        String itemDescription,
        int quantity,
        String status,
        UUID purchaseBoardId
) {
}
