/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Cliente;
import model.Funcionario;
import model.Pedido;

/**
 *
 * @author Medina
 */
public interface PedidoDAO extends DAOInt<Pedido> {
	public void createOnline(Pedido p) throws SQLException;
	public List<Pedido> readPedidos(Funcionario f) throws SQLException;
        public List<Pedido> readPedidos(Cliente cl) throws SQLException;
	public int getLastPedido() throws SQLException;
	public void updateStatus(int idPedido, String status) throws SQLException;
}
