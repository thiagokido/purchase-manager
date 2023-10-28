package br.com.fiap.purchasemanager.infrastructure.models;

import br.com.fiap.purchasemanager.domain.Order.valueObjects.DeliveryAddressVO;
import jakarta.persistence.Embeddable;

@Embeddable
public class DeliveryAddressModel {

    private String street;
    private int number;
    private String zipcode;
    private String city;
    private String state;

    public DeliveryAddressModel() {}

    public DeliveryAddressModel(String street, int number, String zipcode, String city, String state) {
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.city = city;
        this.state = state;
    }

    public DeliveryAddressVO toDeliveryAddressVO() {
        return new DeliveryAddressVO(
                this.street,
                this.number,
                this.zipcode,
                this.city,
                this.state
        );
    }
}
