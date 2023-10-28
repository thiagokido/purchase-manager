package br.com.fiap.purchasemanager.infrastructure.models;

import br.com.fiap.purchasemanager.domain.order.valueObjects.SupplierVO;
import jakarta.persistence.Embeddable;

@Embeddable
public class SupplierModel {

    private String cnpj;
    private String name;

    public SupplierModel() {}

    public SupplierModel(String cnpj, String name) {
        this.cnpj = cnpj;
        this.name = name;
    }

    public SupplierVO toSupplierVO() {
        return new SupplierVO(
                this.cnpj,
                this.name
        );
    }
}
