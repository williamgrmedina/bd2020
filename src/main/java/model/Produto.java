/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Medina
 */
public class Produto {
	private int id;
	private String nome;
	private BigDecimal valor_compra;
	private BigDecimal valor_venda;
	private int qtd;

	public String getFormatted(BigDecimal value) {
		value = value.setScale(2, RoundingMode.HALF_UP);
		return "R$ " + value;
	}
	
	
	
	
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

	public BigDecimal getValor_compra() {
		return valor_compra;
	}

	public void setValor_compra(BigDecimal valor_compra) {
		this.valor_compra = valor_compra;
	}

	public BigDecimal getValor_venda() {
		return valor_venda;
	}

	public void setValor_venda(BigDecimal valor_venda) {
		this.valor_venda = valor_venda;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
}
