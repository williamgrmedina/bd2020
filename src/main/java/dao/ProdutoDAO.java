/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
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
	public void add_items(Produto p) throws SQLException;
	public void remove_items(Produto p) throws SQLException;
        public Produto readRandom() throws SQLException;
}
