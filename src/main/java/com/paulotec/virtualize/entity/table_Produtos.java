package com.paulotec.virtualize.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;


@Data
public class table_Produtos {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_produto;
	private String descricao;
	private String codigo_produto;
	private double preco_custo;
	private double preco_venda;
	private int quantidade;
	private String endereco_imagem;
	private String endereco_imagem2;
	private String endereco_imagem3;
	private String endereco_imagem4;
	private String endereco_imagem5;
	private String descricao_detalhada;
	private int ativo = 1;
}
