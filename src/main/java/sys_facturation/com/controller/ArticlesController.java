package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.Articles;
import sys_facturation.com.service.ArticleService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Articles>> listAll() {
        List<Articles> list = articleService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Articles article = articleService.findById(id);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Artículo con ID " + id + " no encontrado."));
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Articles articles) {
        articleService.insert(articles);
        return ResponseEntity.status(HttpStatus.CREATED).body(articles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Articles newData) {
        Articles article = articleService.findById(id);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Artículo con ID " + id + " no encontrado."));
        }
        article.setNombre(newData.getNombre());
        article.setDescripcion(newData.getDescripcion());
        article.setCodigo(newData.getCodigo());
        article.setPrecio_venta(newData.getPrecio_venta());
        article.setCategories(newData.getCategories());
        articleService.update(article);
        return ResponseEntity.ok(article);
    }
}
