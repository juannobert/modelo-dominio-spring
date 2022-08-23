package com.juannobert.modelo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juannobert.modelo.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
