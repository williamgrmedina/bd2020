/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOFactory;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import dao.ProdutoPedidoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cliente;
import model.Pedido;
import model.Produto;

/**
 *
 * @author Medina
 */
@WebServlet(name = "ClienteController", urlPatterns = {"/cliente_welcome"})
public class ClienteController extends HttpServlet {

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
        ProdutoPedidoDAO dao_prod_ped;
        ProdutoDAO dao_prod;
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) {
            case "/cliente_welcome": {
                String result_url = "";

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_prod = daoFactory.getProdutoDAO();
                    dao_ped = daoFactory.getPedidoDAO();
                    Produto prod;
                    Pedido ped;

                    List<Pedido> pedidos = null;
                    if (session.getAttribute("cliente") != null) {
                        result_url = "/view/cliente/cliente_welcome.jsp";
                        Cliente clnt = (Cliente) session.getAttribute("cliente");
                        pedidos = dao_ped.readPedidos(clnt);
                    } else {
                        dispatcher = request.getRequestDispatcher(result_url);
                        dispatcher.forward(request, response);
                        break;
                    }

                    request.setAttribute("pedidos", pedidos);

                    dispatcher = request.getRequestDispatcher(result_url);
                    dispatcher.forward(request, response);
                    break;

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    session.setAttribute("error", ex.getMessage());
                    dispatcher = request.getRequestDispatcher(result_url);
                    dispatcher.forward(request, response);
                    break;
                }
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
