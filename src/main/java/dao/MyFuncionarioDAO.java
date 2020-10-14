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
import model.Funcionario;
import model.Garcom;
import model.Gerente;
import model.OperadorCaixa;

/**
 *
 * @author Medina
 */
public class MyFuncionarioDAO implements FuncionarioDAO {
    
    private final Connection connection;
    
    private String TYPE_QUERY(String LISTA_CARGOS[], int index){
        
        try{
            if(LISTA_CARGOS.length <= 1){
                throw new NoSuchFieldException("Erro. Tabela de funcionários não definida/definida incorretamente.");
            }
        }catch(NoSuchFieldException ex){
            System.out.println(ex);
        }
        
        if(LISTA_CARGOS.length == 2 || index == LISTA_CARGOS.length - 1){
            return "SELECT login, ? " +
                "AS Source " +    
                "FROM esquema_restaurante." +
                LISTA_CARGOS[index] + " " +
                "WHERE login = ?" + ";";
        }
        else{
            return "SELECT login, ? " +
                   "AS Source " +    
                   "FROM esquema_restaurante." + 
                   LISTA_CARGOS[index] + " " + 
                   "WHERE login = ? " + "UNION ";
        }
    }
    
    @Override
    public Funcionario getFuncionarioType(String login) throws SQLException, NoSuchFieldException {
        try{
            String result_query = "";
            int i, j;
                       
            for (i=0; i<LISTA_CARGOS_MYSQLTABLENAME.length; i += 2){
                result_query += 
                    TYPE_QUERY(LISTA_CARGOS_MYSQLTABLENAME, i + 1);
            }
            
            try(PreparedStatement statement = connection.prepareStatement(result_query)){
                
                for(j=1; j<= LISTA_CARGOS_MYSQLTABLENAME.length; j+=2){
                    statement.setString(j, LISTA_CARGOS_MYSQLTABLENAME[j]);
                    statement.setString(j+1, login);
                }
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    String cargo = result.getString("Source");
                    try{
                        Funcionario newFuncionario = getFuncionarioInstance(cargo);
                        return newFuncionario;
                    }catch(SQLException ex){
                        System.out.println(ex.getMessage());
                    }
                } 
                else {
                    throw new NoSuchFieldException("Erro. Não existe funcionário com este login.");
                }
            }catch(SQLException ex){
                Logger.getLogger(MyUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

                throw new SQLException("Erro ao autenticar usuário.");
            }   
        }catch(NoSuchFieldException ex){
            System.out.println(ex.getMessage());
        }
        throw new SQLException("Erro ao autenticar usuário.");
    }
    
    private static String AUTHENTICATE_QUERY(String LISTA_CARGOS[], int index){
        return "SELECT login, senha, nome, `e-mail`, salario, data_contratacao " +
        "FROM esquema_restaurante." + LISTA_CARGOS[index] + " " +
        "WHERE login = ? AND senha = ?;";
    }
    
    protected static String CREATE_QUERY(String mysqlTable) {
        return "INSERT INTO esquema_restaurante." + mysqlTable + "(login, senha, nome, `e-mail`, salario, data_contratacao, Gerente_login) " +
        "VALUES(md5(?), md5(?), ?, ?, ?, ?, ?);";
    }
    
    
    public MyFuncionarioDAO(Connection connection) {
        this.connection = connection;
    }
    
    
    @Override
    public void authenticate(Funcionario fun) throws SQLException, SecurityException {
        String funcionarioTipo = fun.getTipo();
        
        int index;
        //recupera posicao onde está armazenado o nome da tabela mysql do funcionario    
        for(index=0; index<LISTA_CARGOS_MYSQLTABLENAME.length; index++){
            if(LISTA_CARGOS_MYSQLTABLENAME[index].equals(funcionarioTipo)){
                break;
            }
        }
        index += 1;
        
        try (PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_QUERY(LISTA_CARGOS_MYSQLTABLENAME, index))) {
            statement.setString(1, fun.getLogin());
            statement.setString(2, fun.getSenha());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    fun.setLogin(result.getString("login"));
                    fun.setSenha(result.getString("senha"));
                    fun.setNome(result.getString("nome"));
                    fun.setEmail(result.getString("e-mail"));
                    fun.setSalario(result.getDouble("salario"));
                    fun.setData_efetivacao(result.getDate("data_contratacao"));
                } else {
                    throw new SecurityException("Login ou senha incorretos.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao autenticar usuário.");
        }
    }

    @Override
    public void create(Funcionario fun) throws SQLException, SecurityException {
       
        try {
            /*String login = fun.getLogin();
            if(fun.addLogin(login) == false){
                throw new SQLException("Erro ao inserir funcionário: login já existente.");
            }*/
            
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY(fun.getTipo()));
            statement.setString(1, fun.getLogin());
            statement.setString(2, fun.getSenha());
            statement.setString(3, fun.getNome());
            statement.setString(4, fun.getEmail());
            statement.setDouble(5, fun.getSalario());
            statement.setDate(6, fun.getData_efetivacao());
            statement.setString(7, fun.getGerenteLogin());

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

    @Override
    public Funcionario getFuncionarioInstance(String cargo) throws SQLException{
        try{    
            if(cargo.equalsIgnoreCase("gerente")){
                Gerente gerente = new Gerente();
                return gerente;
            }
            else if(cargo.equalsIgnoreCase("garcom")){
                Garcom garcom = new Garcom();
                return garcom;
            }
            else if(cargo.equalsIgnoreCase("operador_caixa")){
                OperadorCaixa operador_cx = new OperadorCaixa();
                return operador_cx;
            }
            else throw new NoSuchFieldException("Erro. funcionario não está definido na lista de funcionários");
        }catch(NoSuchFieldException ex){
            System.out.println(ex.getMessage());
        }
        throw new SQLException("Ocorreu um erro ao instanciar um objeto de funcionário.");
    }
    
}
