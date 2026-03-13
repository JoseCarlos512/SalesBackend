package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.Categories;
import sys_facturation.com.service.CategoriesService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<Categories>> listAll() {
        List<Categories> list = categoriesService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Categories category = categoriesService.findById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Categoría con ID " + id + " no encontrada."));
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Categories> create(@RequestBody Categories categories) {
        categoriesService.insert(categories);
        return ResponseEntity.status(HttpStatus.CREATED).body(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Categories newData) {
        Categories category = categoriesService.findById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Categoría con ID " + id + " no encontrada."));
        }
        category.setNombre(newData.getNombre());
        category.setDescripcion(newData.getDescripcion());
        categoriesService.update(category);
        return ResponseEntity.ok(category);
    }
}
