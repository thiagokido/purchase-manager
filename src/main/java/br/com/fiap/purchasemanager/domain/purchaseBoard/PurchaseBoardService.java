package br.com.fiap.purchasemanager.domain.purchaseBoard;

import br.com.fiap.purchasemanager.application.dtos.PurchaseBoardRequestDto;
import br.com.fiap.purchasemanager.application.dtos.PurchaseBoardResponseDto;
import br.com.fiap.purchasemanager.infrastructure.models.PurchaseBoardModel;
import br.com.fiap.purchasemanager.infrastructure.repositories.PurchaseBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseBoardService {

    private final PurchaseBoardRepository purchaseBoardRepository;

    public PurchaseBoardService(PurchaseBoardRepository purchaseBoardRepository) {
        this.purchaseBoardRepository = purchaseBoardRepository;
    }

    public Page<PurchaseBoardResponseDto> findAll(Pageable pageable) {
        Page<PurchaseBoardModel> purchaseBoards = purchaseBoardRepository.findAll(pageable);
        return purchaseBoards
                .map(PurchaseBoardModel::toPurchaseBoardEntity)
                .map(PurchaseBoardEntity::toPurchaseBoardResponseDto);
    }

    public PurchaseBoardResponseDto save(PurchaseBoardRequestDto purchaseBoardDto) {

        PurchaseBoardEntity purchaseBoard = purchaseBoardDto.toPurchaseBoardEntity();
        PurchaseBoardModel purchaseBoardModel = purchaseBoard.toPurchaseBoardModel();

        return purchaseBoardRepository.save(purchaseBoardModel)
                .toPurchaseBoardEntity()
                .toPurchaseBoardResponseDto();
    }

    public PurchaseBoardResponseDto findById(UUID id) {

        PurchaseBoardEntity purchaseBoard = purchaseBoardRepository.findById(id)
                .orElseThrow()
                .toPurchaseBoardEntity();

        return purchaseBoard.toPurchaseBoardResponseDto();
    }

    public void deleteBoard(UUID id) {

        PurchaseBoardEntity purchaseBoard = purchaseBoardRepository.findById(id).orElseThrow().toPurchaseBoardEntity();

        if (purchaseBoard.getPurchaseRequests().isEmpty()) {

            purchaseBoardRepository.deleteById(purchaseBoard.toPurchaseBoardModel().getId());
        } else {
            throw new RuntimeException("Não foi possível excluir o quadro pois ele contém requisições");
        }
    }
}
