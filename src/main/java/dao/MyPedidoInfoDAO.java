/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Medina
 */
public class MyPedidoInfoDAO {
	
	String LIST_ALL_QUERY = 
	"WITH prod_ped AS " +
		"(" +
			"SELECT  pedido_idPedido AS idPedido," + 
			"produto_idProduto AS idProduto, " +
			"valor, qtd, observacao" +
			"FROM restaurante.produtos_pedidos" +
		")," +
	"atendimentos AS " +
		"(" +
			"SELECT  atend_idPedido AS idPedido, " +
			"atend_idProduto AS idProduto, " +
			"inicio, fim" +
			"FROM restaurante.atendimentos" +
		")" +
	"SELECT * FROM atendimentos" +
	"JOIN prod_ped" +
	"USING (idPedido, idProduto);";
}
