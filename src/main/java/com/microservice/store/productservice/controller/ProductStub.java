package com.microservice.store.productservice.controller;

import com.microservice.store.productservice.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductStub {
    private static Map<Long, Product> idProductMap = new HashMap<>();
    private static Long idIndex = 3L;

    static {
        idProductMap.put(1L, new Product(1L, "Product 1", "Product Description1"));
        idProductMap.put(2L, new Product(2L, "Product 2", "Product Description2"));
        idProductMap.put(3L, new Product(3L, "Product 3", "Product Description3"));
    }

    public static List<Product> findAll() {
        return new ArrayList<>(idProductMap.values());
    }

    public static Product get(Long id) {
        return idProductMap.get(id);
    }

    public static Product create(Product product) {
        idIndex += 1;
        product.setId(idIndex);
        idProductMap.put(idIndex, product);
        return product;
    }

    public static void update(Long id, Product product) {
        idProductMap.put(id, product);
    }

    public static Product delete(Long id) {
        return idProductMap.remove(id);
    }
}
