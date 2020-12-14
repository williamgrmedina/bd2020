/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.github.cliftonlabs.json_simple.Jsonable;
import com.google.gson.Gson;
import dao.AtendimentoDAO;
import dao.DAOFactory;
import dao.MyAtendimentoDAO;
import dao.MyPedidoInfoDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import dao.ProdutoPedidoDAO;
import java.io.IOException;
import java.io.Writer;
import java.net.SocketTimeoutException;
import java.rmi.AccessException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdk.internal.net.http.common.ConnectionExpiredException;
import model.Atendimento;
import model.Funcionario;
import model.Pedido;
import model.PedidoInfo;
import model.Produto;
import model.ProdutoPedido;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Medina
 */
@WebServlet(name = "PedidoController",
	urlPatterns = {
		"/gerente/pedidos",
		"/funcionario/pedidos",
		"/pedido/createPresencial",
		"/pedido/confirmar_entrega",
		"/pedido/confirmar_pgmt"
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

		PedidoDAO dao_ped;
		MyPedidoInfoDAO dao_ped_info;
		ProdutoPedidoDAO dao_prod_ped;
		MyAtendimentoDAO dao_atd;
		ProdutoDAO dao_prod;
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher;

		switch (request.getServletPath()) {
			case "/funcionario/pedidos": {
				
				try (DAOFactory daoFactory = DAOFactory.getInstance()) {
					dao_ped_info = daoFactory.getPedidoInfoDAO();
					dao_prod = daoFactory.getProdutoDAO();
					dao_ped = daoFactory.getPedidoDAO();
					Produto prod;
					Pedido ped;
					
					List <PedidoInfo> pedidos = dao_ped_info.all();
					for (PedidoInfo pInfo : pedidos){
						ped = dao_ped.read(pInfo.getIdPedido());
						prod = dao_prod.read(pInfo.getIdProduto());
						pInfo.setPedido(ped);
						pInfo.setNomeProduto(prod.getNome());
					}
					
					request.setAttribute("pedidos", pedidos);
										
				} catch (ClassNotFoundException | IOException | SQLException ex) {
					session.setAttribute("error", ex.getMessage());
					dispatcher = request.getRequestDispatcher("/view/pedido/funcionario_pedidos.jsp");
					dispatcher.forward(request, response);
					break;
				}

				dispatcher = request.getRequestDispatcher("/view/pedido/funcionario_pedidos.jsp");
				dispatcher.forward(request, response);
				break;
			}

			case "/pedido/createPresencial": {
				try (DAOFactory daoFactory = DAOFactory.getInstance()) {
					dao_prod = daoFactory.getProdutoDAO();

					request.setAttribute("produtos", dao_prod.all());
				} catch (ClassNotFoundException | IOException | SQLException ex) {
					session.setAttribute("error", ex.getMessage());
					dispatcher = request.getRequestDispatcher("/view/pedido/funcionario_pedidos.jsp");
					dispatcher.forward(request, response);
					break;
				}

				dispatcher = request.getRequestDispatcher("/view/pedido/create.jsp");
				dispatcher.forward(request, response);
				break;
			}
			
			case "/pedido/confirmar_entrega": {
				try (DAOFactory daoFactory = DAOFactory.getInstance()) {
					int id = Integer.parseInt(request.getParameter("id"));
					dao_ped = daoFactory.getPedidoDAO();
					dao_ped.updateStatus(id, "entregue");
				}catch (ClassNotFoundException | IOException | SQLException ex) {
					session.setAttribute("error", ex.getMessage());
					response.sendError(500);
					break;
				}
				
				response.setContentType("text/plain");
				response.getWriter().println(request.getContextPath() + "/funcionario/pedidos");
				break;
			}
			
			case "/pedido/confirmar_pgmt": {
				try (DAOFactory daoFactory = DAOFactory.getInstance()) {
					int id = Integer.parseInt(request.getParameter("id"));
					dao_ped = daoFactory.getPedidoDAO();
					dao_ped.updateStatus(id, "pago");
				}catch (ClassNotFoundException | IOException | SQLException ex) {
					session.setAttribute("error", ex.getMessage());
					response.sendError(500);
					break;
				}
				
				response.setContentType("text/plain");
				response.getWriter().println(request.getContextPath() + "/funcionario/pedidos");
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
		PedidoDAO dao_ped;
		ProdutoPedidoDAO dao_prod_ped;
		AtendimentoDAO dao_atd;
		RequestDispatcher dispatcher;

		HttpSession session = request.getSession();
		String servletPath = request.getServletPath();

		switch (servletPath) {
			case "/pedido/createPresencial": {

				String json = request.getParameter("items");
				int comanda = Integer.parseInt(request.getParameter("comanda"));
				String observacao = request.getParameter("observacao");
				int id, qtd;
				String login = null;
				session = request.getSession(false);
				if (session.getAttribute("funcionario") != null) {
					Funcionario fun = (Funcionario) session.getAttribute("funcionario");
					login = fun.getLogin();
				} else {
					throw new SocketTimeoutException("Sessão expirou. Por favor faça login novamente.");
				}

				try (DAOFactory daoFactory = DAOFactory.getInstance()) {
					dao_prod = daoFactory.getProdutoDAO();
					dao_ped = daoFactory.getPedidoDAO();
					dao_prod_ped = daoFactory.getProdutoPedidoDAO();
					dao_atd = daoFactory.getAtendimentoDAO();

					JSONArray jsonArr = new JSONArray(json);

					try {
						daoFactory.beginTransaction();
						
						Pedido ped = new Pedido();
						ped.setComanda(comanda);
						ped.setFuncionarioLogin(login);
						ped.setObs(observacao);
						dao_ped.create(ped);
						int idPedido = dao_ped.getLastPedido();
						
						for (int i = 0; i < jsonArr.length(); i++) {
							String obj = jsonArr.get(i).toString();
							String[] values = obj.split(",");

							values[0] = values[0].replace("[", "");
							id = Integer.parseInt(values[0]);
							values[1] = values[1].replace("]", "");
							qtd = Integer.parseInt(values[1]);

							Produto prod = new Produto();
							prod.setId(id);
							prod.setQtd(qtd); //essa quantia será removida da disp. produto
							dao_prod.remove_items(prod);
							prod = dao_prod.read(id);

							ProdutoPedido pp = new ProdutoPedido();
							pp.setIdPedido(idPedido);
							pp.setIdProduto(prod.getId());
							pp.setQtd(qtd);
							pp.setValor(prod.getValor_venda());
							dao_prod_ped.create(pp);

							Atendimento atd = new Atendimento();
							atd.setIdPedido(idPedido);
							atd.setIdProduto(prod.getId());
							dao_atd.create(atd);
						}
						daoFactory.commitTransaction();
						daoFactory.endTransaction();
					} catch (SQLException ex) {
						session.setAttribute("error", ex.getMessage());
						daoFactory.rollbackTransaction();
						response.sendError(500);
						break;
					}
				} catch (ClassNotFoundException | IOException | SQLException ex) {
					session.setAttribute("error", ex.getMessage());
					response.sendError(500);
					break;
				}
				
				response.setContentType("text/plain");
				response.getWriter().println(request.getContextPath() + "/funcionario/pedidos");
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

	@Override
	public String toJson() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void toJson(Writer writable) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
