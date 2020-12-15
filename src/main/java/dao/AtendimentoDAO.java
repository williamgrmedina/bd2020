/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Atendimento;

/**
 *
 * @author Medina
 */
public interface AtendimentoDAO extends DAOInt_2x <Atendimento>{
		public List<Atendimento> readByPedido(int idPedido) throws SQLException;
		public void finalize(Atendimento atd) throws SQLException;
		public void finalizeByPedido(int idPedido) throws SQLException;
}
