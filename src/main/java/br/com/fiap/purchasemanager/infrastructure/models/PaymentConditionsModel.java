package br.com.fiap.purchasemanager.infrastructure.models;

import br.com.fiap.purchasemanager.domain.order.valueObjects.PaymentConditionsVO;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class PaymentConditionsModel {

    private LocalDate dueDate;
    private int installments;
    private double totalValue;

    public PaymentConditionsModel() {}

    public PaymentConditionsModel(LocalDate dueDate, int installments, double totalValue) {
        this.dueDate = dueDate;
        this.installments = installments;
        this.totalValue = totalValue;
    }

    public PaymentConditionsVO toPaymentConditionsVO() {
        return new PaymentConditionsVO(
                this.dueDate,
                this.installments,
                this.totalValue
        );
    }
}
