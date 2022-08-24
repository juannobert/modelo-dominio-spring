package com.juannobert.modelo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juannobert.modelo.dto.ProductDTO;
import com.juannobert.modelo.entities.Product;
import com.juannobert.modelo.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(){
		List<Product> list = repository.findAll();
		return list.stream()
				.map(x -> new ProductDTO(x))
				.collect(Collectors.toList());
	}
	
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		BeanUtils.copyProperties(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

}
