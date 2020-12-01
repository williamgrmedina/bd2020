/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Medina
 */
public class Produto {
	private int id;
	private String nome;
	private Double valor_compra;
	private Double valor_venda;
	private int qtd;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor_compra() {
		return valor_compra;
	}

	public void setValor_compra(Double valor_compra) {
		this.valor_compra = valor_compra;
	}

	public Double getValor_venda() {
		return valor_venda;
	}

	public void setValor_venda(Double valor_venda) {
		this.valor_venda = valor_venda;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
}
