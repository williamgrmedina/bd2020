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
import model.ProdutoPedido;

/**
 *
 * @author Medina
 */
public class MyProdutoPedidoDAO implements ProdutoPedidoDAO {

    Connection connection;

    private final static String CREATE_QUERY
            = "INSERT INTO restaurante.produtos_pedidos "
            + "(pedido_idPedido, produto_idProduto, valor, qtd) "
            + "VALUES (?, ?, ?, ?);";

    private final static String READ_QUERY
            = "SELECT * FROM restaurante.produtos_pedidos "
            + "WHERE pedido_idPedido = ? AND produto_idProduto = ?;";

    private final static String READ_ALL_QUERY
            = "SELECT * FROM restaurante.produtos_pedidos;";

    private final static String UPDATE_QUERY
            = "UPDATE restaurante.produtos_pedidos "
            + "SET valor = ?, qtd = ? "
            + "WHERE pedido_idPedido = ? AND produto_idProduto = ?;";

    private final static String DELETE_QUERY
            = "DELETE FROM restaurante.produtos_pedidos "
            + "WHERE pedido_idPedido = ? AND produto_idProduto = ?;";

    private final static String READ_INFO_PEDIDO
            = "SELECT * FROM restaurante.produtos_pedidos "
            + "WHERE pedido_idPedido = ?;";

    @Override
    public void create(ProdutoPedido pp) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, pp.getIdPedido());
            statement.setInt(2, pp.getIdProduto());
            statement.setBigDecimal(3, pp.getValor());
            statement.setInt(4, pp.getQtd());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyPedidoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao inserir produto ao pedido.");
        }
    }

    public MyProdutoPedidoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ProdutoPedido read(Integer idPedido, Integer idProduto) throws SQLException {
        ProdutoPedido pp = new ProdutoPedido();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, idPedido);
            statement.setInt(2, idProduto);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    pp.setIdPedido(result.getInt("pedido_idPedido"));
                    pp.setIdProduto(result.getInt("produto_idProduto"));
                    pp.setValor(result.getBigDecimal("valor"));
                    pp.setQtd(result.getInt("qtd"));
                    return pp;
                } else {
                    throw new SQLException("Erro ao visualizar produto do pedido: produto ou pedido não encontrado.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().equals("Erro ao visualizar produto do pedido: produto ou pedido não encontrado.")) {
                throw new SQLException("Erro ao visualizar produto do pedido: produto ou pedido não encontrado.");
            } else {
                throw new SQLException("Erro ao visualizar produto ou pedido.");
            }
        }
    }

    @Override
    public List<ProdutoPedido> readProdutos(Integer idPedido) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(READ_INFO_PEDIDO)) {

            statement.setInt(1, idPedido);
            List<ProdutoPedido> produtos = new ArrayList();

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    ProdutoPedido pp = new ProdutoPedido();
                    pp.setIdPedido(result.getInt("pedido_idPedido"));
                    pp.setIdProduto(result.getInt("produto_idProduto"));
                    pp.setValor(result.getBigDecimal("valor"));
                    pp.setQtd(result.getInt("qtd"));
                    produtos.add(pp);
                }
                return produtos;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao visualizar produto ou pedido.");
        }
    }

    @Override
    public void update(ProdutoPedido pp) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setBigDecimal(1, pp.getValor());
            statement.setInt(2, pp.getQtd());
            statement.setInt(3, pp.getIdPedido());
            statement.setInt(4, pp.getIdProduto());

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar produto do pedido: um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao atualizar produto do pedido.");
            }
        }
    }

    @Override
    public void delete(Integer idPedido, Integer idProduto) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, idPedido);
            statement.setInt(2, idProduto);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao remover produto do pedido.");
        }
    }

    @Override
    public List<ProdutoPedido> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
