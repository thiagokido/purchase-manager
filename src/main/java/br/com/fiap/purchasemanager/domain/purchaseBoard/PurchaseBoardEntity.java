package br.com.fiap.purchasemanager.domain.purchaseBoard;

import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestEntity;

import java.util.List;
import java.util.UUID;

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

    public void complete() {
        changeStatus(PurchaseBoardStatus.COMPLETED);
    }

    public void changeStatus(PurchaseBoardStatus status) {
        this.status = status;
    }
}
