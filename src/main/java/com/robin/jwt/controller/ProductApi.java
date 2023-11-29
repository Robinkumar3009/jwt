
package com.robin.jwt.controller;
import java.net.URI;
import java.util.List;
 
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.robin.jwt.model.Product;
import com.robin.jwt.repository.ProductRepository;
 
@RestController
//@RequestMapping("/products")
public class ProductApi {
 
    @Autowired private ProductRepository repo;
     
    @GetMapping("/")
    public String hello() {
        return "hello";
    }
    
    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        Product savedProduct = repo.save(product);
        URI productURI = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(productURI).body(savedProduct);
    }
     
    @GetMapping("/products")
    public List<Product> list() {
        return repo.findAll();
    }
}