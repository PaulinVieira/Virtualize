package com.paulotec.virtualize.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import com.paulotec.virtualize.dao.ClientesRepository;
import com.paulotec.virtualize.dao.PedidoRepository;
import com.paulotec.virtualize.dao.ProdutoRepository;
import com.paulotec.virtualize.entity.ItensCompra;
import com.paulotec.virtualize.entity.Pedido;
import com.paulotec.virtualize.entity.table_Cliente;
import com.paulotec.virtualize.entity.table_Compra;
import com.paulotec.virtualize.entity.table_Produtos;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Carrinho_Controller {

//definição das variáveis e métodos
	private static String caminhoImagens = "C:\\imagens\\";
	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
	private table_Compra compra = new table_Compra();
	private table_Cliente cliente = new table_Cliente();
	private ClientesRepository clienteRepository = new ClientesRepository();
	private ProdutoRepository produtoRepository = new ProdutoRepository();
	private PedidoRepository pedidoRepository = new PedidoRepository();

//definição dos métodos
	private void calcularTotal() {

		//antes de calcular o total, define o total como 0.
		compra.setValorTotal(0.);

		//aqui, passamos para por cada item do nosso array list da compra
		for (ItensCompra it : itensCompra) {

			//nesse laço, defino o valor total, como o próprio valor total, + o valor total do
			//item que está sendo incluso			
			compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
		}
	}

	private void somarQuantidade(Integer id_produto){

		//cada para item da minha compra
	  for (ItensCompra it : itensCompra){
		  //verifico se o item do meu arraylist é igual ao ID passado no Mapping
		  if(it.getTable_Produtos().getId_produto().equals(id_produto)){
	  //defino a quantidade atual(pego a quantidade atual e somo um)
	  it.setQuantidade(it.getQuantidade() + 1);
	  //a apartir daqui, faço o cálculo. Valor Total(ATUAL) + ((NOVA) quantidade * valor unitário do produto(ATUAL))
	  it.setValorTotal(0.);
	  it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
  }
}
	}

	//método para buscar o usuário pelo banco de dados. Conforme autenticação.
	private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();

		if (!(autenticado instanceof AnonymousAuthenticationToken)) {

			String usuario = autenticado.getName();
			cliente = clienteRepository.getClientesUsuario(usuario).get(0);
		}

	}

	@GetMapping("/carrinho")
	public ModelAndView showView(RedirectAttributes attrib) {

		ModelAndView mv = new ModelAndView("geral/carrinho");
		if((!itensCompra.isEmpty())){			
			mv.addObject("compra", compra);
			mv.addObject("listaItens", itensCompra);
			return mv;
		
		}
		attrib.addFlashAttribute("msgAlerta", "O carrinho está vazio. Impossível continuar");
		return mv;
		

	
	}

	@GetMapping("/finalizarCompra")
	public ModelAndView finalizarCompra(RedirectAttributes attrib) {


		if(!itensCompra.isEmpty()){
			buscarUsuarioLogado();
		ModelAndView mv = new ModelAndView("geral/finalizarCompra");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		mv.addObject("cliente", cliente);
		return mv;
		}
	
		return showView(attrib);		
	}

	@GetMapping("/salvarPedido")
	public String salvarPedido() {
		buscarUsuarioLogado();
		calcularTotal();
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setItensCompra(itensCompra);
		pedido.setTotalPedido(compra.getValorTotal());
		pedidoRepository.salvar(pedido);

		return "redirect:/consultarPedido";
	}

	@GetMapping("/consultarPedido")
	public ModelAndView consultarPedido() {
		ModelAndView mv = new ModelAndView("clientes/pedidosCliente");
		buscarUsuarioLogado();
		List<Pedido> listPedidos = pedidoRepository.getPedido(cliente.getCpf());
		mv.addObject("listPedidos", listPedidos);
		return mv;
	}
	
	@GetMapping("/consultarPedido/{idPedido}")
	public ModelAndView consultarDetalhes( @PathVariable Integer idPedido) {
		ModelAndView mv = new ModelAndView("clientes/pedidoItem");
		buscarUsuarioLogado();

		List<ItensCompra> listItens = pedidoRepository.getProdutosPedido(idPedido);
		mv.addObject("compra", compra);
		mv.addObject("listItens", listItens);
		return mv;
	}
	
	@GetMapping("/estoquista/pedidos")
	public ModelAndView backOfficePedido() {
		ModelAndView mv = new ModelAndView("estoquista/backofficePedidos");
		
		List<Pedido> listaPedidos = pedidoRepository.getTodosPedido();
		
		mv.addObject("listaPedidos", listaPedidos);
		
		return mv;
		
	}
	
	@GetMapping("/estoquista/pedidos/{idPedido}")
	public ModelAndView backOfficeDetalhes( @PathVariable Integer idPedido) {
		ModelAndView mv = new ModelAndView("estoquista/pedidoItem");

		List<ItensCompra> listItens = pedidoRepository.getProdutosPedido(idPedido);
		mv.addObject("compra", compra);
		mv.addObject("listItens", listItens);
		return mv;
	}
	
	@GetMapping("/estoquista/alterarPedido/{idPedido}")
	  public ModelAndView exibirAlterarPedido(@PathVariable("idPedido") int idPedido) {
	    ModelAndView mv = new ModelAndView("estoquista/backofficeAlterarPedidos");
		Pedido Pedido = pedidoRepository.getPedidoEspecifico(idPedido);
		
		mv.addObject("Pedido", Pedido);
	    return mv;
	  }
	
//método mais complexo. 
	@GetMapping("/alterarQuantidade/{id_produto}/{acao}")
	public String alterarQuantidade(@PathVariable Integer id_produto, @PathVariable Integer acao, RedirectAttributes attrib) {

		//cada para item da minha compra
		for (ItensCompra it : itensCompra) {
			
			//se a ação passada no mapping somo a Quantidade.
				if (acao.equals(1)) {
				somarQuantidade(id_produto);					
				//agora se a acao não for 1, e for 0, subtraio a quantidade
				
				} else if (acao == 0) {
					it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					//novamente o mesmo recálculo dos valores
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
					//se o usuario ir diminuindo a acao, até a quantidade chegar em 0
					//removo o produto e passo o método attrib de sucesso
					if (it.getQuantidade() == 0) {
						removerProduto(id_produto, attrib);										
				}
				break;
			}
			
			}
		return "redirect:/carrinho";
	}

	@GetMapping("/removerProduto/{id_produto}")
	public String removerProduto(@PathVariable Integer id_produto, RedirectAttributes redirAttr) {

		for (ItensCompra it : itensCompra) {
			if (it.getTable_Produtos().getId_produto().equals(id_produto)) {

				itensCompra.remove(it);
				redirAttr.addFlashAttribute("msgSucesso", "Produto removido do carrinho.");
				break;
			}

		}

		return "redirect:/carrinho";
	}
	
//método complexo para adicionar o Carrinho
	@GetMapping("/adicionarCarrinho/{id_produto}")
	public String adicionarCarrinho(@PathVariable int id_produto) {

		table_Produtos prod = produtoRepository.getProdutos(id_produto);
//defino um controle inicial
		int controle = 0;
//novamente, percorro todo o arrayList
		for (ItensCompra it : itensCompra) {
			//pego o id do item e verifico se é o mesmo passado pelo mapping(repositorio do produto)
			if (it.getTable_Produtos().getId_produto().equals(prod.getId_produto())) {
				//novamente
				it.setQuantidade(it.getQuantidade() + 1);
				it.setValorTotal(0.);
				it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				controle = 1;
				break;

			}
		}

		if (controle == 0) {
			ItensCompra item = new ItensCompra();
			item.setTable_Produtos(prod);
			item.setValorUnitario(prod.getPreco_venda());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));

			itensCompra.add(item);

		}
		return "redirect:/carrinho";
	}

	@GetMapping("/carrinho/mostrarImg/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}
	
	@PutMapping("/estoquista/alterarPedido/{idPedido}")
	  public ModelAndView alterarPedido(
	          @PathVariable("idPedido") int idPedido,
	          @ModelAttribute(value = "Pedido") Pedido p) {	
	    pedidoRepository.alterarPedido(p);
	    ModelAndView mv = new ModelAndView("redirect:/estoquista/pedidos");
	    return mv;
	  }


	

	

	

}