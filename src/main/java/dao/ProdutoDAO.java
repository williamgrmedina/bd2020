/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Produto;

/**
 *
 * @author Medina
 */
public interface ProdutoDAO extends DAOInt <Produto> {
	public void update_nome(Produto p) throws SQLException;
	public void update_valor_compra(Produto p) throws SQLException;
	public void update_valor_venda(Produto p) throws SQLException;
	public void update_qtd(Produto p) throws SQLException;
	
	/*retorna itens sem informacao de preco de compra do fornecedor. 
	 Se houver mais de um item com mesmo nome, retorna o de menor preço. 
	 Comando utilizado para visualizacao do cliente/funcionario na hora 
	 da geracao do pedido.*/
	public List<Produto> getComercializaveis() throws SQLException; 
}