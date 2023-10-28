package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.Order.valueObjects.PaymentConditionsVO;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PaymentConditionsDto(

        @NotBlank
        LocalDate dueTo,
        @NotBlank
        int installments,
        @NotBlank
        double totalValue
) {

    public PaymentConditionsVO toPaymentConditionsVO() {
        return new PaymentConditionsVO(
                this.dueTo(),
                this.installments(),
                this.totalValue()
        );
    }
}
