package com.juannobert.modelo.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;

import com.juannobert.modelo.entities.Product;
import com.juannobert.modelo.repositories.ProductRepository;
import com.juannobert.modelo.tests.ProductFactory;

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
	public void findbyIdShouldReturnOptionalNonEmptyWhenIdExists() {
		Optional<Product> product = repository.findById(existingId);
		
		Assertions.assertNotNull(product);
		Assertions.assertTrue(product.isPresent());
	}
	
	@Test
	public void findbyIdShouldReturnOptionalEmptyWhenIdDoesNotExist() {
		Optional<Product> product = repository.findById(nonExistingId);
		
		Assertions.assertFalse(product.isPresent());
		Assertions.assertTrue(product.isEmpty());
	}
	
	@Test
	public void saveShouldPersistEntityWithAutoincrementWhenIdIsNull() {
		Product product = ProductFactory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product);
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(product.getId(), countProducts + 1);
	}
	
	@Test
	public void deleteSholdDeleteEntityWhenIdExists() {
		
		repository.deleteById(existingId);
		
		Optional<Product> product = repository.findById(existingId);
		
		Assertions.assertTrue(product.isEmpty());
		
	}
	
	@Test
	public void deleteSholdThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () ->{
			repository.deleteById(nonExistingId);
		});
	}
	
	
}
