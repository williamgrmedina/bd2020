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
    
    @Override
    public FuncionarioDAO getFuncionarioDAO(){
      return new MyFuncionarioDAO(this.connection);
    }
	
	@Override
    public ProdutoDAO getProdutoDAO(){
      return new MyProdutoDAO(this.connection);
    }

	@Override
	public PedidoDAO getPedidoDAO() {
		return new MyPedidoDAO(this.connection);
	}
	
	@Override
	public ProdutoPedidoDAO getProdutoPedidoDAO() {
		return new MyProdutoPedidoDAO(this.connection);
	}
	
	@Override
	public MyAtendimentoDAO getAtendimentoDAO() {
		return new MyAtendimentoDAO(this.connection);
	}

}
