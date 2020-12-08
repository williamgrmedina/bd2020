/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.DAOFactory;
import dao.MyAtendimentoDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import dao.ProdutoPedidoDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Atendimento;
import model.Funcionario;
import model.Pedido;
import model.Produto;
import model.ProdutoPedido;

/**
 *
 * @author Medina
 */
@WebServlet(name = "PedidoController", 
		urlPatterns = {
			"/gerente/pedidos",
			"/funcionario/pedidos",
			"/pedido/createPresencial",
			"/pedido/add_item",
			"/pedido/remove_item"
		})
public class PedidoController extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	
	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PedidoDAO dao;
		ProdutoPedidoDAO dao_prod_ped;
		MyAtendimentoDAO dao_atd;
		ProdutoDAO dao_prod;
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
		
		switch(request.getServletPath()){
		case "/funcionario/pedidos":{
			/*try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
				dao = daoFactory.getPedidoDAO();
				dao_atd = daoFactory.getAtendimentoDAO();
				dao_prod_ped = daoFactory.getProdutoPedidoDAO();

				Funcionario f = (Funcionario)session.getAttribute("funcionario");
					
				List<Pedido> pedidos = dao.readRelevant(f);
				List<Atendimento> atendimentos = new ArrayList<>();
				List<ProdutoPedido> produtosPedidos = new ArrayList<>();
				
				for(Pedido p : pedidos){
					atendimentos.addAll(dao_atd.readByPedido(p.getId()));
				}
				
				for(Atendimento atd : atendimentos){
					produtosPedidos.add(dao_prod_ped.read(atd.getIdPedido(), atd.getIdProduto()));
				}

				request.setAttribute("atendimentos", atendimentos);
				request.setAttribute("produtos_pedidos", produtosPedidos);
			} catch (Exception ex) {
				request.getSession().setAttribute("error", ex.getMessage());
			} */

			dispatcher = request.getRequestDispatcher("/view/pedido/funcionario_pedidos.jsp");
			dispatcher.forward(request, response);
			break;
		}
		
		case "/pedido/createPresencial":{
			dispatcher = request.getRequestDispatcher("/view/pedido/create.jsp");
			dispatcher.forward(request, response);
			break;
		}
	}	
}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ProdutoDAO dao_prod;
		RequestDispatcher dispatcher;
		
		HttpSession session = request.getSession();
		String servletPath = request.getServletPath();

		switch (servletPath) {
		case "/pedido/createPresencial":{
			try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
				dao_prod = daoFactory.getProdutoDAO();
				List<Produto> produtos = dao_prod.all();
				List<Integer> selectList = new ArrayList<>();
				Integer i;
				for(i=0; i < produtos.size(); i++){
					selectList.add(0);
				}
				synchronized(session){
					session.setAttribute("produtos", produtos);
					session.setAttribute("selectList", selectList);
				}
			}
			catch (ClassNotFoundException | IOException | SQLException ex) {
				request.getSession().setAttribute("error", ex.getMessage());
				dispatcher = request.getRequestDispatcher("/view/pedido/funcionario_pedidos.jsp");
				dispatcher.forward(request, response);
			}
			dispatcher = request.getRequestDispatcher("/view/pedido/create.jsp");
			dispatcher.forward(request, response);
			break;
		}
			
		case "/pedido/add_item":{
			
			response.setContentType("application/json");
			String success_url = request.getContextPath() + "/view/pedido/create.jsp";
			String error_url = request.getContextPath() + "/view/pedido/funcionario_pedidos.jsp";	
			
			try {
				//obs: possivel problema de sincronização em multiplas sessoes com mesmo usuario
				List<Integer> selectList = (List<Integer>) request.getSession().getAttribute("selectList");
				List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("produtos");
				
				Integer index = Integer.parseInt(request.getParameter("idx")); 
				Integer qtd = selectList.get(index);			
				
				Produto prod_new = produtos.get(index);
				prod_new.setQtd(prod_new.getQtd() - 1);
				selectList.set(index, qtd + 1);
				session.setAttribute("selectList", selectList);
				
				Gson gson = new Gson();
				success_url = gson.toJson(success_url);
				response.getOutputStream().print(success_url);
				
			}catch(IOException | NumberFormatException  ex){
				Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
				request.getSession().setAttribute("error", ex.getMessage());
				Gson gson = new Gson();
				error_url = gson.toJson(error_url);
				response.getOutputStream().print(error_url);
			}
			
			break;
		}
		
		case "/pedido/remove_item":{
			
			response.setContentType("application/json");
			String success_url = request.getContextPath() + "/view/pedido/create.jsp";
			String error_url = request.getContextPath() + "/view/pedido/funcionario_pedidos.jsp";	
			
			try {
				//obs: possivel problema de sincronização em multiplas sessoes com mesmo usuario
				List<Integer> selectList = (List<Integer>) request.getSession().getAttribute("selectList");
				List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("produtos");
				
				Integer index = Integer.parseInt(request.getParameter("idx")); 
				Integer qtd = selectList.get(index);			
				
				Produto prod_new = produtos.get(index);
				prod_new.setQtd(prod_new.getQtd() + 1);
				selectList.set(index, qtd - 1);
				session.setAttribute("selectList", selectList);
				
				Gson gson = new Gson();
				success_url = gson.toJson(success_url);
				response.getOutputStream().print(success_url);
				
			}catch(IOException | NumberFormatException  ex){
				Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
				request.getSession().setAttribute("error", ex.getMessage());
				Gson gson = new Gson();
				error_url = gson.toJson(error_url);
				response.getOutputStream().print(error_url);
			}
			
			break;
		}
			
	}
}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
