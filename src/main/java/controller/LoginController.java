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
                if (session != null) {
					if(session.getAttribute("gerente") != null){
						dispatcher = request.getRequestDispatcher("/view/funcionario/gerente_welcome.jsp");
					}
					else if(session.getAttribute("funcionario") != null){
						dispatcher = request.getRequestDispatcher("/view/funcionario/funcionario_welcome.jsp");
					}
					else dispatcher = request.getRequestDispatcher("/index.jsp");
				}
				else dispatcher = request.getRequestDispatcher("/index.jsp");
				
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
        Funcionario fun = new Funcionario();
        HttpSession session = request.getSession();

        switch (request.getServletPath()) {
            case "/login":
                fun.setLogin(request.getParameter("login"));
                fun.setSenha(request.getParameter("senha"));
                
                //retorna conexao com banco de dados se o banco for suportado
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    
                    dao = daoFactory.getFuncionarioDAO();   
                                         
                    /*procura no banco de dados pelas informacoes de login e senha fornecidos nos campos.
                    Se estiverem corretos, seta restante dos dados (salario, etc) ao funcionario fun*/
                    dao.authenticate(fun);
                    
					//gerente: pagina especial
					if(fun.getCargo().equalsIgnoreCase("gerente")){
						session.setAttribute("gerente", fun);
					}
					//funcinario comum
					else session.setAttribute("funcionario", fun);
						
                } catch (ClassNotFoundException | IOException | SQLException | SecurityException ex) {
                    session.setAttribute("error", ex.getMessage());
                    System.out.println(ex.getMessage());
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
