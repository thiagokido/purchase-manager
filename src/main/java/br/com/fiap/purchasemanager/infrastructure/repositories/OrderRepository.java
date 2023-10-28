package br.com.fiap.purchasemanager.infrastructure.repositories;

import br.com.fiap.purchasemanager.infrastructure.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
}
