package com.juannobert.modelo.tests;

import com.juannobert.modelo.entities.Product;

public class ProductFactory {

	public static Product createProduct() {
		return new Product(1L,"TV",2500.00);
		
	}
}
