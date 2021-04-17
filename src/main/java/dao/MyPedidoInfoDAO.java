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
import model.PedidoInfo;

/**
 *
 * @author Medina
 */
public class MyPedidoInfoDAO {

    private final static String LIST_INFO_ALL_QUERY
            = "WITH prod_ped AS "
            + "("
            + "SELECT  pedido_idPedido AS idPedido, "
            + "produto_idProduto AS idProduto, "
            + "valor, qtd "
            + "FROM restaurante.produtos_pedidos "
            + "), "
            + "atendimentos AS "
            + "("
            + "SELECT  atend_idPedido AS idPedido, "
            + "atend_idProduto AS idProduto, "
            + "inicio, fim "
            + "FROM restaurante.atendimentos"
            + "),"
            + "partial_info AS "
            + "("
            + "SELECT * FROM atendimentos "
            + "JOIN prod_ped "
            + "USING (idPedido, idProduto)"
            + ")"
            + "SELECT * FROM restaurante.pedidos "
            + "JOIN partial_info "
            + "USING (idPedido);";

    private final static String LIST_INFO_RELEVANT_QUERY
            = "WITH prod_ped AS "
            + "("
            + "SELECT  pedido_idPedido AS idPedido, "
            + "produto_idProduto AS idProduto, "
            + "valor, qtd "
            + "FROM restaurante.produtos_pedidos "
            + "), "
            + "atendimentos AS "
            + "("
            + "SELECT  atend_idPedido AS idPedido, "
            + "atend_idProduto AS idProduto, "
            + "inicio, fim "
            + "FROM restaurante.atendimentos"
            + "),"
            + "partial_info AS "
            + "("
            + "SELECT * FROM atendimentos "
            + "JOIN prod_ped "
            + "USING (idPedido, idProduto)"
            + ")"
            + "SELECT * FROM restaurante.pedidos "
            + "LEFT JOIN partial_info "
            + "USING (idPedido) "
            + "WHERE funcionario_login = ? "
            + "OR cliente_login IS NOT NULL AND "
            + "funcionario_login IS NULL;";
            
    Connection connection ;

    public MyPedidoInfoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<PedidoInfo> all() throws SQLException {
        List<PedidoInfo> allPedInfo = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(LIST_INFO_ALL_QUERY)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                PedidoInfo info = new PedidoInfo();
                info.setIdPedido(result.getInt("idPedido"));
                info.setIdProduto(result.getInt("idProduto"));
                info.setInicio(result.getString("inicio"));
                info.setFim(result.getString("fim"));
                info.setValor(result.getBigDecimal("valor"));
                info.setQtd(result.getInt("qtd"));
                info.setStatus(result.getString("status"));
                /*Pedido e nome do produto deverão ser setados após criação 
				do PedidoInfo, utilizando as classes PedidoDAO e ProdutoDAO*/
                allPedInfo.add(info);
            }
            return allPedInfo;
        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar informacoes de pedidos.");
        }
    }

    public List<PedidoInfo> readRelevant(String login) throws SQLException {
        List<PedidoInfo> allPedInfo = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(LIST_INFO_RELEVANT_QUERY)) {
            statement.setString(1, login);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                PedidoInfo info = new PedidoInfo();
                info.setIdPedido(result.getInt("idPedido"));
                info.setIdProduto(result.getInt("idProduto"));
                info.setInicio(result.getString("inicio"));
                info.setFim(result.getString("fim"));
                info.setValor(result.getBigDecimal("valor"));
                info.setQtd(result.getInt("qtd"));
                info.setStatus(result.getString("status"));
                /*Pedido e nome do produto deverão ser setados após criação 
				do PedidoInfo, utilizando as classes PedidoDAO e ProdutoDAO*/
                allPedInfo.add(info);
            }
            return allPedInfo;
        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar informacoes de pedidos.");
        }
    }
    
    
}
