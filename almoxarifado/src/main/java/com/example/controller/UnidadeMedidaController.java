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
import com.example.model.Produto;
import com.example.model.UnidadeMedida;
import com.example.service.UnidadeMedidaService;

@Controller
@RequestMapping("/medidas")
public class UnidadeMedidaController {

	private static final String MSG_SUCESS_INSERT = "Unidade de medida inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Unidade de medida successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted unidade de medida successfully.";
	private static final String MSG_ERROR = "Error.";

	@Autowired
	private UnidadeMedidaService unidadeMedidaService;
	

	@GetMapping
	public String index(Model model) {
		List<UnidadeMedida> all = unidadeMedidaService.findAll();
		model.addAttribute("listUnidadeMedida", all);
		return "medida/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			UnidadeMedida unidadeMedida = unidadeMedidaService.findOne(id).get();
			model.addAttribute("unidadeMedida", unidadeMedida);
		}
		return "medida/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute UnidadeMedida entityUnidadeMedida, @ModelAttribute Produto entityStudent) {
		// model.addAttribute("unidadeMedida", entityUnidadeMedida);
		
		return "medida/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute UnidadeMedida entity, BindingResult result, RedirectAttributes redirectAttributes) {
		UnidadeMedida unidadeMedida = null;
		try {
			unidadeMedida = unidadeMedidaService.save(entity);
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
		return "redirect:/medidas/" + unidadeMedida.getId();
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				UnidadeMedida entity = unidadeMedidaService.findOne(id).get();
				model.addAttribute("unidadeMedida", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "medida/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute UnidadeMedida entity, BindingResult result, RedirectAttributes redirectAttributes) {
		UnidadeMedida unidadeMedida = null;
		try {
			unidadeMedida = unidadeMedidaService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/medidas/" + unidadeMedida.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				UnidadeMedida entity = unidadeMedidaService.findOne(id).get();
				unidadeMedidaService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/medidas/";
	}

}
