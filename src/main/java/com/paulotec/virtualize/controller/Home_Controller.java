package com.paulotec.virtualize.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.paulotec.virtualize.dao.ProdutoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Home_Controller {

	private static String caminhoImagens = "C:\\imagens\\";

	@GetMapping("/home")
	public ModelAndView home() {

		ModelAndView mv = new ModelAndView("geral/home");

		ProdutoRepository produtoRepository = new ProdutoRepository();

		// mv.addObject("listaImagens", listaImagens);
		mv.addObject("listaProdutos", produtoRepository.getTable_Produtos());

		return mv;

	}
	
	
	@GetMapping("/")
	public ModelAndView showHome() {

		ModelAndView mv = new ModelAndView("geral/home");

		ProdutoRepository produtoRepository = new ProdutoRepository();

		// mv.addObject("listaImagens", listaImagens);
		mv.addObject("listaProdutos", produtoRepository.getTable_Produtos());

		return mv;

	}
	

	@GetMapping("/viewImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

}