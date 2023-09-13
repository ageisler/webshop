package de.szut.webshop.article;

import de.szut.webshop.exceptionhandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public ArticleEntity create(ArticleEntity newArticle) {
        return repository.save(newArticle);
    }

    public List<ArticleEntity> readAll(){
        return repository.findAll();
    }

    public ArticleEntity readById(long id) {
        Optional<ArticleEntity> articleOptional = repository.findById(id);
        if (articleOptional.isEmpty()) {
            throw new ResourceNotFoundException("Article not found on id = " + id);
        }
        return articleOptional.get();
    }

    public ArticleEntity readByDesignation(String designation){
        List<ArticleEntity> articles = readAll();
        for(ArticleEntity articleEntity: articles) {
            if(articleEntity.getDesignation() == designation) {
                return articleEntity;
            }
        }
        throw new ResourceNotFoundException("Article not found with designation = " + designation);
    }
}
