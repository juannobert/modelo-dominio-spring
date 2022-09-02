package com.juannobert.modelo.resources;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.juannobert.modelo.dto.ProductDTO;
import com.juannobert.modelo.services.ProductService;
import com.juannobert.modelo.services.exceptions.DatabaseIntegrityException;
import com.juannobert.modelo.services.exceptions.ResourceNotFoundException;
import com.juannobert.modelo.tests.ProductFactory;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceTests {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private ProductDTO productDTO;
	private List<ProductDTO> list;
	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 10L;
		dependentId = 3L;
		
		productDTO = ProductFactory.createProductDTO();
		list = new ArrayList<>(List.of(productDTO));
		
		when(service.findById(existingId)).thenReturn(productDTO);
		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		when(service.findById(dependentId)).thenThrow(DatabaseIntegrityException.class);
	}
	
	
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO dto = service.findById(existingId);
		
		Assertions.assertNotNull(dto);
		
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			service.findById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdShouldThrowDatabaseIntegrityExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseIntegrityException.class, () ->{
			service.findById(dependentId);
		});
	}
	
	
	
	
}
