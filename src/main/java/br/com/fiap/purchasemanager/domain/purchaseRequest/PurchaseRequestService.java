package br.com.fiap.purchasemanager.domain.purchaseRequest;

import br.com.fiap.purchasemanager.application.dtos.PurchaseRequestRequestDto;
import br.com.fiap.purchasemanager.application.dtos.PurchaseRequestResponseDto;
import br.com.fiap.purchasemanager.domain.exceptions.NotFoundException;
import br.com.fiap.purchasemanager.domain.exceptions.PurchaseRequestException;
import br.com.fiap.purchasemanager.domain.purchaseBoard.PurchaseBoardStatus;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseBoardModel;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseRequestModel;
import br.com.fiap.purchasemanager.infrastructure.repositories.PurchaseBoardRepository;
import br.com.fiap.purchasemanager.infrastructure.repositories.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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

        PurchaseBoardModel purchaseBoard = getPurchaseBoardById(purchaseBoardId);

        if (Objects.equals(purchaseBoard.getStatus(), PurchaseBoardStatus.CREATED.toString())) {
            purchaseBoard.setStatus(PurchaseBoardStatus.ON_GOING.toString());
            purchaseBoard = purchaseBoardRepository.save(purchaseBoard);
        }

        PurchaseRequestEntity purchaseRequest = purchaseRequestDto.toPurchaseRequestEntity();
        PurchaseRequestModel purchaseRequestModel = purchaseRequest.toPurchaseRequestModel(purchaseBoard);

        PurchaseRequestEntity savedPurchaseRequest = purchaseRequestRepository.save(purchaseRequestModel).toPurchaseRequestEntity();

        return savedPurchaseRequest.toPurchaseRequestResponseDto(purchaseBoard.getId(), purchaseRequestModel.getOrder());
    }

    public PurchaseRequestResponseDto findById(UUID purchaseBoardId, UUID requestId) {

        try {
            PurchaseRequestModel purchaseRequest = getRequestByIdAndPurchaseBoard(purchaseBoardId, requestId);

            return purchaseRequest
                    .toPurchaseRequestEntity()
                    .toPurchaseRequestResponseDto(purchaseBoardId, purchaseRequest.getOrder());

        } catch (NullPointerException e) {
            throw new NotFoundException(MessageFormat.format("Purchase Board with id {0} not found", purchaseBoardId.toString()));
        }

    }

    public void deleteRequest(UUID purchaseBoardId, UUID requestId) {

        PurchaseRequestModel purchaseRequest = getRequestByIdAndPurchaseBoard(purchaseBoardId, requestId);

        if (Objects.equals(purchaseRequest.getStatus(), PurchaseRequestStatus.PENDING_APPROVAL.toString())) {
            purchaseRequestRepository.delete(purchaseRequest);
        } else {
            throw new PurchaseRequestException("Only PENDING APPROVAL requests can be deleted");
        }
    }

    public PurchaseRequestResponseDto updateRequest(UUID purchaseBoardId, UUID requestId, PurchaseRequestRequestDto purchaseRequestRequestDto) {

        PurchaseBoardModel purchaseBoard = getPurchaseBoardById(purchaseBoardId);
        PurchaseRequestEntity purchaseRequest = getRequestByIdAndPurchaseBoard(purchaseBoardId, requestId).toPurchaseRequestEntity();

        if (!Objects.equals(purchaseRequest.getStatus(), PurchaseRequestStatus.COMPLETED)) {

            purchaseRequest.changeItemDescription(purchaseRequestRequestDto.itemDescription());
            purchaseRequest.changeQuantity(purchaseRequestRequestDto.quantity());
            purchaseRequest.moveToPending();

            return purchaseRequestRepository.save(purchaseRequest.toPurchaseRequestModel(purchaseBoard))
                    .toPurchaseRequestEntity()
                    .toPurchaseRequestResponseDto(purchaseBoard.getId(), null);
        } else {
            throw new PurchaseRequestException("COMPLETED requests cannot be changed");
        }
    }

    public PurchaseRequestResponseDto rejectRequest(UUID purchaseBoardId, UUID requestId) {

        PurchaseBoardModel purchaseBoard = getPurchaseBoardById(purchaseBoardId);
        PurchaseRequestEntity purchaseRequest = getRequestByIdAndPurchaseBoard(purchaseBoardId, requestId)
                .toPurchaseRequestEntity();

        purchaseRequest.reject();

        purchaseRequest = purchaseRequestRepository.save(purchaseRequest.toPurchaseRequestModel(purchaseBoard))
                .toPurchaseRequestEntity();

        return purchaseRequest.toPurchaseRequestResponseDto(
                purchaseBoard.getId(),
                purchaseRequest.toPurchaseRequestModel(
                        purchaseBoard
                ).getOrder()
        );
    }

    public PurchaseRequestResponseDto approveRequest(UUID purchaseBoardId, UUID requestId) {

        PurchaseBoardModel purchaseBoard = getPurchaseBoardById(purchaseBoardId);
        PurchaseRequestEntity purchaseRequest = getRequestByIdAndPurchaseBoard(purchaseBoardId, requestId)
                .toPurchaseRequestEntity();

        purchaseRequest.approve();

        purchaseRequest = purchaseRequestRepository.save(purchaseRequest.toPurchaseRequestModel(purchaseBoard))
                .toPurchaseRequestEntity();

        return purchaseRequest.toPurchaseRequestResponseDto(
                purchaseBoard.getId(),
                purchaseRequest.toPurchaseRequestModel(
                        purchaseBoard
                ).getOrder()
        );
    }

    private PurchaseBoardModel getPurchaseBoardById(UUID purchaseBoardId) {
        return purchaseBoardRepository.findById(purchaseBoardId).orElseThrow( () ->
                        new NotFoundException(MessageFormat.format("Purchase Board with id {0} not found", purchaseBoardId.toString()))
                );
    }

    private PurchaseRequestModel getRequestByIdAndPurchaseBoard(UUID purchaseBoardId, UUID requestId) {
        return purchaseRequestRepository.findByPurchaseBoardIdAndId(purchaseBoardId, requestId);
    }
}
