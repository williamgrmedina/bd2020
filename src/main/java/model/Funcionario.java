/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Medina
 */

public class Funcionario {
    private String login;
    private String senha;
    private String pnome;
    private String snome;
    private String email;
    private String cargo;
    private String setor;
    private Double salario;
    private Date data_efetivacao;
    private String gerente_login;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGerenteLogin() {
        return gerente_login;
    }

    public void setGerenteLogin(String gerente_login) {
        this.gerente_login = gerente_login;
    }
    
    public String getTipo() {
       return this.getClass().getSimpleName().toLowerCase();
    }
    
    /**
     * @return the pnome
     */
    public String getPNome() {
        return pnome;
    }

    /**
     * @param pnome the pnome to set
     */
    public void setPNome(String pnome) {
        this.pnome = pnome;
    }

    /**
     * @return the snome
     */
    public String getSNome() {
        return snome;
    }

    /**
     * @param snome the snome to set
     */
    public void setSNome(String snome) {
        this.snome = snome;
    }
    
    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the gargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }
	
	public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
	
    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the salario
     */
    public Double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(Double salario) {
        this.salario = salario;
    }

    /**
     * @return the data_efetivacao
     */
    public Date getData_efetivacao() {
        return data_efetivacao;
    }

    /**
     * @param data_efetivacao the data_efetivacao to set
     */
    public void setData_efetivacao(Date data_efetivacao) {
        this.data_efetivacao = data_efetivacao;
    }
    
}
