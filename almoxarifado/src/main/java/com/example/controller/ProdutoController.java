package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Categoria;
import com.example.model.Produto;
import com.example.service.CategoriaService;
import com.example.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CategoriaService categoriaService; //categoria service

	// Primeira tela da pagina de Produtos
	@GetMapping
	public String index(Model model) {
		List<Produto> all = produtoService.findAll();
		model.addAttribute("listProduto", all);
		model.addAttribute("");
		return "produto/index";
	}
	
	// Tela de Show Produto
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Produto produto = produtoService.findOne(id).get();
			model.addAttribute("produto", produto);
		}
		return "produto/show";
	}

	// Tela com Formulario de New Produto
	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Produto entityProduto, 
			             @ModelAttribute Categoria entityModule) {
		// model.addAttribute("produto", entityProduto);
		List<Categoria> all = categoriaService.findAll();
		model.addAttribute("categorias", all);
		
		return "produto/form";
	}
	
	// Processamento do formulario New Produto (ou Alter Produto) 
	@PostMapping
	public String create(@Valid @ModelAttribute Produto entityProduto, 
			             @Valid @ModelAttribute Categoria entityModule,
			             BindingResult result, RedirectAttributes redirectAttributes) {
		Produto produto = null;
		String pagina_retorno = "redirect:/produtos/" ;
	
		try {
			produto = produtoService.save(entityProduto);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
			pagina_retorno = pagina_retorno + produto.getId();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		
		try {
			if (id != null) {
				List<Categoria> all = categoriaService.findAll();
				model.addAttribute("categorias", all);
				
				Produto entity = produtoService.findOne(id).get();
				model.addAttribute("produto", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "produto/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Produto entity, BindingResult result, 
			             RedirectAttributes redirectAttributes) {
		Produto produto = null;
		try {
			produto = produtoService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/produtos/" + produto.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Produto entity = produtoService.findOne(id).get();
				produtoService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/produtos/";
	}
	
	private static final String MSG_SUCESS_INSERT = "Produto inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Produto successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Produto successfully.";
	private static final String MSG_ERROR = "Erro na inserção do Produto";


}
