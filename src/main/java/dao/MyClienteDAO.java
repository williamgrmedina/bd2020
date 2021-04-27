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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;

/**
 *
 * @author Medina
 */
public class MyClienteDAO implements ClienteDAO {

    Connection connection;

    private final static String CREATE_QUERY
            = "INSERT INTO restaurante.clientes "
            + "(login, senha, pnome, snome, email) "
            + "VALUES(?, SHA2(?, 0), ?, ?, ?);";

    private final static String READ_QUERY
            = "SELECT login, pnome, snome, email "
            + "FROM restaurante.clientes "
            + "WHERE login = ?;";

    private final static String DELETE_QUERY
            = "DELETE "
            + "FROM restaurante.clientes "
            + "WHERE login = ?;";

    private final static String UPDATE_QUERY
            = "UPDATE restaurante.clientes "
            + "SET login = ?, senha = ?, pnome = ?, snome = ?, email = ? "
            + "WHERE login = ?;";

    private final static String AUTHENTICATE_QUERY
            = "SELECT * "
            + "FROM restaurante.clientes "
            + "WHERE login = ? AND senha = SHA2(?, 0);";
    
    private final static String READ_RANDOM_QUERY
            = "SELECT login, pnome, snome, email "
            + "FROM restaurante.clientes " 
            + "ORDER BY RAND() "
            + "LIMIT 1;";

    public MyClienteDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Cliente cl) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {

            statement.setString(1, cl.getLogin());
            statement.setString(2, cl.getSenha());
            statement.setString(3, cl.getPNome());
            statement.setString(4, cl.getSNome());
            statement.setString(5, cl.getEmail());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir cliente: um campo obrigatório está em branco.");
            } else {
                throw new SQLException(ex.getMessage());
            }
        }
    }

    @Override
    public Cliente read(String login) throws SQLException {
        Cliente cl = new Cliente();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cl.setLogin(result.getString("login"));
                    cl.setPNome(result.getString("pnome"));
                    cl.setSNome(result.getString("snome"));
                    cl.setEmail(result.getString("email"));
                    return cl;
                } else {
                    throw new SQLException("Erro ao visualizar: cliente não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: cliente não encontrado.")) {
                throw new SQLException("Erro ao visualizar: cliente não encontrado.");
            } else {
                throw new SQLException("Erro ao visualizar cliente.");
            }
        }
    }

    @Override
    public void update(Cliente cl) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, cl.getLogin());
            statement.setString(2, cl.getSenha());
            statement.setString(3, cl.getPNome());
            statement.setString(4, cl.getSNome());
            statement.setString(5, cl.getEmail());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar cliente: um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao atualizar cliente.");
            }
        }
    }

    @Override
    public void delete(String login) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setString(1, login);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao remover cliente.");
        }
    }

    @Override
    public void authenticate(Cliente cl) throws SQLException, SecurityException {
        try (PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_QUERY)) {
            statement.setString(1, cl.getLogin());
            statement.setString(2, cl.getSenha());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cl.setLogin(result.getString("login"));
                    cl.setSenha(result.getString("senha"));
                    cl.setPNome(result.getString("pnome"));
                    cl.setSNome(result.getString("snome"));
                    cl.setEmail(result.getString("email"));
                } else {
                    throw new SecurityException("Login ou senha incorretos.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao autenticar cliente.");
        }
    }

    @Override
    public List<Cliente> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Cliente readRandom() throws SQLException {
        Cliente cl = new Cliente();

        try (PreparedStatement statement = connection.prepareStatement(READ_RANDOM_QUERY)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cl.setLogin(result.getString("login"));
                    cl.setPNome(result.getString("pnome"));
                    cl.setSNome(result.getString("snome"));
                    cl.setEmail(result.getString("email"));
                    return cl;
                } else {
                    throw new SQLException("Erro ao retornar cliente aleatorio: não existem clientes.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao retornar cliente aleatorio: não existem clientes.")) {
                throw new SQLException("Erro ao retornar cliente aleatorio: não existem clientes.");
            } else {
                throw new SQLException("Erro ao retornar cliente aleatorio.");
            }
        }
    }
}
