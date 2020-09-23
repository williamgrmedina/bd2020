/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOFactory;
import dao.FuncionarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Funcionario;
import model.Gerente;

/**
 *
 * @author dskaster
 */
@WebServlet(
        name = "LoginController",
        urlPatterns = {
            "",
            "/login",
            "/logout"
        })
public class LoginController extends HttpServlet {

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
        HttpSession session;
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) {
            case "": {
                session = request.getSession(false);

                if (session != null && session.getAttribute("usuario") != null) {
                    dispatcher = request.getRequestDispatcher("/welcome.jsp");
                } else {
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                }

                dispatcher.forward(request, response);

                break;
            }
            
            case "/logout": {
                session = request.getSession(false);

                if (session != null) {
                    session.invalidate();
                }

                response.sendRedirect(request.getContextPath() + "/");
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
        FuncionarioDAO dao;
        HttpSession session = request.getSession();

        switch (request.getServletPath()) {
            case "/login":
                
                String login = request.getParameter("login");
                String senha = request.getParameter("senha");
                
                //retorna conexao com banco de dados se o banco for suportado
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    dao = daoFactory.getFuncionarioDAO();              
                     
                    Funcionario fun = dao.getFuncionarioType(login);
                    
                    fun.setLogin(login);
                    fun.setSenha(senha);
                    
                    /*procura no banco de dados pelas informacoes de login e senha fornecidos nos campos.
                    Se estiverem corretos, seta restante dos dados (salario, etc) ao funcionario fun*/
                    dao.authenticate(fun);
                    
                    session.setAttribute("usuario", fun);
                    
                } catch (ClassNotFoundException | IOException | SQLException | SecurityException | NoSuchFieldException ex) {
                    session.setAttribute("error", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/");
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
