package de.szut.webshop.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddArticleDto {
    @NotBlank(message="Designation is mandatory")
    private String designation;

    @NotNull
    private Double price;
}
