package com.juannobert.modelo.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.juannobert.modelo.dto.ProductDTO;
import com.juannobert.modelo.entities.Product;
import com.juannobert.modelo.repositories.ProductRepository;
import com.juannobert.modelo.services.exceptions.ResourceNotFoundException;
import com.juannobert.modelo.tests.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private Product product;
	private List<Product> list;
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		dependentId = 2L;
		nonExistingId = 3L;
		product = ProductFactory.createProduct();
		list = new ArrayList<>(List.of(product));
		
		when(repository.findAll()).thenReturn(list);
		
		when(repository.findById(existingId)).thenReturn(Optional.of(product));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
	}
	
	
	@Test
	public void findAllShouldReturnList() {
		
		List<ProductDTO> result = service.findAll();
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void findByIdShouldReturnEntityWhenIdExists() {
		ProductDTO dto = service.findById(existingId);
		
		Assertions.assertNotNull(dto);
		Assertions.assertNotNull(dto.getId());
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
				service.findById(nonExistingId);
		});
		
	}
	
	
	

}
