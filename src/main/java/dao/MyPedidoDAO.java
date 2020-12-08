/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import model.Pedido;

/**
 *
 * @author Medina
 */
public class MyPedidoDAO implements PedidoDAO {
	
	Connection connection;
	
	private final static String CREATE_PRESENCIAL_QUERY =
        "INSERT INTO restaurante.pedidos " +
		"(funcionario_login, comanda) " + //id é gerado automaticamente
		"VALUES (?, ?);";
	
	private final static String CREATE_ONLINE_QUERY =
        "INSERT INTO restaurante.pedidos " +
		"(cliente_login) " + //id é gerado automaticamente
		"VALUES (?);";
	
	/*private final static String CREATE_PROD_PEDIDO_QUERY =
		"INSERT INTO restaurante.produtos_pedidos " +
		"(pedido_idPedido, produto_idProduto, valor, qtd, observacao)" +
		"VALUES (?, ?, ?, ?, ?);";
	
	private final static String CREATE_ATENDIMENTO_QUERY =
		"INSERT INTO restaurante.atendimentos " +
		"(inicio, atend_idPedido, atend_idProduto)" +
		"VALUES (?, ?, ?);";*/
	
	private final static String READ_QUERY = 
		"SELECT * FROM restaurante.pedidos " +
		"WHERE idPedido = ?;";
	
	private final static String READ_ALL_QUERY = 
		"SELECT * FROM restaurante.pedidos;";
	
	private final static String READ_RELEVANT_QUERY =
		"SELECT * FROM restaurante.pedidos " +
		"WHERE cliente_login IS NOT NULL " +
		"OR funcionario_login = ?;";
	
	private final static String UPDATE_QUERY = 
		"UPDATE restaurante.pedidos " +
		"SET idPedido = ?, comanda = ?, cliente_login = ?, funcionario_login = ? " +
		"WHERE idPedido = ?;";
	
	private final static String UPDATE_COMANDA_QUERY =
		"UPDATE restaurante.pedidos " +
		"SET comanda = ?" +
		"WHERE comanda = ?;";
	
	private final static String UPDATE_FUNCIONARIO_QUERY =
		"UPDATE restaurante.pedidos " +
		"SET funcionario_login = ?" +
		"WHERE cliente_login = ?;";
	
	private final static String DELETE_QUERY =
		"DELETE FROM restaurante.pedidos " +
		"WHERE idPedido = ?;";

	public MyPedidoDAO(Connection connection) {
        this.connection = connection;
    }
	
	
	
	@Override
	public void create(Pedido p) throws SQLException { 
		//padrao pedido presencial. Para pedido online, utilize o metodo createOnline().
		try(PreparedStatement statement = connection.prepareStatement(CREATE_PRESENCIAL_QUERY)){
			statement.setString(1, p.getFuncionarioLogin());
			statement.setInt(2, p.getComanda());
			
			statement.executeUpdate();
		}
		catch (SQLException ex) {
           Logger.getLogger(MyPedidoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
		   throw new SQLException("Erro ao inserir pedido.");
		}
	}
	
	@Override
	public void createOnline(Pedido p) throws SQLException { 
		try(PreparedStatement statement = connection.prepareStatement(CREATE_ONLINE_QUERY)){
			statement.setString(1, p.getClienteLogin());
			statement.executeUpdate();
		}
		catch (SQLException ex) {
           Logger.getLogger(MyPedidoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
		   throw new SQLException("Erro ao inserir pedido.");
		}
	}

	@Override
	public Pedido read(Integer id) throws SQLException {
		Pedido p = new Pedido();
		
		try(PreparedStatement statement = connection.prepareStatement(READ_QUERY)){
			statement.setInt(1, id);
			
			try(ResultSet result = statement.executeQuery()){
				if(result.next()){
					p.setId(result.getInt("idPedido"));
					p.setComanda(result.getInt("comanda"));
					p.setClienteLogin(result.getString("cliente_login"));
					p.setFuncionarioLogin(result.getString("funcionario_login"));
					return p;
				}
				else{
					throw new SQLException("Erro ao visualizar pedido: pedido não encontrado.");
				}
			}
				
		} catch (SQLException ex) {
           Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
		   if(ex.getMessage().equals("Erro ao visualizar pedido: pedido não encontrado.")){
               throw new SQLException("Erro ao visualizar pedido: pedido não encontrado.");
           }
		   else throw new SQLException("Erro ao visualizar pedido.");
		}
	}
	
	@Override
	public List<Pedido> readRelevant(Funcionario f) throws SQLException {
		List<Pedido> allPed = new ArrayList<>();
        
		try (PreparedStatement statement = connection.prepareStatement(READ_RELEVANT_QUERY)) {
            statement.setString(1, f.getLogin());
			
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Pedido  p = new Pedido();
                p.setId(result.getInt("idProduto"));
				p.setComanda(result.getInt("comanda"));
				p.setClienteLogin(result.getString("cliente_login"));
				p.setFuncionarioLogin(result.getString("funcionario_login"));
				allPed.add(p);
			}
			return allPed;
		}
		catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar funcionários.");
		}
	}

	@Override
	public void update(Pedido p) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)){
			statement.setInt(1, p.getId());
			statement.setInt(2, p.getComanda());
			statement.setString(3, p.getClienteLogin());
			statement.setString(4, p.getFuncionarioLogin());
			
			statement.executeUpdate();
			
		}catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar pedido: um campo obrigatório está em branco.");
            }
			else throw new SQLException("Erro ao atualizar pedido.");
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            
            statement.setInt(1, id);
			statement.executeUpdate();
			
		} catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			throw new SQLException("Erro ao remover pedido.");
        }
	}

	@Override
	public List<Pedido> all() throws SQLException {
		List<Pedido> allPed = new ArrayList<>();
        
		try (PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {
            
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Pedido  p = new Pedido();
                p.setId(result.getInt("idProduto"));
				p.setComanda(result.getInt("comanda"));
				p.setClienteLogin(result.getString("cliente_login"));
				p.setFuncionarioLogin(result.getString("funcionario_login"));
				allPed.add(p);
			}
			return allPed;
		}
		catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar funcionários.");
		}
	}
		
	
	
}
