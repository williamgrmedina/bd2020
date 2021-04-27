/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cancelamento;
import model.ProdutoPedido;

/**
 *
 * @author Medina
 */
public class MyCancelamentoDAO implements DAOInt_3x<Cancelamento> {

    Connection connection;

    private final static String CREATE_QUERY
            = "INSERT INTO restaurante.cancelamentos "
            + "(cancelamento_idPedido, cancelamento_idProduto, valor, qtd) "
            + "VALUES (?, ?, ?, ?);";

    /*private final static String READ_QUERY
            = "SELECT * FROM restaurante.cancelamentos "
            + "WHERE cancelamento_idPedido = ? AND cancelamento_idProduto = ? AND horario = ?;";*/

    private final static String READ_ALL_QUERY
            = "SELECT * FROM restaurante.cancelamentos;";

    /*private final static String UPDATE_QUERY
            = "UPDATE restaurante.cancelamentos "
            + "SET cancelamento_idPedido = ?, cancelamento_idProduto = ?, valor = ?, qtd = ?, horario = ? "
            + "WHERE cancelamento_idPedido = ? AND cancelamento_idProduto = ? AND horario = ?;";*/

    public MyCancelamentoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Cancelamento c) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, c.getIdPedido());
            statement.setInt(2, c.getIdProduto());
            statement.setBigDecimal(3, c.getValor());
            statement.setInt(4, c.getQtd());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyPedidoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao inserir produto à relação de cancelamentos.");
        }
    }

    @Override
    public Cancelamento read(Integer prim_key_one, Integer prim_key_two, Integer prim_key_three) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Cancelamento t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer prim_key_one, Integer prim_key_two, Integer prim_key_three) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cancelamento> all() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {

            List<Cancelamento> cancelamentos = new ArrayList();

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Cancelamento c = new Cancelamento();
                    c.setIdPedido(result.getInt("Cancelamento_idPedido"));
                    c.setIdProduto(result.getInt("Cancelamento_idProduto"));
                    c.setValor(result.getBigDecimal("valor"));
                    c.setQtd(result.getInt("qtd"));
                    cancelamentos.add(c);
                }
                return cancelamentos;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao visualizar cacelamentos.");
        }
    }

}
