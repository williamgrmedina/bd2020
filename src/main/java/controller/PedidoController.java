/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.google.gson.Gson;
import dao.DAOFactory;
import dao.MyAtendimentoDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import dao.ProdutoPedidoDAO;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Produto;

/**
 *
 * @author Medina
 */
@WebServlet(name = "PedidoController", 
		urlPatterns = {
			"/gerente/pedidos",
			"/funcionario/pedidos",
			"/pedido/createPresencial"
		})
public class PedidoController extends HttpServlet implements Jsonable {

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

			dispatcher = request.getRequestDispatcher("/view/pedido/funcionario_inicio.jsp");
			dispatcher.forward(request, response);
			break;
		}
		
		case "/pedido/createPresencial":{
			try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
				dao_prod = daoFactory.getProdutoDAO();
				List<Produto> produtos = dao_prod.all();
				
				request.setAttribute("produtos", produtos); 
			}
			catch (ClassNotFoundException | IOException | SQLException ex) {
				session.setAttribute("error", ex.getMessage());
				dispatcher = request.getRequestDispatcher("/view/pedido/funcionario_inicio.jsp");
				dispatcher.forward(request, response);
				break;
			}
			
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
								
				request.setAttribute("produtos", produtos); 
				
			}
			catch (ClassNotFoundException | IOException | SQLException ex) {
				session.setAttribute("error", ex.getMessage());
				dispatcher = request.getRequestDispatcher("/view/pedido/funcionario_inicio.jsp");
				dispatcher.forward(request, response);
				break;
			}
			dispatcher = request.getRequestDispatcher("/view/pedido/create.jsp");
			dispatcher.forward(request, response);
			break;
			
			/*URL url = this.getClass().getClassLoader().getResource("../../assets/js/DBData.json");
			//nota: se o projeto está sendo executado em um ambiente github, 
			//  a url mapeada pode em alguns casos pertencer à pasta target em vez de src 
			String file;
			try {
				file = url.toURI().getPath();
			} catch (URISyntaxException ex) {
				Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
				break;
			}
			
			
			try (FileWriter writer = new FileWriter(file)) {
				writer.write("{ \"data:\" [1, Produto esgotando, 40, 3, 0]]}");
				//printToJson(writer, );
			} catch (IOException e) {
				e.printStackTrace();
			}*/
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

	@Override
	public String toJson() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void toJson(Writer writable) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
