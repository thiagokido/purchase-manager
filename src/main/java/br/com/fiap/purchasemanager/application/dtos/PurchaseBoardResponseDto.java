package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardEntity;
import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record PurchaseBoardResponseDto(

        UUID id,
        @NotBlank
        String description,
        @NotBlank
        String constructionName,
        String status,
        List<PurchaseRequestResponseDto> requests
) {
}
