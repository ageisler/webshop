package de.szut.webshop.article;

import lombok.Data;

@Data
public class GetArticleDto {
    private Long aid;
    private String designation;
    private Double price;
    private String currency;
}
