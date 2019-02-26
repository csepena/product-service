package com.microservice.productservice.controller;

import com.microservice.productservice.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping(value = "/")
    public List<Product> list() {
        return ProductStub.findAll();
    }

    @GetMapping(value = "/{id}")
    public Product get(@PathVariable Long id) {
        return ProductStub.get(id);
    }

    @PostMapping(value = "/")
    public Product create(@RequestBody Product product) {
        return ProductStub.create(product);
    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Product product) {
        ProductStub.update(id, product);
    }

    @DeleteMapping(value = "/{id}")
    public Product delete(@PathVariable Long id) {
        return ProductStub.delete(id);
    }
}
