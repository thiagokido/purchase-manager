package br.com.fiap.purchasemanager.infrastructure.models;

import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardEntity;
import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardStatus;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name="purchase_boards")
public class PurchaseBoardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private String constructionName;
    private String status;
    @OneToMany(mappedBy = "purchaseBoard")
    private List<PurchaseRequestModel> purchaseRequests;

    public PurchaseBoardModel(UUID id, String description, String constructionName, String status) {
        this.id = id;
        this.description = description;
        this.constructionName = constructionName;
        this.status = status;
    }

    protected PurchaseBoardModel() {}

    public UUID getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PurchaseBoardEntity toPurchaseBoardEntity() {
        return new PurchaseBoardEntity(
                this.id,
                this.description,
                this.constructionName,
                PurchaseBoardStatus.valueOf(this.status),
                purchaseRequests == null
                        ? Collections.emptyList()
                        : this.purchaseRequests
                            .stream()
                            .map(PurchaseRequestModel::toPurchaseRequestEntity)
                            .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        return "PurchaseBoardModel{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", constructionName='" + constructionName + '\'' +
                ", status='" + status + '\'' +
                ", purchaseRequests=" + purchaseRequests +
                '}';
    }
}
