package com.example.controller;

import java.util.List;

import javax.persistence.Id;
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
import com.example.model.Medida;
import com.example.service.CategoriaService;
import com.example.service.ProdutoService;
import com.example.service.MedidaService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private MedidaService medidaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public String index(Model model) {
		List<Produto> all = produtoService.findAll();
		model.addAttribute("listProduto", all);
		//model.addAttribute("");
		return "produto/index";
	}
	
	@GetMapping("/saida")
	public String saida(Model model) {
		List<Produto> allProdutos = produtoService.findAll();
		model.addAttribute("produtos", allProdutos);
		return "produto/saida";
	}
		
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Produto produto = produtoService.findOne(id).get();
			model.addAttribute("produto", produto);
		}
		return "produto/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Produto entityProduto, @ModelAttribute Medida entityMedida, @ModelAttribute Categoria entityCategoria) {
		// model.addAttribute("produto", entityProduto);
		List<Medida> allMedida = medidaService.findAll();
		model.addAttribute("medidas", allMedida);
		
		List<Categoria> allCategoria = categoriaService.findAll();
		model.addAttribute("categorias", allCategoria);
		
		return "produto/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Produto entityProduto, 
						@Valid @ModelAttribute Medida entityMedida, 
						@Valid @ModelAttribute Categoria entityCategoria, BindingResult result, 
			RedirectAttributes redirectAttributes) {
		Produto produto = null;
//		String pagina_retorno = "redirect:/produtos" ;
	
		try {
			if(medidaService.empty()) {
				redirectAttributes.addFlashAttribute("warning", "É preciso cadastrar unidades de medida.");
				return "redirect:/medidas/new";
			} else if(categoriaService.empty()) {
				redirectAttributes.addFlashAttribute("warning", "É preciso cadastrar categorias.");
				return "redirect:/categorias/new";
			} else if(!produtoService.existe(entityProduto)) {
				produtoService.save(entityProduto);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
			} else {
				redirectAttributes.addFlashAttribute("warning", MSG_WARNING);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		
		return "redirect:/produtos";
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		
		try {
			if (id != null) {
				List<Medida> allMedida = medidaService.findAll();
				model.addAttribute("medidas", allMedida);
				
				List<Categoria> allCategoria = categoriaService.findAll();
				model.addAttribute("categorias", allCategoria);
				
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
			produtoService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/produtos" /*+ produto.getId()*/;
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
		return "redirect:/produtos";
	}
	
	private static final String MSG_SUCESS_INSERT = "Produto inserido.";
	private static final String MSG_SUCESS_UPDATE = "Produto modificado.";
	private static final String MSG_SUCESS_DELETE = "Produto apagado.";
	private static final String MSG_ERROR = "Erro na inserção do produto";
	private static final String MSG_WARNING = "Produto já cadastrado.";


}
