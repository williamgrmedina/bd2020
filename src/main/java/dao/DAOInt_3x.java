/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Medina
 * @param <T> the DAOInt Type. Ex: Pedido indicates pedido
 */
public interface DAOInt_3x <T> {
    public void create(T t) throws SQLException;
    public T read(Integer prim_key_one, Integer prim_key_two, Integer prim_key_three) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(Integer prim_key_one, Integer prim_key_two, Integer prim_key_three) throws SQLException;
    
     public List<T> all() throws SQLException;
}
