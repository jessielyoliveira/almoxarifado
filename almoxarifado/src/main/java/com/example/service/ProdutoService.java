package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Categoria;
import com.example.model.Medida;
import com.example.model.Produto;
import com.example.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}
	
	public Optional<Produto> findOne(Integer id) {
		return produtoRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Produto save(Produto entity) {
		return produtoRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Produto entity) {
		produtoRepository.delete(entity);
	}
	
	public boolean existe(Produto prod) {
		List<Produto> produtos = findAll();	
		for (Produto produto : produtos) {
			String codigo = produto.getCodigo();
			String nome = produto.getNome().toLowerCase();
			if(nome.equals(prod.getNome().toLowerCase()) || codigo.equals(prod.getCodigo())) {
				return true;
			}
		}	
		return false;
	}
	
	
}
	
