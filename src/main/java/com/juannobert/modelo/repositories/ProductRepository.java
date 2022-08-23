package com.juannobert.modelo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juannobert.modelo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
