package com.juannobert.modelo.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juannobert.modelo.dto.ProductDTO;
import com.juannobert.modelo.entities.Product;
import com.juannobert.modelo.repositories.ProductRepository;
import com.juannobert.modelo.services.exceptions.DatabaseIntegrityException;
import com.juannobert.modelo.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	private final String ERROR_MESSAGE = "Product with id = %d not found";
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(){
		List<Product> list = repository.findAll();
		return list.stream()
				.map(x -> new ProductDTO(x))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(ERROR_MESSAGE, id)));
		return new ProductDTO(entity);
	}
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		BeanUtils.copyProperties(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id,ProductDTO dto) {
		try {
		Product entity = repository.getReferenceById(id);
		BeanUtils.copyProperties(dto, entity,"id");
		entity = repository.save(entity);
		return new ProductDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format(ERROR_MESSAGE, id));
		}
	}
	
	public void delete(Long id){
		try {
			repository.deleteById(id);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format(ERROR_MESSAGE, id));
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseIntegrityException("Database integrity violation on DELETE product with id = " + id);
		}
		
	}
	
	
}
