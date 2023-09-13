package de.szut.webshop.supplier;

import lombok.Data;

@Data
public class GetSupplierDto {
    private Long sid;
    private String name;
    private String street;
    private String postcode;
    private String city;
    private String phone;
}
