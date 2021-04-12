package com.paulotec.virtualize.controller;

import java.util.List;

import com.paulotec.virtualize.dao.UsuarioRepository;
import com.paulotec.virtualize.entity.table_Usuarios;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Usuario_Controller {

  @GetMapping("/administrativo")
  public ModelAndView mostrarTela() {

    ModelAndView mv = new ModelAndView("administrativo/backofficeUsuariosConsulta");
    UsuarioRepository usuarioRepository = new UsuarioRepository();
    List<table_Usuarios> table_Usuarios = usuarioRepository.getTable_Usuarios();
    mv.addObject("usuarios", table_Usuarios);
    return mv;
  }

  @GetMapping("/administrativo/Novo")
  public ModelAndView exibirCadastro() {
    table_Usuarios u = new table_Usuarios();
    ModelAndView mv = new ModelAndView("administrativo/backofficeUsuariosNovo");
    mv.addObject("usuario", u);
    return mv;
  }

  @GetMapping("/administrativo/{id_usuario}")
  public ModelAndView exibirAlterarUsuario(@PathVariable("id_usuario") int id_usuario) {
    ModelAndView mv = new ModelAndView("administrativo/backofficeUsuariosAlterar");
    UsuarioRepository usuarioRepository = new UsuarioRepository();
    table_Usuarios u = usuarioRepository.getUsuarios(id_usuario);
    mv.addObject("usuario", u);
    return mv;
  }

  @PutMapping("/administrativo/{id_usuario}")
  public ModelAndView alterarUsuario(
          @PathVariable("id_usuario") int id_usuario,
          @ModelAttribute(value = "usuario") table_Usuarios u) {

    UsuarioRepository usuarioRepository = new UsuarioRepository();
    usuarioRepository.alterarUsuario(u);
    ModelAndView mv = new ModelAndView("redirect:/administrativo");
    return mv;
  }

  @PostMapping("/administrativo/Novo")
  public ModelAndView adicionarUsuario(
          @ModelAttribute(value = "usuario") table_Usuarios u) {	  

    UsuarioRepository usuarioRepository = new UsuarioRepository();
    usuarioRepository.salvarUsuario(u);
    ModelAndView mv = new ModelAndView("redirect:/administrativo");
    return mv;
  }

  @PostMapping("/administrativo/{id_usuario}")
  public ModelAndView removeUsuario(@PathVariable("id_usuario") int id_usuario, RedirectAttributes attrib) {
	  
	  UsuarioRepository usuarioRepository = new UsuarioRepository();
    usuarioRepository.inativarUsuario(id_usuario);
    attrib.addFlashAttribute("message", "Usuário removido com sucesso.");
    ModelAndView mv = new ModelAndView("redirect:/administrativo");
    return mv;
  }

}
