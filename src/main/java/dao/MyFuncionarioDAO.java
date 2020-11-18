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
    
	private final static String CREATE_QUERY =
        "INSERT INTO restaurante.funcionarios " +
        "(login, senha, pnome, snome, email, cargo, setor, salario, data_efetivacao, gerente_login) " +
        "VALUES(?, SHA2(?, 0), ?, ?, ?, ?, ?, ?, ?, ?);";
    
	private final static String READ_QUERY =
        "SELECT login, pnome, snome, email, cargo, setor, salario, data_efetivacao, gerente_login " +
        "FROM restaurante.funcionarios " +
        "WHERE login = ?;";
    
    private final static String AUTHENTICATE_QUERY =
        "SELECT * " +
        "FROM restaurante.funcionarios " +
        "WHERE login = ? AND senha = SHA2(?, 0);";
    
    private final static String SELECT_GERENCIADOS_QUERY =
        "SELECT login, pnome, snome, email, cargo, setor, salario, data_efetivacao " +
        "FROM restaurante.funcionarios " +
		"WHERE gerente_login = ?;";
	
	private final static String SELECT_ALL_QUERY =
        "SELECT login, pnome, snome, email, cargo, setor, salario, data_efetivacao " +
        "FROM restaurante.funcionarios;";
    
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
                throw new SQLException("Erro ao inserir usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir usuário.");
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
                }
                else throw new SQLException("Erro ao visualizar: funcionário não encontrado.");
            }
        } catch (SQLException ex) {
           Logger.getLogger(MyFuncionarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
           
           if(ex.getMessage().equals("Erro ao visualizar: funcionário não encontrado.")){
               
           }
           else throw new SQLException("Erro ao visualizar funcionário.");
        } 
        
        return fun;
    }

    @Override
    public void update(Funcionario t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String login) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Funcionario> all() throws SQLException {
        List<Funcionario> allFun = new ArrayList<>();
        
		try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            
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
           
           throw new SQLException("Erro ao listar funcionário.");
        } 
    }
	
	@Override
	public List<Funcionario> get_gerenciados(String gerente_login) throws SQLException {
        List<Funcionario> allFun = new ArrayList<>();
		
		try (PreparedStatement statement = connection.prepareStatement(SELECT_GERENCIADOS_QUERY)) {
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
    
}