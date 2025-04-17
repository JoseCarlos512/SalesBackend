package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.Articles;
import sys_facturation.com.service.ArticleService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    ArticleService articleService;

    // --------------------- METHOD LIST --------------------------
    // link: http://localhost:9696/api/articles/list-All

    @RequestMapping("/list-All")
    public ResponseEntity<?> listAll(){
        Collection<Articles> listAll = articleService.findAll();
        if(listAll.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ERROR: Categories not found");
        }
        return  ResponseEntity.ok(listAll);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody Articles articles) {
        Map<String, Object> response = new HashMap<>();
        try {
            articleService.insert(articles);
            response.put("Articulo creado:", articles);
            response.put("message", "Articulo creado correctamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Status 201 Created
        } catch (Exception e) {
            response.put("error", "Error al crear categoria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Status 500 Error
        }
    }

    @PutMapping("/update_article/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Articles newArticles) {
        Map<String, Object> response = new HashMap<>();
        Articles articles = articleService.findById(id);
        if (articles != null) {

            articles.setNombre(newArticles.getNombre());
            articles.setDescripcion(newArticles.getDescripcion());
            articles.setCodigo(newArticles.getCodigo());
            articles.setPrecio_venta(newArticles.getPrecio_venta());
            articles.setUpdate_at(newArticles.getUpdate_at());

            // FK
            articles.setCategories(newArticles.getCategories());

            articleService.update(articles);
            response.put("Mensaje", "Articulo actualizado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Error: ", "No se encuentra Articulo con el ID" +id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
