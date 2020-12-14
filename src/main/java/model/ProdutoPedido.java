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
public class ProdutoPedido implements FormatCurrency {
	private int idPedido;
	private int idProduto;
	private BigDecimal valor;
	private int qtd;
	Pedido pedido;
	Produto produto;
	Atendimento atd;

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	@Override
	public String getFormatted(BigDecimal value) {
		value = value.setScale(2, RoundingMode.HALF_UP);
		return "R$ " + value;
	}
	
}
