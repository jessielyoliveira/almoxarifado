package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.UnidadeMedida;
import com.example.repository.UnidadeMedidaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UnidadeMedidaService {

	@Autowired
	private UnidadeMedidaRepository unidadeMedidaRepository;

	public List<UnidadeMedida> findAll() {
		return unidadeMedidaRepository.findAll();
	}

	public Optional<UnidadeMedida> findOne(Integer id) {
		return unidadeMedidaRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public UnidadeMedida save(UnidadeMedida entity) {
		return unidadeMedidaRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(UnidadeMedida entity) {
		unidadeMedidaRepository.delete(entity);
	}

}
	
