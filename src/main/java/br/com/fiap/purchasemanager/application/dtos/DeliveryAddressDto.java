package br.com.fiap.purchasemanager.application.dtos;

import br.com.fiap.purchasemanager.domain.order.valueObjects.DeliveryAddressVO;
import jakarta.validation.constraints.NotBlank;

public record DeliveryAddressDto(

        @NotBlank
        String street,
        @NotBlank
        int number,
        @NotBlank
        String zipcode,
        @NotBlank
        String city,
        @NotBlank
        String state
) {
    public DeliveryAddressVO toDeliveryAddressVO() {
        return new DeliveryAddressVO(
                this.street(),
                this.number(),
                this.zipcode(),
                this.city(),
                this.state()
        );
    }
}
