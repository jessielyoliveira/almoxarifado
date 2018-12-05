package com.example.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull @Column(name = "codigo")
    private String codigo;

	@NotNull @Column(name = "nome")
    private String nome;
	
    @Override
	public String toString() {
		return nome;
	}
    
    @OneToMany(mappedBy="categoria", cascade = CascadeType.ALL)
    List<Produto> produtos;
    
	public void setCodigo(String codigo) {this.codigo = codigo;}
	public String getCodigo() {return codigo;}
	
	public void setNome(String nome) {this.nome = nome;}
	public String getNome() {return nome;}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	public List<Produto> getProdutos() { return produtos; }
	public void setProdutoss(List<Produto> produtos) { this.produtos = produtos; }
	
}