package com.juannobert.modelo.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.juannobert.modelo.dto.ProductDTO;
import com.juannobert.modelo.entities.Product;
import com.juannobert.modelo.repositories.ProductRepository;
import com.juannobert.modelo.services.exceptions.DatabaseIntegrityException;
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
	void setUp() throws Exception {
		existingId = 1L;
		dependentId = 2L;
		nonExistingId = 3L;
		product = ProductFactory.createProduct();
		list = new ArrayList<>(List.of(product));

		when(repository.findAll()).thenReturn(list);

		when(repository.findById(existingId)).thenReturn(Optional.of(product));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		doNothing().when(repository).deleteById(existingId);
		doThrow(EntityNotFoundException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		when(repository.save(any())).thenReturn(product);

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
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void deleteShouldThrowDatabaseIntegrityExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseIntegrityException.class, () ->{
			service.delete(dependentId);
		});
	}
	
	@Test
	public void saveShouldPersistEntityWithAutoincrementAndReturnProductDTOWhenIdIsNull() {
		ProductDTO dto = ProductFactory.createProductDTO();
		dto = service.insert(dto);
		
		Assertions.assertNotNull(dto);
		Assertions.assertNotNull(dto.getId());
	}
	
}
