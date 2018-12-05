package com.example.repository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Medida;

@Repository
public interface MedidaRepository extends JpaRepository<Medida, Integer> {
	
}