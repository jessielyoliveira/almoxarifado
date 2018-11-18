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

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

	private static final String MSG_SUCESS_INSERT = "Categoria inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Categoria successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Categoria successfully.";
	private static final String MSG_ERROR = "Error.";

	@Autowired
	private CategoriaService categoriaService;
	

	@GetMapping
	public String index(Model model) {
		List<Categoria> all = categoriaService.findAll();
		model.addAttribute("listCategoria", all);
		return "categoria/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Categoria categoria = categoriaService.findOne(id).get();
			model.addAttribute("categoria", categoria);
		}
		return "categoria/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Categoria entityCategoria, @ModelAttribute Produto entityStudent) {
		// model.addAttribute("categoria", entityCategoria);
		
		return "categoria/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Categoria entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Categoria categoria = null;
		try {
			categoria = categoriaService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			System.out.println("Throwable:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		return "redirect:/categorias/" + categoria.getId();
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				Categoria entity = categoriaService.findOne(id).get();
				model.addAttribute("categoria", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "categoria/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Categoria entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Categoria categoria = null;
		try {
			categoria = categoriaService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/categorias/" + categoria.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Categoria entity = categoriaService.findOne(id).get();
				categoriaService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/categorias/";
	}

}
