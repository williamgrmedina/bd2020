/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ClienteDAO;
import dao.DAOFactory;
import dao.FuncionarioDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import dao.ProdutoPedidoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cliente;
import model.Funcionario;
import model.Pedido;
import model.Produto;
import model.ProdutoPedido;

/**
 *
 * @author Medina
 */
@WebServlet(name = "TestController",
        urlPatterns = {
            "/test/generate_dataset"
        }
)
public class TestController extends HttpServlet {

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
        FuncionarioDAO dao_fun;
        ClienteDAO dao_cliente;
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) {
            case "/test/generate_dataset": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {

                    long hr_ini_MIN = Timestamp.valueOf("2018-01-01 00:00:00").getTime();
                    long hr_ini_MAX = Timestamp.valueOf("2021-04-20 00:00:00").getTime();
                    long diff = hr_ini_MAX - hr_ini_MIN + 1;
                    
                    dao_ped = daoFactory.getPedidoDAO();
                    dao_prod = daoFactory.getProdutoDAO();
                    dao_prod_ped = daoFactory.getProdutoPedidoDAO();
                    dao_fun = daoFactory.getFuncionarioDAO();
                    dao_cliente = daoFactory.getClienteDAO();

                    double PROB_PEDIDO_ONLINE = 0.7;
                    double PROB_CANCELAMENTO = 0.05;
                    
                    try{
                        daoFactory.beginTransaction();
                        for(int i = 0; i < 3000; i++){                     
                            double res = Math.random();
                            Funcionario f = dao_fun.readRandom();                    
                            int comanda = 1 + (int) (Math.random() * (Integer.MAX_VALUE - 1) );
                            Timestamp hr_ini = new Timestamp(hr_ini_MIN + (long) (Math.random() * diff));
                            Timestamp hr_fim = new Timestamp( Timestamp.valueOf(hr_ini.toString()).getTime() +  (long) (Math.random() * 3600000));

                            Pedido p = new Pedido();                  
                            p.setFuncionarioLogin(f.getLogin());
                            p.setComanda(comanda);
                            if (PROB_PEDIDO_ONLINE > res) {
                                Cliente c = dao_cliente.readRandom();
                                p.setClienteLogin(c.getLogin());
                                dao_ped.createOnline(p);
                            } else {
                                //é pedido presencial
                                dao_ped.create(p);
                            }
                            int id = dao_ped.getLastPedidoId();
                            p = dao_ped.read(id);
                            p.setInicioAtd(hr_ini);
                            p.setFimAtd(hr_fim);
                            if(PROB_CANCELAMENTO > res){
                                p.setStatus("cancelado");
                            }
                            else {
                                p.setStatus("pago");

                                Produto prod = dao_prod.readRandom();
                                ProdutoPedido pp = new ProdutoPedido();
                                pp.setIdPedido(p.getId());
                                pp.setIdProduto(prod.getId());
                                pp.setValor(prod.getValor_venda());
                                pp.setQtd(3);                       
                                dao_prod_ped.create(pp);
                            }
                            dao_ped.updateAllAttr(p);
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
