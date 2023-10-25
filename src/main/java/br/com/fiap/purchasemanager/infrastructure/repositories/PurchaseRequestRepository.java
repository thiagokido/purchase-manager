package br.com.fiap.purchasemanager.infrastructure.repositories;

import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestEntity;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequestModel, UUID> {
    PurchaseRequestModel findByPurchaseBoardIdAndId(UUID purchaseBoardId, UUID requestId);
}
