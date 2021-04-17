/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author João Vitor
 */
public class MySqlConnectionFactory extends ConnectionFactory {
       private String dbHost;
       private String dbPort;
       private String dbName;
       private String dbUser;
       private String dbPassword;
       private String dbTimezone;
       
       public void readProperties() throws IOException {
           Properties properties = new Properties();
           
           try {
               InputStream input = this.getClass().getClassLoader().getResourceAsStream(MySqlConnectionFactory.propertiesPath);
               properties.load(input);
               
               dbHost = properties.getProperty("host");
               dbPort = properties.getProperty("port");
               dbName = properties.getProperty("name");
               dbUser = properties.getProperty("user");
               dbPassword = properties.getProperty("password");
               dbTimezone = properties.getProperty("timezone");
               
           } catch (IOException ex) {
               System.err.println(ex.getMessage());
               
               throw new IOException("Erro ao obter informação do banco de dados.");
           }
       }

    @Override
    public Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            readProperties();
            
            String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useUnicode=yes&characterEncoding=utf-8&useTimeZone=true&serverTimezone=" + dbTimezone;
            
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            
        } catch (ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            
            throw new ClassNotFoundException("Erro de conexão ao banco de dados.");
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            
            throw new SQLException("Erro de conexão ao banco de dados.");
        }
        
        return connection;
    }
}
