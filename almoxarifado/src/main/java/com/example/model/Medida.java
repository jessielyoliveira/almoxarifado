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
@Table(name = "medida")
public class Medida implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    @NotNull @Column(name = "nome")
    private String nome;
	
    @Override
	public String toString() {
		return nome;
	}
    
    @OneToMany(mappedBy="medida", cascade = CascadeType.ALL)
    List<Produto> produtos;
    
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
    
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	
	public List<Produto> getProdutos() { return produtos; }
	public void setProdutos(List<Produto> produtos) { this.produtos = produtos;	}
	
}