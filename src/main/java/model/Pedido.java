/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Medina
 */
public class Pedido {

    /*Esta é uma classe para a tabela pedidos.
	Para manipulacao de métodos e atributos como valor do pedido
	e data de atendimento, veja a classe PedidoInfo ou ProdutoPedido*/

    private int id;
    private int comanda;
    private String tipo;
    private String status;
    private String cliente_login;
    private String funcionario_login;
    private String obs;
    private Timestamp inicio_atd;
    private Timestamp fim_atd;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Timestamp getInicioAtd() {
        return inicio_atd;
    }

    public void setInicioAtd(Timestamp inicio_atd) {
        this.inicio_atd = inicio_atd;
    }

    public Timestamp getFimAtd() {
        return fim_atd;
    }

    public void setFimAtd(Timestamp fim_atd) {
        this.fim_atd = fim_atd;
    }

}
