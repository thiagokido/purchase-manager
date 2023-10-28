package br.com.fiap.purchasemanager.domain.purchaseBoard;

import br.com.fiap.purchasemanager.application.dtos.PurchaseBoardResponseDto;
import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestEntity;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseBoardModel;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PurchaseBoardEntity {

    private UUID id;
    private String description;
    private String constructionName;
    private PurchaseBoardStatus status;
    private List<PurchaseRequestEntity> purchaseRequests;

    public PurchaseBoardEntity(UUID id, String description, String constructionName, PurchaseBoardStatus status, List<PurchaseRequestEntity> purchaseRequests) {
        this.id = id;
        this.description = description;
        this.constructionName = constructionName;
        this.status = status;
        this.purchaseRequests = purchaseRequests;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getConstructionName() {
        return constructionName;
    }

    public PurchaseBoardStatus getStatus() {
        return status;
    }

    public List<PurchaseRequestEntity> getPurchaseRequests() {
        return purchaseRequests;
    }

    public void complete() {
        changeStatus(PurchaseBoardStatus.COMPLETED);
    }

    public void changeStatus(PurchaseBoardStatus status) {
        this.status = status;
    }

    public PurchaseBoardResponseDto toPurchaseBoardResponseDto() {
        return new PurchaseBoardResponseDto(
                this.id,
                this.description,
                this.constructionName,
                this.status.toString(),
                this.purchaseRequests
                        .stream()
                        .map( it -> it.toPurchaseRequestResponseDto(this.getId()))
                        .collect(Collectors.toList())
        );
    }

    public PurchaseBoardModel toPurchaseBoardModel() {
        return new PurchaseBoardModel(
                this.id,
                this.description,
                this.constructionName,
                this.status.toString()
        );
    }
}
