/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import model.Funcionario;

/**
 *
 * @author Medina
 */
public interface FuncionarioDAO extends DAO<Funcionario> {    
    
    /*esta é uma lista de tuplas de "<nome do cargo>", "<nome da tabela mysql>".
    A lista {"gerente", "tabela_gerentes", "garcom", "tabela_garcons"} representa 
    dois cargos (gerente e garcom), que possuem respectivas tabelas mySQL
    identificadas por "tabela_gerentes" e "tabela_garcons".
    Listas devem *obrigatoriamente* seguir o padrão 
    "<nome do cargo>", "<nome da tabela mysql>"*/
    static final String[] LISTA_CARGOS_MYSQLTABLENAME =
        {"gerente", "gerente", "garcom", "garcom", "operador_caixa", "operador_caixa"};
    
    public void authenticate(Funcionario fun) throws SQLException, SecurityException;
    
}
