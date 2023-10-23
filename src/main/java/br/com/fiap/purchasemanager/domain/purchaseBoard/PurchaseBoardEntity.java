package br.com.fiap.purchasemanager.domain.purchaseBoard;

import java.util.UUID;

public class PurchaseBoardEntity {

    private UUID id;
    private String description;
    private String constructionName;
    private String status;

    public PurchaseBoardEntity(UUID id, String description, String constructionName) {
        this.id = id;
        this.description = description;
        this.constructionName = constructionName;
    }

    public void changeStatus(String status) {
        this.status = status;
    }
}
