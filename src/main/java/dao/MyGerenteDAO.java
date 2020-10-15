/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;

/**
 *
 * @author Medina
 */
public class MyGerenteDAO extends MyFuncionarioDAO{    
    public MyGerenteDAO(Connection connection){
        super(connection);
    }
    
    @Override
    String AUTHENTICATE_QUERY(int index){
        String TABLE_NAME = INFO_FUNCIONARIOS[index];
        
        return "SELECT login, senha, nome, `e-mail`, salario, data_contratacao " +
        "FROM " + DBNAME + "." + TABLE_NAME + " " +
        "WHERE login = ? AND senha = ?;";
    }
}