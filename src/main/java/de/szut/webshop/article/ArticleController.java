package de.szut.webshop.article;

import de.szut.webshop.exceptionhandling.CurrencycodeNotFoundException;
import de.szut.webshop.exchangerateservice.ExchangeRateService;
import de.szut.webshop.exchangerateservice.RateDto;
import de.szut.webshop.mapping.MappingService;
import de.szut.webshop.supplier.SupplierEntity;
import de.szut.webshop.supplier.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/store/article")
public class ArticleController {
    private final ArticleService service;
    private final SupplierService supplierService;
    private final MappingService mappingService;

    private final ExchangeRateService exchangeRateService;

    public ArticleController(ArticleService service, SupplierService supplierService, MappingService mappingService, ExchangeRateService exchangeRateService) {
        this.service = service;
        this.supplierService = supplierService;
        this.mappingService = mappingService;
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<GetArticleDto> createArticle(@Valid @RequestBody final AddArticleDto dto, @PathVariable("id") final Long supplierId) {
        SupplierEntity supplier = this.supplierService.readById(supplierId);
        ArticleEntity newArticle = this.mappingService.mapAddArticleDtoToArticle(dto, supplier);
        newArticle = this.service.create(newArticle);
        final GetArticleDto request = this.mappingService.mapArticleToGetArticleDto(newArticle, "EUR");
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetArticleDto>> findAllArticles(@RequestParam(required = false, defaultValue = "") String designation) {
        List<GetArticleDto> dtoList = new LinkedList<>();
        if (!designation.isEmpty()) {
            final var article = this.service.readByDesignation(designation);
            final GetArticleDto dto = this.mappingService.mapArticleToGetArticleDto(article, "EUR");
            dtoList.add(dto);
        } else {
            List<ArticleEntity> all = this.service.readAll();
            for (ArticleEntity articleEntity : all) {
                dtoList.add(this.mappingService.mapArticleToGetArticleDto(articleEntity, "EUR"));
            }
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArticleDto> getArticleById(@PathVariable final Long id, @RequestParam(required = false) final String currency) {
        final var article = this.service.readById(id);
        final GetArticleDto dto;
        if(currency != null && !currency.isEmpty()) {
            RateDto rate = this.exchangeRateService.convert("EUR", currency, article.getPrice());
            if (rate.getResult() == 0.0) {
                throw new CurrencycodeNotFoundException("Currencycode don’t exist!");
            }
            dto = this.mappingService.mapArticleToGetArticleDto(article, currency);
            dto.setPrice(rate.getResult());
        } else {
            dto = this.mappingService.mapArticleToGetArticleDto(article, "EUR");
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
