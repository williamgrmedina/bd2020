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

/**
 *
 * @author Medina
 */
public class MyFuncionarioDAO implements FuncionarioDAO {

    Connection connection;

    private final static String CREATE_QUERY
            = "INSERT INTO restaurante.funcionarios "
            + "(login, senha, pnome, snome, email, cargo, setor, salario, data_efetivacao, gerente_login) "
            + "VALUES(?, SHA2(?, 0), ?, ?, ?, ?, ?, ?, ?, ?);";

    private final static String READ_QUERY
            = "SELECT login, pnome, snome, email, cargo, setor, salario, data_efetivacao, gerente_login "
            + "FROM restaurante.funcionarios "
            + "WHERE login = ?;";

    private final static String DELETE_QUERY
            = "DELETE "
            + "FROM restaurante.funcionarios "
            + "WHERE login = ?;";

    private final static String UPDATE_QUERY
            = "UPDATE restaurante.funcionarios "
            + "SET login = ?, senha = ?, pnome = ?, snome = ?, email = ?, cargo = ?, setor = ?,"
            + "salario = ?, data_efetivacao = ?, gerente_login = ? "
            + "WHERE login = ?;";

    private final static String AUTHENTICATE_QUERY
            = "SELECT * "
            + "FROM restaurante.funcionarios "
            + "WHERE login = ? AND senha = SHA2(?, 0);";

    private final static String READ_GERENCIADOS_QUERY
            = "SELECT login, pnome, snome, email, cargo, setor, salario, data_efetivacao "
            + "FROM restaurante.funcionarios "
            + "WHERE gerente_login = ?;";

    private final static String READ_ALL_QUERY
            = "SELECT login, pnome, snome, email, cargo, setor, salario, data_efetivacao "
            + "FROM restaurante.funcionarios;";

    private final static String READ_RANDOM_QUERY
            = "SELECT login, pnome, snome, email, cargo, setor, salario, data_efetivacao, gerente_login "
            + "FROM restaurante.funcionarios "
            + "ORDER BY RAND() "
            + "LIMIT 1;";
    
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
                    fun.setSetor(result.getString("setor"));
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
            statement.setString(7, fun.getSetor());
            statement.setDouble(8, fun.getSalario());
            statement.setDate(9, fun.getData_efetivacao());
            statement.setString(10, fun.getGerenteLogin());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir funcionário: um campo obrigatório está em branco.");
            } else if (ex.getMessage().contains("Duplicate")) {
                throw new SQLException("Erro ao inserir funcionário: login ja utilizado.");
            } else {
                throw new SQLException("Erro ao inserir funcionário.");
            }
        }
    }

    @Override
    public Funcionario read(String login) throws SQLException {
        Funcionario fun = new Funcionario();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    fun.setLogin(result.getString("login"));
                    fun.setPNome(result.getString("pnome"));
                    fun.setSNome(result.getString("snome"));
                    fun.setEmail(result.getString("email"));
                    fun.setCargo(result.getString("cargo"));
                    fun.setSetor(result.getString("setor"));
                    fun.setSalario(result.getDouble("salario"));
                    fun.setData_efetivacao(result.getDate("data_efetivacao"));
                    return fun;
                } else {
                    throw new SQLException("Erro ao visualizar: funcionário não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: funcionário não encontrado.")) {
                throw new SQLException("Erro ao visualizar: funcionário não encontrado.");
            } else {
                throw new SQLException("Erro ao visualizar funcionário.");
            }
        }
    }

    @Override
    public void update(Funcionario fun) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, fun.getLogin());
            statement.setString(2, fun.getSenha());
            statement.setString(3, fun.getPNome());
            statement.setString(4, fun.getSNome());
            statement.setString(5, fun.getEmail());
            statement.setString(6, fun.getCargo());
            statement.setString(7, fun.getSetor());
            statement.setDouble(8, fun.getSalario());
            statement.setDate(9, fun.getData_efetivacao());
            statement.setString(10, fun.getGerenteLogin());
            statement.setString(11, fun.getLogin());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar funcionário: um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao atualizar funcionário.");
            }
        }
    }

    @Override
    public void updateWithLogin(Funcionario fun, String new_login) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, new_login);
            statement.setString(2, fun.getSenha());
            statement.setString(3, fun.getPNome());
            statement.setString(4, fun.getSNome());
            statement.setString(5, fun.getEmail());
            statement.setString(6, fun.getCargo());
            statement.setString(7, fun.getSetor());
            statement.setDouble(8, fun.getSalario());
            statement.setDate(9, fun.getData_efetivacao());
            statement.setString(10, fun.getGerenteLogin());
            statement.setString(11, fun.getLogin());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar funcionário: um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao inserir funcionário.");
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
            throw new SQLException("Erro ao remover funcionário.");
        }

    }

    @Override
    public List<Funcionario> all() throws SQLException {
        List<Funcionario> allFun = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Funcionario fun = new Funcionario();
                fun.setLogin(result.getString("login"));
                fun.setPNome(result.getString("pnome"));
                fun.setSNome(result.getString("snome"));
                fun.setEmail(result.getString("email"));
                fun.setCargo(result.getString("cargo"));
                fun.setSetor(result.getString("setor"));
                fun.setSalario(result.getDouble("salario"));
                fun.setData_efetivacao(result.getDate("data_efetivacao"));
                allFun.add(fun);
            }
            return allFun;
        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar funcionários.");
        }
    }

    @Override
    public List<Funcionario> get_gerenciados(String gerente_login) throws SQLException {
        List<Funcionario> allFun = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(READ_GERENCIADOS_QUERY)) {
            statement.setString(1, gerente_login);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Funcionario fun = new Funcionario();
                fun.setLogin(result.getString("login"));
                fun.setPNome(result.getString("pnome"));
                fun.setSNome(result.getString("snome"));
                fun.setEmail(result.getString("email"));
                fun.setCargo(result.getString("cargo"));
                fun.setSetor(result.getString("setor"));
                fun.setSalario(result.getDouble("salario"));
                fun.setData_efetivacao(result.getDate("data_efetivacao"));
                allFun.add(fun);
            }
            return allFun;
        } catch (NullPointerException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new NullPointerException("Erro. Sessão de gerente não encontrada.");
        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar funcionário.");
        }
    }
 
    public Funcionario readRandom() throws SQLException {
        Funcionario fun = new Funcionario();

        try (PreparedStatement statement = connection.prepareStatement(READ_RANDOM_QUERY)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    fun.setLogin(result.getString("login"));
                    fun.setPNome(result.getString("pnome"));
                    fun.setSNome(result.getString("snome"));
                    fun.setEmail(result.getString("email"));
                    fun.setCargo(result.getString("cargo"));
                    fun.setSetor(result.getString("setor"));
                    fun.setSalario(result.getDouble("salario"));
                    fun.setData_efetivacao(result.getDate("data_efetivacao"));
                    return fun;
                } else {
                    throw new SQLException("Erro ao buscar funcionario aleatório.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao buscar funcionario aleatório.");
        }
    }
}
