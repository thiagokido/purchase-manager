package br.com.fiap.purchasemanager.application.controllers;

import br.com.fiap.purchasemanager.application.dtos.OrderRequestDto;
import br.com.fiap.purchasemanager.application.dtos.OrderResponseDto;
import br.com.fiap.purchasemanager.domain.Order.OrderService;
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
            @RequestBody OrderRequestDto order
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
}
