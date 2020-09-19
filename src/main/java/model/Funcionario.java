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

public abstract class Funcionario extends HashSetLogin {
    private String nome;
    private String login;
    private String senha;
    private Double salario;
    private Date data_efetivacao;
    
    public String getTipo() {
       return this.getClass().getSimpleName().toLowerCase();
    }
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
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
