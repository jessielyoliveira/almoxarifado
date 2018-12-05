package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "quantidade")
	private int quantidade;
	
	@ManyToOne
	@JoinColumn (name = "medida")
	private Medida medida;

	@ManyToOne
	@JoinColumn(name="categoria")
	private Categoria categoria;
	
	public void setCodigo(String codigo) { this.codigo = codigo; }
	public String getCodigo() { return codigo; }

	public void setNome(String nome) { this.nome = nome; }
	public String getNome() { return nome; }

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	public Medida getMedida() {	return medida; }
	public void setMedida(Medida medida) { this.medida = medida; }

	public Categoria getCategoria() { return categoria; }
	public void setCategoria(Categoria categoria) { this.categoria = categoria; }

}