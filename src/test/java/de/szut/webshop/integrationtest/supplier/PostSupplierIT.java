package de.szut.webshop.integrationtest.supplier;

import de.szut.webshop.testcontainers.AbstractIntegrationTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostSupplierIT extends AbstractIntegrationTest {
    @Test
    void postSupplier() throws Exception {
        String content = """
                {
                         "name": "Meier",
                         "street": "Benquestraße 50",
                         "postcode": "28209",
                         "city": "Bremen",
                         "phone": "01637122020"
                }
                """;

        final var contentAsString = this.mockMvc.perform(post("/store/supplier").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", is("Meier")))
                .andExpect(jsonPath("street", is("Benquestraße 50")))
                .andExpect(jsonPath("city", is("Bremen")))
                .andExpect(jsonPath("postcode", is("28209")))
                .andExpect(jsonPath("phone", is("01637122020")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("sid").toString());

        final var loadedEntity = supplierRepository.findById(id);
        assertThat(loadedEntity.get().getName()).isEqualTo("Meier");
        assertThat(loadedEntity.get().getContact().getStreet()).isEqualTo("Benquestraße 50");
        assertThat(loadedEntity.get().getContact().getPostcode()).isEqualTo("28209");
        assertThat(loadedEntity.get().getContact().getCity()).isEqualTo("Bremen");
        assertThat(loadedEntity.get().getContact().getPhone()).isEqualTo("01637122020");
    }
}

