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
    identificadas por "tabela_gerentes" e "tabela_garcons".*/
    static final String[] INFO_FUNCIONARIOS =
        {"gerente", "gerente", "garcom", "garcom", "operador_caixa", "operador_caixa"};
    
    public Funcionario getFuncionario(String login) throws SQLException, NoSuchFieldException;
    public Funcionario getFuncionarioInstance(String cargo) throws SQLException;
    public void authenticate(Funcionario fun) throws SQLException, SecurityException;
    
}
