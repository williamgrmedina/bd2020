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
public class Pedido {
	private int id;
	private int comanda;
	private String cliente_login;
	private String funcionario_login;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getComanda() {
		return comanda;
	}

	public void setComanda(int comanda) {
		this.comanda = comanda;
	}

	public String getClienteLogin() {
		return cliente_login;
	}

	public void setClienteLogin(String cliente_login) {
		this.cliente_login = cliente_login;
	}

	public String getFuncionarioLogin() {
		return funcionario_login;
	}

	public void setFuncionarioLogin(String funcionario_login) {
		this.funcionario_login = funcionario_login;
	}
}
