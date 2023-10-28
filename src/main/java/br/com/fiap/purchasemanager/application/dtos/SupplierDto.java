package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.order.valueObjects.SupplierVO;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record SupplierDto(

        @NotBlank
        @CNPJ
        String cnpj,
        @NotBlank
        String name
) {
    public SupplierVO toSupplierVO() {
        return new SupplierVO(
            this.cnpj(),
            this.name()
        );
    }
}
