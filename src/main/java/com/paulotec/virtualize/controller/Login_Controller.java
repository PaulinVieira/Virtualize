package com.paulotec.virtualize.controller;

import com.paulotec.virtualize.dao.ClientesRepository;
import com.paulotec.virtualize.entity.table_Cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Login_Controller {
	
	//login admin
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/login");
		
		return mv;
	}
	//acesso negado
	@GetMapping("/negado")
	public ModelAndView negado() {
		ModelAndView mv = new ModelAndView("/negado");
		
		return mv;
	}
	
	@GetMapping("/loginCliente")
	public ModelAndView exibirLoginCliente() {
		
		ModelAndView mv = new ModelAndView("/loginCliente");

		return mv;
		
	}


	





}
