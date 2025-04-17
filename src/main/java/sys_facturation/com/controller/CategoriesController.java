package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sys_facturation.com.service.CategoriesService;
import java.util.Collection;

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
}
