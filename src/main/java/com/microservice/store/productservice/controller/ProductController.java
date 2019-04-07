package com.microservice.store.productservice.controller;

import com.microservice.store.productservice.model.Product;
import com.microservice.store.productservice.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/")
    public ResponseEntity<List<Product>> list() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        Product product = findByIdOrElseThrowException(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping(value = "/")
    public Product create(@RequestBody Product product) {
        return productRepository.saveAndFlush(product);
    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Product product) {
        Product persistedEntity = findByIdOrElseThrowException(id);
        BeanUtils.copyProperties(product, persistedEntity, "id");
        productRepository.saveAndFlush(persistedEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        Product product = findByIdOrElseThrowException(id);
        productRepository.delete(product);
        return ResponseEntity.ok(product);
    }

    private Product findByIdOrElseThrowException(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with Id - " + id));
    }
}
