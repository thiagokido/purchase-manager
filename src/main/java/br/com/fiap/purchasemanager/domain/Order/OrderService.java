package br.com.fiap.purchasemanager.domain.Order;

import br.com.fiap.purchasemanager.application.dtos.OrderItemDto;
import br.com.fiap.purchasemanager.application.dtos.OrderRequestDto;
import br.com.fiap.purchasemanager.application.dtos.OrderResponseDto;
import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestStatus;
import br.com.fiap.purchasemanager.infrastructure.models.OrderItemModel;
import br.com.fiap.purchasemanager.infrastructure.models.OrderModel;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseRequestModel;
import br.com.fiap.purchasemanager.infrastructure.repositories.OrderItemRepository;
import br.com.fiap.purchasemanager.infrastructure.repositories.OrderRepository;
import br.com.fiap.purchasemanager.infrastructure.repositories.PurchaseRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    @Transactional
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

            if (Objects.equals(purchaseRequest.getStatus(), PurchaseRequestStatus.APPROVED.toString())) {
                return orderItemRepository.save(it.toOrderItemModel(purchaseRequest, savedOrderModel));
            } else {
                throw new RuntimeException("Pedidos só podem ser gerados para requisições aprovadas");
            }
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

    @Transactional
    public OrderResponseDto approveOrder(UUID id) {

        OrderModel orderModel = orderRepository.findById(id).orElseThrow();
        OrderEntity orderEntity = orderModel.toOrderEntity();

        orderEntity.approve();

        List<OrderItemModel> orderItemModel = orderModel.getOrderItems();

        orderModel = orderEntity.toOrderModel();
        orderModel.setOrderItems(orderItemModel);

        OrderModel finalOrderModel = orderModel;
        orderItemModel.forEach(it -> {

            PurchaseRequestModel purchaseRequest = purchaseRequestRepository.findById(it.getPurchaseRequest().getId()).orElseThrow();
            purchaseRequest.setOrder(finalOrderModel);
            purchaseRequest.setStatus(PurchaseRequestStatus.COMPLETED.toString());
            purchaseRequestRepository.save(purchaseRequest);
        });

        return orderRepository.save(orderModel).toOrderEntity().toOrderResponseDto();
    }

    public OrderResponseDto rejectOrder(UUID id) {

        OrderModel orderModel = orderRepository.findById(id).orElseThrow();
        OrderEntity orderEntity = orderModel.toOrderEntity();

        orderEntity.reject();

        List<OrderItemModel> orderItemModel = orderModel.getOrderItems();

        orderModel = orderEntity.toOrderModel();
        orderModel.setOrderItems(orderItemModel);

        return orderRepository.save(orderModel).toOrderEntity().toOrderResponseDto();
    }
}
