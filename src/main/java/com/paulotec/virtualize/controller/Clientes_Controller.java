package com.paulotec.virtualize.controller;

import com.paulotec.virtualize.dao.ClientesRepository;
import com.paulotec.virtualize.entity.table_Cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Clientes_Controller {

	@GetMapping("/cadastroCliente")
	public ModelAndView exibirCadastroCliente(){

		table_Cliente c = new table_Cliente();
		ModelAndView mv = new ModelAndView("clientes/cadastroCliente");

		mv.addObject("cliente", c);
		return mv;
	}


	@GetMapping("/clientes/editar/{id_cliente}")
	public ModelAndView editar(@PathVariable("id_cliente") Integer id_cliente,
			@ModelAttribute(value = "cliente") table_Cliente c) {

		ClientesRepository clientesRepository = new ClientesRepository();
		clientesRepository.alterarCliente(c);
		ModelAndView mv = new ModelAndView("clientes/cadastrar");
		return mv;
	}

	@PostMapping("/clientes/cadastroCliente")
	public String cadastrarCliente(@ModelAttribute(value = "cliente") table_Cliente c) {

		ClientesRepository clientesRepository = new ClientesRepository();
		clientesRepository.salvar(c);
		return "redirect:/finalizarCompra";
	}

	

}
