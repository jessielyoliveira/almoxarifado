package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Medida;
import com.example.repository.MedidaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MedidaService {

	@Autowired
	private MedidaRepository medidaRepository;

	public List<Medida> findAll() {
		return medidaRepository.findAll();
	}

	public Optional<Medida> findOne(Integer id) {
		return medidaRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Medida save(Medida entity) {
		return medidaRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Medida entity) {
		medidaRepository.delete(entity);
	}
	
	public boolean existe(Medida unidade) {
		List<Medida> medidas = findAll();	
		for (Medida medida : medidas) {
			String temp = medida.getNome().toLowerCase();
			if(temp.equals(unidade.getNome().toLowerCase())) {
				return true;
			}
		}	
		return false;
	}
	
	public boolean empty() {
		List<Medida> medidas = findAll();
		if(medidas.isEmpty()) {
			return true;
		}
		return false;
	}

}
	
