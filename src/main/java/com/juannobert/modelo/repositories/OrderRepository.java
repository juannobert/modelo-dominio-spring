package com.juannobert.modelo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juannobert.modelo.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
