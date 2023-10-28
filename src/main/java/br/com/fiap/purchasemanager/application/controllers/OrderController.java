package br.com.fiap.purchasemanager.application.controllers;

import br.com.fiap.purchasemanager.application.dtos.CreateOrderRequestDto;
import br.com.fiap.purchasemanager.application.dtos.OrderItemDto;
import br.com.fiap.purchasemanager.application.dtos.OrderResponseDto;
import br.com.fiap.purchasemanager.domain.Order.OrderService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody CreateOrderRequestDto order
    ) {
        OrderResponseDto createdOrder = orderService.create(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findByID(
            @PathVariable UUID id
    ) {
        OrderResponseDto order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<OrderResponseDto> approveOrder(
            @PathVariable UUID id
    ) {
        OrderResponseDto order = orderService.approveOrder(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<OrderResponseDto> rejectOrder(
            @PathVariable UUID id
    ) {
        OrderResponseDto order = orderService.rejectOrder(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/item/{itemId}")
    public ResponseEntity<OrderItemDto> udpateOrderItem(
            @PathVariable UUID orderId,
            @PathVariable Long itemId,
            @RequestBody OrderItemDto itemDto
    ) {
        OrderItemDto item = orderService.updateOrderItem(orderId, itemId, itemDto);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID id
    ) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
