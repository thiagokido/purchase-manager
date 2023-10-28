package br.com.fiap.purchasemanager.domain.Order.valueObjects;

import br.com.fiap.purchasemanager.application.dtos.DeliveryAddressDto;
import br.com.fiap.purchasemanager.infrastructure.models.DeliveryAddressModel;

public class DeliveryAddressVO {

    private String street;
    private int number;
    private String zipcode;
    private String city;
    private String state;

    public DeliveryAddressVO(String street, int number, String zipcode, String city, String state) {
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.city = city;
        this.state = state;
    }

    public DeliveryAddressModel toDeliveryAddressModel() {
        return new DeliveryAddressModel(
                this.street,
                this.number,
                this.zipcode,
                this.city,
                this.state
        );
    }

    public DeliveryAddressDto toDeliveryAddressDto() {
        return new DeliveryAddressDto(
                this.street,
                this.number,
                this.zipcode,
                this.city,
                this.state
        );
    }
}
