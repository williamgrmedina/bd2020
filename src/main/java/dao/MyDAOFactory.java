/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;

/**
 *
 * @author João Vitor
 */
public class MyDAOFactory extends DAOFactory {
    
    public MyDAOFactory(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public UserDAO getUserDAO(){
      return new MyUserDAO(this.connection);
    }
}
