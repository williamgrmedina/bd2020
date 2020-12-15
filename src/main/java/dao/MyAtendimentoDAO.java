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
import model.Atendimento;

/**
 *
 * @author Medina
 */
public class MyAtendimentoDAO implements AtendimentoDAO {

	Connection connection;
	
	private final static String CREATE_QUERY = 
		"INSERT INTO restaurante.atendimentos " +
		"(inicio, atend_idPedido, atend_idProduto) " +
		"VALUES (CURRENT_TIMESTAMP(), ?, ?);";

	private final static String READ_QUERY = 
		"SELECT * FROM restaurante.atendimentos " +
		"WHERE atend_idPedido = ? AND atend_idProduto = ?;";
	
	private final static String UPDATE_QUERY =
		"UPDATE restaurante.atendimentos " +
		"SET atend_idPedido = ?, atend_idProduto = ?, fim = ? " +
		"WHERE idAtendimento = ?;";
	
	private final static String FINALIZE_QUERY =
		"UPDATE restaurante.atendimentos " +
		"SET fim = CURRENT_TIMESTAMP() " +
		"WHERE atend_idPedido = ? AND atend_idProduto = ?;";
	
	private final static String DELETE_QUERY =
		"DELETE FROM restaurante.atendimentos " +
		"WHERE atend_idPedido = ? AND atend_idProduto = ?;";
	
	private final static String READ_ALL_QUERY =
		"SELECT * FROM restaurante.atendimentos; ";
	
	private final static String READ_BY_PEDIDO_QUERY =
		"SELECT * FROM restaurante.atendimentos " +
		"WHERE atend_idPedido = ? " +
		"ORDER BY inicio;";
	
	public MyAtendimentoDAO(Connection connection) {
        this.connection = connection;
    }
	
	@Override
	public void create(Atendimento atd) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)){
			statement.setInt(1, atd.getIdPedido());
			statement.setInt(2, atd.getIdProduto());
			
			statement.executeUpdate();
		}
		catch (SQLException ex) {
           Logger.getLogger(MyAtendimentoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
		   throw new SQLException("Erro ao inserir atendimento.");
		}
	}

	@Override
	public Atendimento read(Integer idPedido, Integer idProduto) throws SQLException {
		Atendimento atd = new Atendimento();
		
		try(PreparedStatement statement = connection.prepareStatement(READ_QUERY)){
			statement.setInt(1, idPedido);
			statement.setInt(2, idProduto);
			
			try(ResultSet result = statement.executeQuery()){
				if(result.next()){
					atd.setInicio(result.getString("inicio"));
					atd.setFim(result.getString("fim"));
					atd.setIdPedido(result.getInt("atend_idPedido"));
					atd.setIdProduto(result.getInt("atend_idProduto"));
					return atd;
				}
				else{
					throw new SQLException("Erro ao visualizar atendimento: atendimento não encontrado.");
				}
			}
				
		} catch (SQLException ex) {
           Logger.getLogger(MyAtendimentoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
		   if(ex.getMessage().equals("Erro ao visualizar atendimento: atendimento não encontrado.")){
               throw new SQLException("Erro ao visualizar atendimento: atendimento não encontrado.");
           }
		   else throw new SQLException("Erro ao visualizar atendimento.");
		}
	}

	@Override
	public void update(Atendimento atd) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)){
			statement.setInt(1, atd.getIdPedido());
			statement.setInt(2, atd.getIdProduto());
			statement.setString(3, atd.getFim());
			
			statement.executeUpdate();
			
		}catch(SQLException ex){
			Logger.getLogger(MyAtendimentoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar atendimento: um campo obrigatório está em branco.");
            }
			else throw new SQLException("Erro ao atualizar atendimento.");
		}
	}
	
	@Override
	public void finalize(Atendimento atd) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(FINALIZE_QUERY)){
			
			statement.setInt(1, atd.getIdPedido());
			statement.setInt(2, atd.getIdProduto());
			
			statement.executeUpdate();
			
		}catch(SQLException ex){
			Logger.getLogger(MyAtendimentoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar atendimento: um campo obrigatório está em branco.");
            }
			else throw new SQLException("Erro ao atualizar atendimento.");
		}
	}

	@Override
	public void delete(Integer idPedido, Integer idProduto) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            
            statement.setInt(1, idPedido);
			statement.setInt(2, idProduto);
			
			statement.executeUpdate();
			
		} catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
			throw new SQLException("Erro ao remover atendimento.");
        }
	}

	public List<Atendimento> all() throws SQLException {
		List<Atendimento> allAtd = new ArrayList<>();
        
		try (PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {
            
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Atendimento atd = new Atendimento();
                atd.setInicio(result.getString("inicio"));
				atd.setFim(result.getString("fim"));
				atd.setIdPedido(result.getInt("atend_idPedido"));
				atd.setIdProduto(result.getInt("atend_idProduto"));
				allAtd.add(atd);
			}
			return allAtd;
		}
		catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar atendimentos.");
		}
	}
	
	@Override
	public List<Atendimento> readByPedido(int idPedido) throws SQLException {
		List<Atendimento> allAtd = new ArrayList<>();
        
		try (PreparedStatement statement = connection.prepareStatement(READ_BY_PEDIDO_QUERY)) {
            statement.setInt(1, idPedido);
			
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Atendimento atd = new Atendimento();
                atd.setInicio(result.getString("inicio"));
				atd.setFim(result.getString("fim"));
				atd.setIdPedido(result.getInt("atend_idPedido"));
				atd.setIdProduto(result.getInt("atend_idProduto"));
				allAtd.add(atd);
			}
			return allAtd;
		}
		catch(SQLException ex){
			Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar atendimentos.");
		}
	}
}
