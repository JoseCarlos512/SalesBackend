package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sys_facturation.com.service.ArticleService;
import java.util.Collection;

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

}
