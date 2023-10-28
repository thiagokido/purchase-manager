package br.com.fiap.purchasemanager.domain.Order;

import br.com.fiap.purchasemanager.application.dtos.OrderItemDto;
import br.com.fiap.purchasemanager.application.dtos.OrderRequestDto;
import br.com.fiap.purchasemanager.application.dtos.OrderResponseDto;
import br.com.fiap.purchasemanager.infrastructure.models.OrderItemModel;
import br.com.fiap.purchasemanager.infrastructure.models.OrderModel;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseRequestModel;
import br.com.fiap.purchasemanager.infrastructure.repositories.OrderItemRepository;
import br.com.fiap.purchasemanager.infrastructure.repositories.OrderRepository;
import br.com.fiap.purchasemanager.infrastructure.repositories.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            PurchaseRequestRepository purchaseRequestRepository,
            OrderItemRepository orderItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderResponseDto create(OrderRequestDto orderRequestDto) {

        OrderEntity order = orderRequestDto.toOrderEntity();
        OrderModel savedOrderModel = orderRepository.save(
                order.toOrderModel()
        );

        List<OrderItemEntity> orderItems = orderRequestDto.orderItems()
                .stream()
                .map(OrderItemDto::toOrderItemEntity)
                .toList();

        List<OrderItemModel> savedListOfOrderItemModel = orderItems.stream().map( it -> {
            PurchaseRequestModel purchaseRequest =  purchaseRequestRepository.findById(it.getPurchaseRequestId()).orElseThrow();
            return orderItemRepository.save(it.toOrderItemModel(purchaseRequest, savedOrderModel));
        }).toList();

        List<OrderItemEntity> savedListOfOrderItems = savedListOfOrderItemModel
                .stream()
                .map(OrderItemModel::toOrderItemEntity)
                .toList();

        savedOrderModel.setOrderItems(savedListOfOrderItemModel);

        return savedOrderModel.toOrderEntity().toOrderResponseDto();
    }

    public OrderResponseDto getById(UUID id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow().toOrderEntity();
        return order.toOrderResponseDto();
    }
}
