/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;

/**
 *
 * @author Medina
 * @param <T>
 */
public interface DAOInt_2x<T> {

    public void create(T pp) throws SQLException;

    public T read(Integer idPedido, Integer idProduto) throws SQLException;

    public void update(T pp) throws SQLException;

    public void delete(Integer idPedido, Integer idProduto) throws SQLException;
}
