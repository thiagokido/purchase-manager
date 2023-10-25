package br.com.fiap.purchasemanager.infrastructure.repositories;

import br.com.fiap.purchasemanager.infrastructure.models.PurchaseBoardModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseBoardRepository extends JpaRepository<PurchaseBoardModel, UUID> {
}
