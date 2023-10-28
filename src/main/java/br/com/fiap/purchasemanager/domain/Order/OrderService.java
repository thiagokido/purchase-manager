package br.com.fiap.purchasemanager.domain.Order;

import br.com.fiap.purchasemanager.application.dtos.OrderItemDto;
import br.com.fiap.purchasemanager.application.dtos.CreateOrderRequestDto;
import br.com.fiap.purchasemanager.application.dtos.OrderResponseDto;
import br.com.fiap.purchasemanager.domain.exceptions.NotFoundException;
import br.com.fiap.purchasemanager.domain.exceptions.OrderException;
import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestStatus;
import br.com.fiap.purchasemanager.infrastructure.models.OrderItemModel;
import br.com.fiap.purchasemanager.infrastructure.models.OrderModel;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseRequestModel;
import br.com.fiap.purchasemanager.infrastructure.repositories.OrderItemRepository;
import br.com.fiap.purchasemanager.infrastructure.repositories.OrderRepository;
import br.com.fiap.purchasemanager.infrastructure.repositories.PurchaseRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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
            OrderItemRepository orderItemRepository,
            PurchaseRequestRepository purchaseRequestRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    @Transactional
    public OrderResponseDto create(CreateOrderRequestDto orderRequestDto) {

        OrderEntity order = orderRequestDto.toOrderEntity();
        OrderModel savedOrderModel = orderRepository.save(
                order.toOrderModel()
        );

        List<OrderItemEntity> orderItems = orderRequestDto.orderItems()
                .stream()
                .map(OrderItemDto::toOrderItemEntity)
                .toList();

        List<OrderItemModel> savedListOfOrderItemModel = orderItems.stream().map( it -> {

            PurchaseRequestModel purchaseRequest =  getPurchaseRequest(it.getPurchaseRequestId());

            if (Objects.equals(purchaseRequest.getStatus(), PurchaseRequestStatus.APPROVED.toString())) {
                return orderItemRepository.save(it.toOrderItemModel(purchaseRequest, savedOrderModel));
            } else {
                throw new OrderException("Only approved requests can be ordered");
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
        OrderEntity order = getOrderById(id).toOrderEntity();
        return order.toOrderResponseDto();
    }

    @Transactional
    public OrderResponseDto approveOrder(UUID id) {

        OrderModel orderModel = getOrderById(id);
        OrderEntity orderEntity = orderModel.toOrderEntity();

        orderEntity.approve();

        List<OrderItemModel> orderItemModel = orderModel.getOrderItems();

        orderModel = orderEntity.toOrderModel();
        orderModel.setOrderItems(orderItemModel);

        OrderModel finalOrderModel = orderModel;
        orderItemModel.forEach(it -> {

            PurchaseRequestModel purchaseRequest = getPurchaseRequest(it.getPurchaseRequest().getId());
            purchaseRequest.setOrder(finalOrderModel);
            purchaseRequest.setStatus(PurchaseRequestStatus.COMPLETED.toString());
            purchaseRequestRepository.save(purchaseRequest);
        });

        return orderRepository.save(orderModel).toOrderEntity().toOrderResponseDto();
    }

    public OrderResponseDto rejectOrder(UUID id) {

        OrderModel orderModel = getOrderById(id);
        OrderEntity orderEntity = orderModel.toOrderEntity();

        orderEntity.reject();

        List<OrderItemModel> orderItemModel = orderModel.getOrderItems();

        orderModel = orderEntity.toOrderModel();
        orderModel.setOrderItems(orderItemModel);

        return orderRepository.save(orderModel).toOrderEntity().toOrderResponseDto();
    }

    public void deleteOrder(UUID id) {

        OrderModel order = getOrderById(id);

        if (Objects.equals(order.getStatus(), OrderStatus.PENDING_APPROVAL.toString())) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderException("Only PENDING APPROVAL orders can be deleted");
        }
    }

    public OrderItemDto updateOrderItem(UUID orderId, Long itemId, OrderItemDto itemDto) {

        OrderItemModel orderItem = orderItemRepository.findByOrderIdAndId(orderId, itemId);

        PurchaseRequestModel purchaseRequestModel = getPurchaseRequest(itemDto.purchaseRequestId());

        orderItem.setDescription(itemDto.description());
        orderItem.setQuantity(itemDto.quantity());
        orderItem.setUnitPrice(itemDto.unitPrice());
        orderItem.setTotalPrice(itemDto.totalPrice());
        orderItem.setPurchaseRequest(purchaseRequestModel);

        return orderItemRepository.save(orderItem).toOrderItemEntity().toOrderItemDto();

    }

    private OrderModel getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow( () ->
                        new NotFoundException(MessageFormat.format("An order with id {0} was not found.", orderId.toString()))
                );
    }

    private PurchaseRequestModel getPurchaseRequest(UUID purchaseRequestId) {
        return purchaseRequestRepository.findById(purchaseRequestId).orElseThrow(() ->
                        new NotFoundException(MessageFormat.format("A Purchase Request with id {0} was not found.", purchaseRequestId.toString()))
                );
    }
}
