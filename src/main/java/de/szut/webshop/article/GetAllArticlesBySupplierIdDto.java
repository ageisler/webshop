package de.szut.webshop.article;

import lombok.Data;

import java.util.Set;

@Data
public class GetAllArticlesBySupplierIdDto {
    private Long supplierId;
    private String name;
    private Set<GetArticleDto> articles;
}
