package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Categoria;
import com.example.model.Medida;
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
			String codigo = categoria.getCodigo();
			String nome = categoria.getNome().toLowerCase();
			if(nome.equals(categ.getNome().toLowerCase()) || codigo.equals(categ.getCodigo())) {
				return true;
			}
		}	
		return false;
	}
	
	public boolean empty() {
		List<Categoria> categorias = findAll();
		if(categorias.isEmpty()) {
			return true;
		}
		return false;
	}

}
	
