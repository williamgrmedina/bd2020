/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOFactory;
import dao.MyProdutoDAO;
import dao.ProdutoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
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
@WebServlet(name = "ProdutoController", 
	urlPatterns = {
		"/gerente/produtos",
		"/produto/alter_name",
		"/produto/alter_compra",
		"/produto/alter_venda",
		"/produto/alter_qtd",
		"/produto/create",
		"/produto/delete"
	})
public class ProdutoController extends HttpServlet {

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
		
		ProdutoDAO dao;
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
		Produto prod;
        
        switch(request.getServletPath()){
            case "/gerente/produtos":
				try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
					dao = daoFactory.getProdutoDAO();
					
					List<Produto> produtos = dao.all();					
					
					request.setAttribute("produtos", produtos);
				} catch (Exception ex) {
					request.getSession().setAttribute("error", ex.getMessage());
				} 

				dispatcher = request.getRequestDispatcher("/view/produto/gerente_produtos.jsp");
				dispatcher.forward(request, response);
				break;
			
			case "/produto/create":
				dispatcher = request.getRequestDispatcher("/view/produto/create.jsp");
				dispatcher.forward(request, response);
				break;
				
			case "/produto/delete": {
				try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getProdutoDAO();
					dao.delete(Integer.parseInt(request.getParameter("id")));
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/gerente/produtos");
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
		
		ProdutoDAO dao;
		Produto prod = new Produto();
		
		HttpSession session = request.getSession();
		String servletPath = request.getServletPath();
		
		switch (servletPath) {
		case "/produto/alter_name":
			prod.setId(Integer.parseInt(request.getParameter("id")));
			prod.setNome(request.getParameter("nome"));
			
			try (DAOFactory daoFactory = DAOFactory.getInstance()){
				dao = daoFactory.getProdutoDAO();
				dao.update_nome(prod);
				
				response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			}catch(SQLException | ClassNotFoundException ex){
				Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, "Controller", ex);
				session.setAttribute("error", ex.getMessage());
				response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			}
			break;
		
		case "/produto/alter_compra":			
			try{
				prod.setId(Integer.parseInt(request.getParameter("id")));
				BigDecimal valor_compra = new BigDecimal(request.getParameter("value"));
				prod.setValor_compra(valor_compra);
			
				DAOFactory daoFactory = DAOFactory.getInstance();
				dao = daoFactory.getProdutoDAO();
				dao.update_valor_compra(prod);
				
				response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			}catch(SQLException | IOException | ClassNotFoundException | NumberFormatException ex){
				Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, "Controller", ex);
				session.setAttribute("error", ex.getMessage());
				response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			}
			break;
		
		case "/produto/alter_venda":		
			try{
				prod.setId(Integer.parseInt(request.getParameter("id")));
				BigDecimal valor_venda = new BigDecimal(request.getParameter("value"));
				prod.setValor_venda(valor_venda);
			
				DAOFactory daoFactory = DAOFactory.getInstance();
				dao = daoFactory.getProdutoDAO();
				dao.update_valor_venda(prod);
				
				response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			}catch(SQLException | IOException | ClassNotFoundException | NumberFormatException ex){
				Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, "Controller", ex);
				session.setAttribute("error", ex.getMessage());
				response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			}
			break;
		
		case "/produto/alter_qtd":		
			try{
				prod.setId(Integer.parseInt(request.getParameter("id")));
				Integer qtd = Integer.parseInt(request.getParameter("value"));
				prod.setQtd(qtd);
			
				DAOFactory daoFactory = DAOFactory.getInstance();
				dao = daoFactory.getProdutoDAO();
				dao.update_qtd(prod);
				
				response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			}catch(SQLException | IOException | ClassNotFoundException | NumberFormatException ex){
				Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, "Controller", ex);
				session.setAttribute("error", ex.getMessage());
				response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			}
			break;
		
		case "/produto/create":		
			try{
				prod.setNome(request.getParameter("nome"));
				BigDecimal val_compra = new BigDecimal(request.getParameter("val_compra"));
				prod.setValor_compra(val_compra);
				BigDecimal val_venda = new BigDecimal(request.getParameter("val_venda"));
				prod.setValor_venda(val_venda);	
				Integer qtd = Integer.parseInt(request.getParameter("qtd"));
				prod.setQtd(qtd);
			
				DAOFactory daoFactory = DAOFactory.getInstance();
				dao = daoFactory.getProdutoDAO();
				dao.create(prod);
				
				response.setContentType("text/plain");
				response.getWriter().println(request.getContextPath() + "/gerente/produtos");
				
			}catch(SQLException | IOException | ClassNotFoundException | NumberFormatException ex){
				Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, "Controller", ex);
				session.setAttribute("error", ex.getMessage());
				response.setContentType("text/plain");
				response.getWriter().println(request.getContextPath() + "/gerente/produtos");
			}
			break;
		
		case "/produto/delete": 
			String[] produtos = request.getParameterValues("delete");

			try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
				dao = daoFactory.getProdutoDAO();

				try {
					daoFactory.beginTransaction();

					for (String prodId : produtos) {
						dao.delete(Integer.parseInt(prodId));
					}

					daoFactory.commitTransaction();
					daoFactory.endTransaction();
				} catch (SQLException ex) {
					session.setAttribute("error", ex.getMessage());
					daoFactory.rollbackTransaction();
				}
			} catch (ClassNotFoundException | IOException ex) {
				Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Controller", ex);
				session.setAttribute("error", ex.getMessage());
			} catch (SQLException ex) {
				Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Controller", ex);
				session.setAttribute("rollbackError", ex.getMessage());
			}

			response.sendRedirect(request.getContextPath() + "/gerente/produtos");
			break;
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
