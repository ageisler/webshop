package de.szut.webshop.contact;

import de.szut.webshop.supplier.SupplierEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="supplier_contact")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    private String street;

    @Column(name="zip")
    private String postcode;

    private String city;

    private String phone;

    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SupplierEntity supplier;
}
