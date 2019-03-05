package com.microservice.store.productservice.controller;

import com.microservice.store.productservice.model.Product;
import com.microservice.store.productservice.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/")
    public List<Product> list() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Product get(@PathVariable Long id) {
        return findByIdOrElseThrowException(id);
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
    public Product delete(@PathVariable Long id) {
        Product product = findByIdOrElseThrowException(id);
        productRepository.delete(product);
        return product;
    }

    private Product findByIdOrElseThrowException(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with Id - " + id));
    }
}
