/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import model.Cliente;

/**
 *
 * @author Medina
 */
public interface ClienteDAO extends DAOString<Cliente> {
	public void authenticate(Cliente cl) throws SQLException, SecurityException;
}
