package com.juannobert.modelo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juannobert.modelo.dto.ProductDTO;
import com.juannobert.modelo.entities.Product;
import com.juannobert.modelo.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public List<ProductDTO> findAll(){
		List<Product> list = repository.findAll();
		return list.stream()
				.map(x -> new ProductDTO(x))
				.collect(Collectors.toList());
	}
	

}
