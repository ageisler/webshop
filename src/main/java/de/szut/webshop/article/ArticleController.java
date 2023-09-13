package de.szut.webshop.article;

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

    public ArticleController(ArticleService service, SupplierService supplierService, MappingService mappingService) {
        this.service = service;
        this.supplierService = supplierService;
        this.mappingService = mappingService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<GetArticleDto> createArticle(@Valid @RequestBody final AddArticleDto dto, @PathVariable("id") final Long supplierId) {
        SupplierEntity supplier = this.supplierService.readById(supplierId);
        ArticleEntity newArticle = this.mappingService.mapAddArticleDtoToArticle(dto, supplier);
        newArticle = this.service.create(newArticle);
        final GetArticleDto request = this.mappingService.mapArticleToGetArticleDto(newArticle);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetArticleDto>> findAllArticles() {
        List<ArticleEntity> all = this.service.readAll();
        List<GetArticleDto> dtoList = new LinkedList<>();
        for(ArticleEntity articleEntity: all) {
            dtoList.add(this.mappingService.mapArticleToGetArticleDto(articleEntity));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<GetArticleDto> getArticleById(@PathVariable final Long id) {
        final var article = this.service.readById(id);
        final GetArticleDto dto = this.mappingService.mapArticleToGetArticleDto(article);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("ByDesignation") //Möglicherweise andere Umsetzung an Stelle von Param
    public ResponseEntity<GetArticleDto> getArticleByDesignation(@RequestParam String designation) {
        final var article = this.service.readByDesignation(designation);
        final GetArticleDto dto = this.mappingService.mapArticleToGetArticleDto(article);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
