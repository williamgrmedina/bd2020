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
import model.Produto;

/**
 *
 * @author Medina
 */
public class MyProdutoDAO implements ProdutoDAO {
	
	Connection connection;
	
	private final static String CREATE_QUERY = 
		"INSERT INTO restaurante.produtos " +
		"(nome, valor_de_compra, valor_de_venda, qtd) " +
		"VALUES (?, ?, ?, ?);";
	
	private final static String READ_QUERY =
		"SELECT * FROM restaurante.produtos " +
		"WHERE idProduto = ?;";
	
	private final static String UPDATE_QUERY =
		"UPDATE restaurante.produtos " +
		"SET nome = ? valor_de_compra = ?, valor_de_venda = ?, qtd = ?;" +
		"WHERE idProduto = ?;";
	
	private final static String DELETE_QUERY =
		"DELETE FROM restaurante.produtos " +
		"WHERE idProduto = ?;";
	
	private final static String READ_ALL_QUERY =
		"SELECT * FROM restaurante.produtos; ";
	
	private final static String READ_ALL_COMERCIALIZAVEL_QUERY =
		"SELECT idProduto, nome, valor_de_compra, MIN(valor_de_venda) as valor_de_venda, qtd " +
		"FROM restaurante.produtos " +
		"GROUP BY nome;";
	
	private final static String UPDATE_NAME_QUERY =
		"UPDATE restaurante.produtos " +
		"SET nome = ? " +
		"WHERE idProduto = ?;";
	
	private final static String UPDATE_BUY_VALUE_QUERY =
		"UPDATE restaurante.produtos " +
		"SET valor_de_compra = ? " +
		"WHERE idProduto = ?;";
	
	private final static String UPDATE_SELL_VALUE_QUERY =
		"UPDATE restaurante.produtos " +
		"SET valor_de_venda = ? " +
		"WHERE idProduto = ?;";
	
	private final static String UPDATE_QTY_QUERY =
		"UPDATE restaurante.produtos " +
		"SET qtd = ? " +
		"WHERE idProduto = ?;";
	
	
	public MyProdutoDAO(Connection connection) {
        this.connection = connection;
    }

	@Override
	public void create(Produto p) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)){
			statement.setString(1, p.getNome());
			statement.setBigDecimal(2, p.getValor_compra());
			statement.setBigDecimal(3, p.getValor_venda());
			statement.setInt(4, p.getQtd());
			
			statement.executeUpdate();
		}
		catch (SQLException ex) {
           Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
		   throw new SQLException("Erro ao inserir produto.");
		}
	}

	@Override
	public Produto read(Integer id) throws SQLException {
		Produto prod = new Produto();
		
		try(PreparedStatement statement = connection.prepareStatement(READ_QUERY)){
			statement.setInt(1, id);
			
			try(ResultSet result = statement.executeQuery()){
				if(result.next()){
					prod.setId(result.getInt("idProduto"));
					prod.setNome(result.getString("nome"));
					prod.setValor_compra(result.getBigDecimal("valor_de_compra"));
					prod.setValor_venda(result.getBigDecimal("valor_de_venda"));
					return prod;
				}
				else{
					throw new SQLException("Erro ao visualizar produto: produto não encontrado.");
				}
			}
				
		} catch (SQLException ex) {
           Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
		   if(ex.getMessage().equals("Erro ao visualizar produto: produto não encontrado.")){
               throw new SQLException("Erro ao visualizar produto: produto não encontrado.");
           }
		   else throw new SQLException("Erro ao visualizar produto.");
		}
	}

	@Override
	public void update(Produto p) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)){
			statement.setString(1, p.getNome());
			statement.setBigDecimal(2, p.getValor_compra());
			statement.setBigDecimal(3, p.getValor_venda());
			statement.setInt(4, p.getQtd());
			
			statement.executeUpdate();
			
		}catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar produto: um campo obrigatório está em branco.");
            }
			else throw new SQLException("Erro ao atualizar produto.");
		}
	}
	
	
	@Override
	public void update_nome(Produto p) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(UPDATE_NAME_QUERY)){
			statement.setString(1, p.getNome());
			statement.setInt(2, p.getId());
			
			statement.executeUpdate();
			
		}catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar produto: um campo obrigatório está em branco.");
            }
			else throw new SQLException("Erro ao atualizar produto.");
		}
	}
	
	@Override
	public void update_valor_compra(Produto p) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(UPDATE_BUY_VALUE_QUERY)){
			statement.setBigDecimal(1, p.getValor_compra());
			statement.setInt(2, p.getId());
			
			statement.executeUpdate();
			
		}catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar produto: um campo obrigatório está em branco.");
            }
			else throw new SQLException("Erro ao atualizar produto.");
		}
	}
	
	@Override
	public void update_valor_venda(Produto p) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(UPDATE_SELL_VALUE_QUERY)){
			statement.setBigDecimal(1, p.getValor_venda());
			statement.setInt(2, p.getId());
			
			statement.executeUpdate();
			
		}catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar produto: um campo obrigatório está em branco.");
            }
			else throw new SQLException("Erro ao atualizar produto.");
		}
	}
	
	@Override
	public void update_qtd(Produto p) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(UPDATE_QTY_QUERY)){
			statement.setInt(1, p.getQtd());
			statement.setInt(2, p.getId());
			
			statement.executeUpdate();
			
		}catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar produto: um campo obrigatório está em branco.");
            }
			else throw new SQLException("Erro ao atualizar produto.");
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            
            statement.setInt(1, id);
			statement.executeUpdate();
			
		} catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			throw new SQLException("Erro ao remover produto.");
        }
	}

	@Override
	public List<Produto> all() throws SQLException {
		List<Produto> allProd = new ArrayList<>();
        
		try (PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {
            
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Produto p = new Produto();
                p.setId(result.getInt("idProduto"));
				p.setNome(result.getString("nome"));
				p.setValor_compra(result.getBigDecimal("valor_de_compra"));
				p.setValor_venda(result.getBigDecimal("valor_de_venda"));
				p.setQtd(result.getInt("qtd"));
				allProd.add(p);
			}
			return allProd;
		}
		catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar funcionários.");
		}
	}
	
	@Override
	public List<Produto> getComercializaveis() throws SQLException { 
		List<Produto> allProd = new ArrayList<>();
        
		try (PreparedStatement statement = connection.prepareStatement(READ_ALL_COMERCIALIZAVEL_QUERY)) {
            
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Produto p = new Produto();
                p.setId(result.getInt("idProduto"));
				p.setNome(result.getString("nome"));
				p.setValor_venda(result.getBigDecimal("preco"));
				p.setQtd(result.getInt("qtd"));
				allProd.add(p);
			}
			return allProd;
		}
		catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar funcionários.");
		}
	}
}
