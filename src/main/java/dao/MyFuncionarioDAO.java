/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import model.Garcom;
import model.Gerente;
import model.OperadorCaixa;

/**
 *
 * @author Medina
 */
public class MyFuncionarioDAO implements FuncionarioDAO {
    
    Connection connection;
    
    private final static String CREATE_QUERY =
        "INSERT INTO esquema_restaurante.funcionarios " +
        "(login, senha, pnome, snome, email, cargo, salario, data_efetivacao, gerente_login) " +
        "VALUES(SHA2(?, 0), SHA2(?, 0), ?, ?, ?, ?, ?, ?, SHA2(?, 0));";
    
    private final static String AUTHENTICATE_QUERY =
        "SELECT *" +
        "FROM esquema_restaurante.funcionarios " +
        "WHERE login = SHA2(?, 0) AND senha = SHA2(?, 0);";
    
    public MyFuncionarioDAO(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void authenticate(Funcionario fun) throws SQLException, SecurityException {
        
        try (PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_QUERY)) {
            statement.setString(1, fun.getLogin());
            statement.setString(2, fun.getSenha());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    fun.setLogin(result.getString("login"));
                    fun.setSenha(result.getString("senha"));
                    fun.setPNome(result.getString("pnome"));
                    fun.setSNome(result.getString("snome"));
                    fun.setEmail(result.getString("email"));
                    fun.setCargo(result.getString("cargo"));
                    fun.setSalario(result.getDouble("salario"));
                    fun.setData_efetivacao(result.getDate("data_efetivacao"));
                    fun.setGerenteLogin(result.getString("gerente_login"));
                } else {
                    throw new SecurityException("Login ou senha incorretos.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao autenticar funcionário.");
        }
    }

    @Override
    public void create(Funcionario fun) throws SQLException, SecurityException {
       
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            
            statement.setString(1, fun.getLogin());
            statement.setString(2, fun.getSenha());
            statement.setString(3, fun.getPNome());
            statement.setString(4, fun.getSNome());
            statement.setString(5, fun.getEmail());
            statement.setString(6, fun.getCargo());
            statement.setDouble(7, fun.getSalario());
            statement.setDate(8, fun.getData_efetivacao());
            statement.setString(9, fun.getGerenteLogin());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("Erro ao inserir funcionário: login já existente.")) {
                throw new SQLException("Erro ao inserir funcionário: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir usuário.");
            }
        }
    }

    @Override
    public Funcionario read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Funcionario t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Funcionario> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}