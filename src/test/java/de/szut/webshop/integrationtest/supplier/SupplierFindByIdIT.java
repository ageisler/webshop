package de.szut.webshop.integrationtest.supplier;

import de.szut.webshop.contact.ContactEntity;
import de.szut.webshop.supplier.SupplierEntity;
import de.szut.webshop.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

public class SupplierFindByIdIT extends AbstractIntegrationTest {
    @Test
    @Transactional
    void findById() throws Exception {

        var supplier1 = new SupplierEntity();
        supplier1.setName("Meier");
        var contact1 = new ContactEntity();
        contact1.setStreet("Hauptstraße");
        contact1.setPostcode("12345");
        contact1.setCity("Bremen");
        contact1.setPhone("+4912345");
        supplier1.setContact(contact1);

        this.supplierRepository.save(supplier1);

        this.mockMvc.perform(get("/store/supplier/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name", is("Meier")))
                .andExpect(jsonPath("street", is("Hauptstraße")))
                .andExpect(jsonPath("postcode", is("12345")))
                .andExpect(jsonPath("city", is("Bremen")))
                .andExpect(jsonPath("phone", is("+4912345")));
    }
}