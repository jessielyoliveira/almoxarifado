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



@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
	
    @Override
	public String toString() {
		return name;
	}
    
    @OneToMany(mappedBy="categoria", cascade = CascadeType.ALL)
    List<Produto> produtos;
    
	public void setCode(String code) {this.code = code;}
	public String getCode() {return code;}
	
	public void setName(String name) {this.name = name;}
	public String getName() {return name;}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutoss(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	
}