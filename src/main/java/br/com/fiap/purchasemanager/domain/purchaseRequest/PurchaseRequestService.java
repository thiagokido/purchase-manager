package br.com.fiap.purchasemanager.domain.purchaseRequest;

import br.com.fiap.purchasemanager.application.dtos.PurchaseRequestRequestDto;
import br.com.fiap.purchasemanager.application.dtos.PurchaseRequestResponseDto;
import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardStatus;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseBoardModel;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseRequestModel;
import br.com.fiap.purchasemanager.infrastructure.repositories.PurchaseBoardRepository;
import br.com.fiap.purchasemanager.infrastructure.repositories.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final PurchaseBoardRepository purchaseBoardRepository;

    public PurchaseRequestService(
            PurchaseRequestRepository purchaseRequestRepository,
            PurchaseBoardRepository purchaseBoardRepository
    ) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.purchaseBoardRepository = purchaseBoardRepository;
    }

    public PurchaseRequestResponseDto save(PurchaseRequestRequestDto purchaseRequestDto, UUID purchaseBoardId) {

        PurchaseBoardModel purchaseBoard = purchaseBoardRepository.findById(purchaseBoardId).orElseThrow();

        if (Objects.equals(purchaseBoard.getStatus(), PurchaseBoardStatus.CREATED.toString())) {
            purchaseBoard.setStatus(PurchaseBoardStatus.ON_GOING.toString());
            purchaseBoard = purchaseBoardRepository.save(purchaseBoard);
        }

        PurchaseRequestEntity purchaseRequest = purchaseRequestDto.toPurchaseRequestEntity();
        PurchaseRequestModel purchaseRequestModel = toPurchaseRequestModel(purchaseRequest, purchaseBoard);

        PurchaseRequestEntity savedPurchaseRequest = purchaseRequestRepository.save(purchaseRequestModel).toPurchaseRequestEntity();

        return toPurchaseRequestResponseDto(savedPurchaseRequest, purchaseBoard);
    }

    public PurchaseRequestResponseDto findById(UUID boardId, UUID requestId) {

        PurchaseRequestEntity purchaseRequest = purchaseRequestRepository.findByPurchaseBoardIdAndId(boardId, requestId).toPurchaseRequestEntity();
        return purchaseRequest.toPurchaseRequestResponseDto(boardId);
    }

    private PurchaseRequestModel toPurchaseRequestModel(PurchaseRequestEntity purchaseRequest, PurchaseBoardModel purchaseBoard) {
        return new PurchaseRequestModel(
                purchaseRequest.getItemDescription(),
                purchaseRequest.getQuantity(),
                purchaseRequest.getStatus().toString(),
                purchaseBoard
        );
    }

    private PurchaseRequestResponseDto toPurchaseRequestResponseDto(PurchaseRequestEntity purchaseRequest, PurchaseBoardModel purchaseBoard) {
        return new PurchaseRequestResponseDto(
                purchaseRequest.getId(),
                purchaseRequest.getItemDescription(),
                purchaseRequest.getQuantity(),
                purchaseRequest.getStatus().toString(),
                purchaseBoard.getId()
        );
    }
}
