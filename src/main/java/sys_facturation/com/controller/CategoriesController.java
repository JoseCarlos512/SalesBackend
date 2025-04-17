package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.Categories;
import sys_facturation.com.service.CategoriesService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    // --------------------- METHOD LIST --------------------------
    // link: http://localhost:9696/api/categories/list-All

    @GetMapping("/list-All")
    public ResponseEntity<?> listAll() {
        Collection<Categories> listAll = categoriesService.findAll();
        if (listAll.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ERROR: Categories not found");
        }
        return ResponseEntity.ok(listAll);
    }

    // --------------------- METHOD CREATE CATEGORIES --------------------------
    // link: http://localhost:9696/api/categories/create

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody Categories categories) {
        Map<String, Object> response = new HashMap<>();
        try {
            categoriesService.insert(categories);
            response.put("Categoria creada:", categories);
            response.put("message", "Categoria creada correctamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Status 201 Created
        } catch (Exception e) {
            response.put("error", "Error al crear Categoria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Status 500 Error
        }
    }
    // --------------------- METHOD UPDATE CATEGORIES --------------------------
    // link: http://localhost:9696/api/categories/update_article/

    @PutMapping("/update_article/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Categories newCategories) {
        Map<String, Object> response = new HashMap<>();
        Categories categories = categoriesService.findById(id);
        if (categories != null) {

            categories.setNombre(newCategories.getNombre());
            categories.setDescripcion(newCategories.getDescripcion());
            categories.setUpdate_at(newCategories.getUpdate_at());

            categoriesService.update(categories);
            response.put("Mensaje: ", "Categiria actualizada correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Error: ", "No se encuentra la categoria con el ID: " +id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
