package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Categoria;
import com.example.repository.CategoriaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Optional<Categoria> findOne(Integer id) {
		return categoriaRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Categoria save(Categoria entity) {
		return categoriaRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Categoria entity) {
		categoriaRepository.delete(entity);
	}
	
	public boolean existe(Categoria categ) {
		List<Categoria> categorias = findAll();	
		for (Categoria categoria : categorias) {
			String temp = categoria.getNome().toLowerCase();
			if(temp.equals(categ.getNome().toLowerCase())) {
				return true;
			}
		}	
		return false;
	}

}
	
