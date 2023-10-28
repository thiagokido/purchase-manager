package br.com.fiap.purchasemanager.domain.order.valueObjects;

import br.com.fiap.purchasemanager.application.dtos.SupplierDto;
import br.com.fiap.purchasemanager.infrastructure.models.SupplierModel;

public class SupplierVO {

    private String cnpj;
    private String name;

    public SupplierVO(String cnpj, String name) {
        this.cnpj = cnpj;
        this.name = name;
    }

    public SupplierModel toSupplierModel() {
        return new SupplierModel(
                this.cnpj,
                this.name
        );
    }

    public SupplierDto toSupplierDto() {
        return new SupplierDto(
                this.cnpj,
                this.name
        );
    }
}
