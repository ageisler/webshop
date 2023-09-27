package de.szut.webshop.mapping;

import de.szut.webshop.article.AddArticleDto;
import de.szut.webshop.article.ArticleEntity;
import de.szut.webshop.article.GetAllArticlesBySupplierIdDto;
import de.szut.webshop.article.GetArticleDto;
import de.szut.webshop.contact.ContactEntity;
import de.szut.webshop.supplier.AddSupplierDto;
import de.szut.webshop.supplier.GetSupplierDto;
import de.szut.webshop.supplier.SupplierEntity;
import de.szut.webshop.supplier.SupplierService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class MappingService {
    // Supplier
    public SupplierEntity mapAddSupplierDtoToSupplier(AddSupplierDto dto) {
        SupplierEntity newSupplier = new SupplierEntity();
        newSupplier.setName(dto.getName());
        ContactEntity newContact = new ContactEntity();
        newContact.setStreet(dto.getStreet());
        newContact.setPostcode(dto.getPostcode());
        newContact.setCity(dto.getCity());
        newContact.setPhone(dto.getPhone());
        newSupplier.setContact(newContact);
        newContact.setSupplier(newSupplier);
        return newSupplier;
    }

    public GetSupplierDto mapSupplierToGetSupplierDto(SupplierEntity supplier) {
        GetSupplierDto dto = new GetSupplierDto();
        dto.setSid(supplier.getSid());
        dto.setName(supplier.getName());

        if (supplier.getContact() != null) {
            dto.setStreet(supplier.getContact().getStreet());
            dto.setPostcode(supplier.getContact().getPostcode());
            dto.setCity(supplier.getContact().getCity());
            dto.setPhone(supplier.getContact().getPhone());
        }

        return dto;
    }

    public GetAllArticlesBySupplierIdDto mapSupplierToGetAllArticlesBySupplierIdDto(SupplierEntity supplier) {
        GetAllArticlesBySupplierIdDto allArticlesBySupplierId = new GetAllArticlesBySupplierIdDto();
        allArticlesBySupplierId.setSupplierId(supplier.getSid());
        allArticlesBySupplierId.setName(supplier.getName());
        //Convert Set of ArticleEntity to Set of GetArticleDto
        Set<ArticleEntity> articleEntities = supplier.getArticles();
        Set<GetArticleDto> articleDtoSet = new HashSet<>();

        for (ArticleEntity articleEntity: articleEntities){
            GetArticleDto newDto = new GetArticleDto();
            newDto.setAid(articleEntity.getAid());
            newDto.setDesignation(articleEntity.getDesignation());
            newDto.setPrice(articleEntity.getPrice());
            articleDtoSet.add(newDto);
        }
        allArticlesBySupplierId.setArticles(articleDtoSet);
        return allArticlesBySupplierId;
    }

    // Articles
    public ArticleEntity mapAddArticleDtoToArticle(AddArticleDto dto, SupplierEntity supplier) {
        ArticleEntity newArticle = new ArticleEntity();
        newArticle.setDesignation(dto.getDesignation());
        newArticle.setPrice(dto.getPrice());
        newArticle.setSupplier(supplier);
        return newArticle;
    }

    public GetArticleDto mapArticleToGetArticleDto(ArticleEntity article,String currency) {
        GetArticleDto dto = new GetArticleDto();
        dto.setAid(article.getAid());
        dto.setDesignation(article.getDesignation());
        dto.setPrice(article.getPrice());
        dto.setCurrency(currency);
        return dto;
    }

}
