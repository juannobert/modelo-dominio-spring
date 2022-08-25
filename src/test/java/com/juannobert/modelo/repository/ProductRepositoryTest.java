package com.juannobert.modelo.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.juannobert.modelo.entities.Product;
import com.juannobert.modelo.repositories.ProductRepository;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long depententId;
	private long countProducts;
	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 2L;
		nonExistingId = 1000L;
		depententId = 1L;		
		countProducts = 3L;
	}
	
	@Test
	public void findAllShouldReturnAllProducts() {
		
		List<Product> list = repository.findAll();
		
		Assertions.assertNotNull(list);
		Assertions.assertEquals(countProducts, list.size());
		
	}
	
	@Test
	public void findbyIdShouldReturnOptionalNonEmptyWhenIdExist() {
		Optional<Product> product = repository.findById(existingId);
		
		Assertions.assertNotNull(product);
		Assertions.assertTrue(product.isPresent());
	}
	
	@Test
	public void findbyIdShouldReturnOptionalEmptyWhenIdDoesNotExists() {
		Optional<Product> product = repository.findById(nonExistingId);
		
		Assertions.assertFalse(product.isPresent());
		Assertions.assertTrue(product.isEmpty());
	}
	
	
	
	
}
