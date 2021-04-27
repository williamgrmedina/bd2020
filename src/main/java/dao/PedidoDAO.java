/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Cliente;
import model.Funcionario;
import model.FuncionarioPedidos;
import model.Pedido;

/**
 *
 * @author Medina
 */
public interface PedidoDAO extends DAOInt<Pedido> {
        public void createOnline(Pedido p) throws SQLException;
        public List<Pedido> readPedidos(Funcionario f) throws SQLException;
        public List<Pedido> readPedidos(Cliente cl) throws SQLException;
        public int getLastPedidoId() throws SQLException;
        public void updateStatus(int idPedido, String status) throws SQLException;
        public void finalize(int idPedido) throws SQLException;
        public int getCanceladosCount(int year) throws SQLException;
        public int getPagosCount(int year) throws SQLException;
        public int getMaxYear() throws SQLException;
        public int getMinYear() throws SQLException;
        public int getMaxMonth(int year) throws SQLException;
        public int getMonthYearPresCount(int month, int year) throws SQLException;
        public int getMonthYearOnlineCount(int month, int year) throws SQLException;
        public void updateAllAttr(Pedido p) throws SQLException;
        public int getPedidosByFuncionario(Funcionario f, int month, int year) throws SQLException;
        public List<Pedido> getMonthYearPedidos(int month, int year) throws SQLException;
}
