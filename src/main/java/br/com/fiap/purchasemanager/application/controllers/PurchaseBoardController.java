package br.com.fiap.purchasemanager.application.controllers;

import br.com.fiap.purchasemanager.application.dtos.PurchaseBoardRequestDto;
import br.com.fiap.purchasemanager.application.dtos.PurchaseBoardResponseDto;
import br.com.fiap.purchasemanager.application.dtos.PurchaseRequestRequestDto;
import br.com.fiap.purchasemanager.application.dtos.PurchaseRequestResponseDto;
import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardService;
import br.com.fiap.purchasemanager.domain.purchaseRequest.PurchaseRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/purchase-board")
public class PurchaseBoardController {

    private final PurchaseBoardService purchaseBoardService;
    private final PurchaseRequestService purchaseRequestService;

    public PurchaseBoardController(
            PurchaseBoardService purchaseBoardService,
            PurchaseRequestService purchaseRequestService
    ) {
        this.purchaseBoardService = purchaseBoardService;
        this.purchaseRequestService = purchaseRequestService;
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseBoardResponseDto>> getAllPurchaseBoard(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<PurchaseBoardResponseDto> purchaseBoards = purchaseBoardService.findAll(pageable);
        return ResponseEntity.ok(purchaseBoards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseBoardResponseDto> getPurchaseBoardById(
            @PathVariable UUID id
    ) {
        PurchaseBoardResponseDto purchaseBoardDto = purchaseBoardService.findById(id);
        return ResponseEntity.ok(purchaseBoardDto);
    }

    @PostMapping
    public ResponseEntity<PurchaseBoardResponseDto> createPurchaseBoard(
            @RequestBody PurchaseBoardRequestDto purchaseBoardDto
    ) {
        PurchaseBoardResponseDto createdPurchaseBoardDto = purchaseBoardService.save(purchaseBoardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPurchaseBoardDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseBoard(
            @PathVariable UUID id
    ) {
        purchaseBoardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/purchase-request")
    public ResponseEntity<PurchaseRequestResponseDto> createPurchaseRequest(
            @PathVariable UUID id,
            @RequestBody PurchaseRequestRequestDto purchaseRequestDto
    ) {
        PurchaseRequestResponseDto createdPurchaseRequestDto = purchaseRequestService.save(purchaseRequestDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPurchaseRequestDto);
    }

    @GetMapping("/{boardId}/purchase-request/{requestId}")
    public ResponseEntity<PurchaseRequestResponseDto> getPurchaseRequestById(
            @PathVariable UUID boardId,
            @PathVariable UUID requestId
    ) {
        PurchaseRequestResponseDto requestResponseDto = purchaseRequestService.findById(boardId, requestId);
        return ResponseEntity.ok(requestResponseDto);
    }

    @PostMapping("/{boardId}/purchase-request/{requestId}/approve")
    public ResponseEntity<PurchaseRequestResponseDto> approvePurchaseRequest(
            @PathVariable UUID boardId,
            @PathVariable UUID requestId
    ) {
        PurchaseRequestResponseDto requestResponseDto = purchaseRequestService.approveRequest(boardId, requestId);
        return ResponseEntity.ok(requestResponseDto);
    }

    @PostMapping("/{boardId}/purchase-request/{requestId}/update")
    public ResponseEntity<PurchaseRequestResponseDto> updatePurchaseRequest(
            @PathVariable UUID boardId,
            @PathVariable UUID requestId,
            @RequestBody PurchaseRequestRequestDto purchaseRequestRequestDto
    ) {
        PurchaseRequestResponseDto requestResponseDto = purchaseRequestService.updateRequest(boardId, requestId, purchaseRequestRequestDto);
        return ResponseEntity.ok(requestResponseDto);
    }

    @PostMapping("/{boardId}/purchase-request/{requestId}/reject")
    public ResponseEntity<PurchaseRequestResponseDto> rejectPurchaseRequest(
            @PathVariable UUID boardId,
            @PathVariable UUID requestId
    ) {
        PurchaseRequestResponseDto requestResponseDto = purchaseRequestService.rejectRequest(boardId, requestId);
        return ResponseEntity.ok(requestResponseDto);
    }

    @DeleteMapping("/{boardId}/purchase-request/{requestId}")
    public ResponseEntity<Void> deleteRequest(
            @PathVariable UUID boardId,
            @PathVariable UUID requestId
    ) {
        purchaseRequestService.deleteRequest(boardId, requestId);
        return ResponseEntity.noContent().build();
    }
}
