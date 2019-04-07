package com.microservice.store.productservice.controller;

import com.microservice.store.productservice.model.Product;
import com.microservice.store.productservice.repository.ProductRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @InjectMocks
    private ProductController controller;

    @Mock
    private ProductRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void list_should_return_all_products() {
        List<Product> products = Arrays.asList(new Product(1L, "Product1", "Description1"), new Product(2L, "Product2", "Description2"));
        when(repository.findAll())
                .thenReturn(products);
        ResponseEntity<List<Product>> responseEntity = controller.list();

        verify(repository).findAll();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertTrue(responseEntity.hasBody());
        List<Product> responseEntityBody = responseEntity.getBody();
        assertNotNull(responseEntityBody);
        assertFalse(responseEntityBody.isEmpty());
        assertEquals(2, responseEntityBody.size());
    }

    @Test
    public void list_should_return_empty_result() {
        when(repository.findAll())
                .thenReturn(Collections.emptyList());
        ResponseEntity<List<Product>> responseEntity = controller.list();

        verify(repository).findAll();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        List<Product> responseEntityBody = responseEntity.getBody();
        assertNotNull(responseEntityBody);
        assertTrue(responseEntityBody.isEmpty());
    }

    @Test(expected = EntityNotFoundException.class)
    public void get_should_throw_EntityNotFoundException() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        controller.get(1L);
    }

    @Test
    public void get_should_return_product_for_given_id() {
        long productId = 1L;
        when(repository.findById(productId))
                .thenReturn(Optional.of(new Product(productId, "Product1", "Description1")));
        ResponseEntity<Product> responseEntity = controller.get(productId);

        verify(repository).findById(productId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        Product responseEntityBody = responseEntity.getBody();
        assertNotNull(responseEntityBody);
        MatcherAssert.assertThat(responseEntityBody.getId(), Matchers.is(productId));
    }

    @Test
    public void create() {
        //TODO: Implement this
    }

    @Test
    public void update() {
        //TODO: Implement this
    }

    @Test
    public void delete() {
        //TODO: Implement this
    }
}