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
public interface FuncionarioDAO extends DAOString<Funcionario> {    
	
    public void authenticate(Funcionario fun) throws SQLException, SecurityException;
    
}
