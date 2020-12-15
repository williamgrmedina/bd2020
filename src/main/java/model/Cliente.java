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
public class Cliente {

	private String login;
	private String senha;
	private String pnome;
	private String snome;
	private String email;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPNome() {
		return pnome;
	}

	public void setPNome(String pnome) {
		this.pnome = pnome;
	}

	public String getSNome() {
		return snome;
	}

	public void setSNome(String snome) {
		this.snome = snome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
