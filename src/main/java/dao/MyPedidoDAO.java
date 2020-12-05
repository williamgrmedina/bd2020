/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Pedido;

/**
 *
 * @author Medina
 */
public class MyPedidoDAO implements DAOInt<Pedido> {
	
	private final static String CREATE_ONLINE_QUERY =
        "INSERT INTO restaurante.pedidos " +
		"(cliente_login) " + //id é gerado automaticamente
		"VALUES (?);";
	
	private final static String CREATE_PRESENCIAL_QUERY =
        "INSERT INTO restaurante.pedidos " +
		"(funcionario_login, comanda) " + //id é gerado automaticamente
		"VALUES (?, ?);";
	
	private final static String CREATE_PROD_PEDIDO_QUERY =
		"INSERT INTO restaurante.produtos_pedidos " +
		"(pedido_idPedido, produto_idProduto, valor, qtd, observacao)" +
		"VALUES (?, ?, ?, ?, ?);";
	
	private final static String CREATE_ATENDIMENTO_QUERY =
		"INSERT INTO restaurante.atendimentos " +
		"(inicio, atend_idPedido, atend_idProduto)" +
		"VALUES (?, ?, ?);";

	private final static String UPDATE_COMANDA_QUERY =
		"UPDATE restaurante.pedidos " +
		"SET comanda = ?" +
		"WHERE comanda = ?;";
	
	private final static String REMOVE_QUERY =
		"DELETE FROM restaurante.atendimentos " +
		"WHERE atend_idPedido = ?;" +
		"DELETE FROM restaurante.produtos_pedidos " +
		"WHERE pedido_idPedido = ?;" +
		"DELETE FROM restaurante.pedidos " +
		"WHERE idPedido = ?;";

	@Override
	public void create(Pedido p) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Pedido read(Integer id) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void update(Pedido p) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void delete(Integer id) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<Pedido> all() throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
		
	
	
}
