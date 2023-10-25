package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardEntity;
import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.Collections;
import java.util.UUID;

public record PurchaseBoardRequestDto (

        @NotBlank
        String description,
        @NotBlank
        String constructionName
) {
    public PurchaseBoardEntity toPurchaseBoardEntity() {
        return new PurchaseBoardEntity(
                this.description(),
                this.constructionName(),
                PurchaseBoardStatus.valueOf("CREATED"),
                Collections.emptyList()
        );
    }
}
