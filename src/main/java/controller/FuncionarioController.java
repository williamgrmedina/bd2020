/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOFactory;
import dao.FuncionarioDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
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
import model.Funcionario;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Medina
 */
@WebServlet(
        name = "FuncionarioController", 
        urlPatterns = {"/gerente",
			"/funcionario/create",
			"/funcionario/update"
        })
public class FuncionarioController extends HttpServlet {

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
        FuncionarioDAO dao;
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
		Funcionario fun;
        
        switch(request.getServletPath()){
            case "/gerente":
				try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
					dao = daoFactory.getFuncionarioDAO();
					
					Funcionario gerente = (Funcionario)session.getAttribute("gerente");
					
					List<Funcionario> funList = dao.get_gerenciados(gerente.getLogin());					
					
					request.setAttribute("funList", funList);
				} catch (Exception ex) {
					request.getSession().setAttribute("error", ex.getMessage());
				} 

				dispatcher = request.getRequestDispatcher("/view/funcionario/gerente_index.jsp");
				dispatcher.forward(request, response);
				break;
			case "/funcionario/create":
				dispatcher = request.getRequestDispatcher("/view/funcionario/create.jsp");
                dispatcher.forward(request, response);
				break;
			case "/funcionario/update": {
				try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getFuncionarioDAO();

                    fun = dao.read(request.getParameter("login"));
                    request.setAttribute("funcionario", fun);

                    dispatcher = request.getRequestDispatcher("/view/funcionario/update.jsp");
                    dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
					request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/gerente");
                }
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
        
		FuncionarioDAO dao;
		Funcionario fun = new Funcionario();
		HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
		
		String servletPath = request.getServletPath();
		
		switch(request.getServletPath()){
			case "/funcionario/create":
				
				DiskFileItemFactory factory = new DiskFileItemFactory();
				
				Funcionario gerente = (Funcionario)session.getAttribute("gerente");
				fun.setGerenteLogin(gerente.getLogin());
				
				// Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
				
				try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    // Parse the request
                    List<FileItem> items = upload.parseRequest(request);
					Iterator<FileItem> iter = items.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();

						// Process a regular form field
						String fieldName = item.getFieldName();
						String fieldValue = item.getString();

						switch (fieldName) {
							case "login":
								fun.setLogin(fieldValue);
								break;
							case "senha":
								fun.setSenha(fieldValue);
								break;
							case "nome":
								fun.setPNome(fieldValue);
								break;
							case "sobrenome":
								fun.setSNome(fieldValue);
								break;	
							case "email":
								fun.setEmail(fieldValue);
								break;	
							case "cargo":
								fun.setCargo(fieldValue);
								break;
							case "setor":
								fun.setSetor(fieldValue);
								break;	
							case "salario":
								fun.setSalario(Double.parseDouble(fieldValue));
								break;	
							case "efetivacao":
								java.util.Date dataEfetivacao = new SimpleDateFormat("yyyy-mm-dd").parse(fieldValue);
								fun.setData_efetivacao(new Date(dataEfetivacao.getTime()));
								break;
						}
					}
				fun.setGerenteLogin(gerente.getLogin());
						
				dao = daoFactory.getFuncionarioDAO();
				dao.create(fun);

				response.sendRedirect(request.getContextPath() + "/gerente");
				} catch (ParseException ex) {
                    Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", "O formato de data não é válido. Por favor entre data no formato dd/mm/aaaa");
                    response.sendRedirect(request.getContextPath() + servletPath);
				} catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + servletPath);
                } catch (Exception ex) {
                    Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", "Erro ao gravar arquivo no servidor.");
                    response.sendRedirect(request.getContextPath() + servletPath);
                }
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
