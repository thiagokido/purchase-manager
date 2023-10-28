package br.com.fiap.purchasemanager.infrastructure.repositories;

import br.com.fiap.purchasemanager.infrastructure.models.OrderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemModel, Long> {
}
