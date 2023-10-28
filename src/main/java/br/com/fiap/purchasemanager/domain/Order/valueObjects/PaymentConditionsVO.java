package br.com.fiap.purchasemanager.domain.Order.valueObjects;

import br.com.fiap.purchasemanager.application.dtos.PaymentConditionsDto;
import br.com.fiap.purchasemanager.infrastructure.models.PaymentConditionsModel;

import java.time.LocalDate;

public class PaymentConditionsVO {

    private LocalDate dueDate;
    private int installments;
    private double totalValue;

    public PaymentConditionsVO(LocalDate dueDate, int installments, double totalValue) {
        this.dueDate = dueDate;
        this.installments = installments;
        this.totalValue = totalValue;
    }

    public PaymentConditionsModel toPaymentConditionsModel() {
        return new PaymentConditionsModel(
                this.dueDate,
                this.installments,
                this.totalValue
        );
    }

    public PaymentConditionsDto toPaymentConditionsDto() {
        return new PaymentConditionsDto(
                this.dueDate,
                this.installments,
                this.totalValue
        );
    }
}
