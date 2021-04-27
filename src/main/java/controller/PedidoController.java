/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.github.cliftonlabs.json_simple.Jsonable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DAOFactory;
import dao.FuncionarioDAO;
import dao.MyCancelamentoDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import dao.ProdutoPedidoDAO;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
import model.Cancelamento;
import model.Cliente;
import model.Funcionario;
import model.FuncionarioPedidos;
import model.Pedido;
import model.Produto;
import model.ProdutoPedido;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Medina
 */
@WebServlet(name = "PedidoController",
        urlPatterns = {
            "/pedidos",
            "/pedido/createPresencial",
            "/pedido/createOnline",
            "/pedido/confirmar",
            "/pedido/enviar",
            "/pedido/confirmar_entrega",
            "/pedido/confirmar_pgmt",
            "/pedido/cancelar_pedido",
            "/pedido/visualizar_todos",
            "/pedido/cancelar_produto",
            "/pedido/visualizar_produtos",
            "/pedido/visualizar_estatisticas",
            "/pedido/info_graphs_upper",
            "/pedido/info_graphs_lower"
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
        ProdutoPedidoDAO dao_prod_ped;
        ProdutoDAO dao_prod;
        FuncionarioDAO dao_f;
        
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        boolean gerente_session = false, funcionario_session = false, cliente_session = false;
        if (session != null) {
            if (session.getAttribute("gerente") != null) {
                gerente_session = true;
            } else if (session.getAttribute("funcionario") != null) {
                funcionario_session = true;
            } else if (session.getAttribute("cliente") != null) {
                cliente_session = true;
            }
        }

        switch (request.getServletPath()) {
            case "/pedidos": {

                String result_url = "/";
                if (gerente_session) {
                    result_url = "/view/pedido/gerente_pedidos.jsp";
                } else if (funcionario_session) {
                    result_url = "/view/pedido/funcionario_pedidos.jsp";
                }

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_prod = daoFactory.getProdutoDAO();
                    dao_ped = daoFactory.getPedidoDAO();
                    Produto prod;
                    Pedido ped;

                    List<Pedido> pedidos = null;

                    if (gerente_session) {
                        Funcionario f = (Funcionario) session.getAttribute("gerente");
                        pedidos = dao_ped.readPedidos(f);
                    } else if (funcionario_session) {
                        Funcionario f = (Funcionario) session.getAttribute("funcionario");
                        pedidos = dao_ped.readPedidos(f);
                    } else {
                        dispatcher = request.getRequestDispatcher(result_url);
                        dispatcher.forward(request, response);
                        break;
                    }

                    request.setAttribute("pedidos", pedidos);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    session.setAttribute("error", ex.getMessage());
                    dispatcher = request.getRequestDispatcher(result_url);
                    dispatcher.forward(request, response);
                    break;
                }

                dispatcher = request.getRequestDispatcher(result_url);
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

            case "/pedido/createOnline": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_prod = daoFactory.getProdutoDAO();

                    request.setAttribute("produtos", dao_prod.all());
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    session.setAttribute("error", ex.getMessage());
                    dispatcher = request.getRequestDispatcher("/view/cliente/cliente_welcome.jsp");
                    dispatcher.forward(request, response);
                    break;
                }

                dispatcher = request.getRequestDispatcher("/view/pedido/createOnline.jsp");
                dispatcher.forward(request, response);
                break;
            }

            case "/pedido/confirmar":
            case "/pedido/enviar":
            case "/pedido/confirmar_entrega":
            case "/pedido/confirmar_pgmt": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    dao_ped = daoFactory.getPedidoDAO();
                    Funcionario f;

                    String path = request.getServletPath();
                    if (session != null) {
                        if (gerente_session) {
                            f = (Funcionario) session.getAttribute("gerente");
                        } else if (funcionario_session) {
                            f = (Funcionario) session.getAttribute("funcionario");
                        } else {
                            break;
                        }

                        switch (path) {
                            case "/pedido/confirmar":
                                Pedido ped = dao_ped.read(id);
                                ped.setStatus("em preparo");
                                ped.setFuncionarioLogin(f.getLogin());
                                dao_ped.update(ped);
                                break;
                            case "/pedido/enviar":
                                dao_ped.updateStatus(id, "enviado para entrega");
                                break;
                            case "/pedido/confirmar_entrega":
                                dao_ped.updateStatus(id, "entregue");
                                break;
                            case "/pedido/confirmar_pgmt":
                                dao_ped.updateStatus(id, "pago");
                                dao_ped.finalize(id);
                                break;
                            default:
                                break;
                        }
                    }

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    session.setAttribute("error", ex.getMessage());
                    response.sendError(500);
                    break;
                }

                response.setContentType("text/plain");
                response.getWriter().println(request.getContextPath() + "/pedidos");
                break;
            }

            case "/pedido/cancelar_pedido": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    int idPed = Integer.parseInt(request.getParameter("idPed"));

                    try {
                        daoFactory.beginTransaction();

                        dao_ped = daoFactory.getPedidoDAO();
                        dao_prod_ped = daoFactory.getProdutoPedidoDAO();
                        dao_prod = daoFactory.getProdutoDAO();

                        Produto prod;
                        Pedido ped;

                        List<ProdutoPedido> produtos = dao_prod_ped.readProdutos(idPed);
                        int idPedido, idProduto, qtd_atual, qtd_a_remover;

                        for (ProdutoPedido pp : produtos) {
                            //readicione produtos à estoque disponivel e 
                            //remova-os da relacao produtos_pedido
                            idPedido = pp.getIdPedido();
                            idProduto = pp.getIdProduto();

                            qtd_a_remover = pp.getQtd();
                            prod = dao_prod.read(idProduto);
                            qtd_atual = prod.getQtd();
                            prod.setQtd(qtd_atual + qtd_a_remover);

                            dao_prod.update(prod);
                            dao_prod_ped.delete(idPedido, idProduto);

                            //atualize o status do pedido para cancelado
                            dao_ped.updateStatus(idPedido, "cancelado");
                            dao_ped.finalize(idPedido);
                        }

                        daoFactory.commitTransaction();
                        daoFactory.endTransaction();

                    } catch (SQLException ex) {
                        daoFactory.rollbackTransaction();
                        Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Controller", ex);
                        session.setAttribute("error", ex.getMessage());
                        response.sendError(500);
                        break;
                    }

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", ex.getMessage());
                    response.sendError(500);
                    break;
                }

                response.setContentType("text/plain");
                response.getWriter().println(request.getContextPath() + "/pedidos");
                break;
            }

            case "/pedido/visualizar_todos": {
                String result_url = "/";
                if (gerente_session) {
                    result_url = "/view/pedido/gerente_pedidos_all.jsp";

                    try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao_ped = daoFactory.getPedidoDAO();
                        List<Pedido> pedidos = dao_ped.all();
                        request.setAttribute("pedidos", pedidos);

                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                dispatcher = request.getRequestDispatcher(result_url);
                dispatcher.forward(request, response);
                break;
            }

            case "/pedido/visualizar_produtos": {
                String result_url = "/";
                if (gerente_session) {
                    result_url = "/view/pedido/gerente_visual_produtos.jsp";
                } else if (funcionario_session) {
                    result_url = "/view/pedido/funcionario_visual_produtos.jsp";
                } else if (cliente_session) {
                    result_url = "/view/pedido/cliente_visual_produtos.jsp";
                } else {
                    dispatcher = request.getRequestDispatcher(result_url);
                    dispatcher.forward(request, response);
                    break;
                }

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_ped = daoFactory.getPedidoDAO();
                    dao_prod = daoFactory.getProdutoDAO();
                    dao_prod_ped = daoFactory.getProdutoPedidoDAO();
                    Produto produtoObjeto;
                    Pedido pedidoObjeto;
                    int idPed = Integer.parseInt(request.getParameter("id"));

                    List<ProdutoPedido> produtos = dao_prod_ped.readProdutos(idPed);

                    for (ProdutoPedido p : produtos) {
                        produtoObjeto = dao_prod.read(p.getIdProduto());
                        pedidoObjeto = dao_ped.read(p.getIdPedido());
                        p.setProduto(produtoObjeto);
                        p.setPedido(pedidoObjeto);
                    }

                    request.setAttribute("produtos", produtos);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", ex.getMessage());
                    response.sendError(500);
                    break;
                }

                dispatcher = request.getRequestDispatcher(result_url);
                dispatcher.forward(request, response);
                break;
            }

            case "/pedido/visualizar_estatisticas": {
                String result_url = "/";
                if (gerente_session) {
                    result_url = "/view/pedido/estatisticas.jsp";

                    try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                        dao_ped = daoFactory.getPedidoDAO();
                        Pedido pedidoObjeto;
                        int ano_first_pedido = dao_ped.getMinYear();
                        int ano_last_pedido = dao_ped.getMaxYear();

                        request.setAttribute("first_year", ano_first_pedido);
                        request.setAttribute("last_year", ano_last_pedido);

                    } catch (ClassNotFoundException | IOException | SQLException ex) {
                        Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Controller", ex);
                        session.setAttribute("error", ex.getMessage());
                        response.sendError(500);
                        break;
                    }
                }
                dispatcher = request.getRequestDispatcher(result_url);
                dispatcher.forward(request, response);
                break;
            }

            case "/pedido/info_graphs_upper": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {

                    dao_ped = daoFactory.getPedidoDAO();
                    dao_prod = daoFactory.getProdutoDAO();
                    dao_prod_ped = daoFactory.getProdutoPedidoDAO();
                    dao_f = daoFactory.getFuncionarioDAO();
                    
                    int year = Integer.parseInt(request.getParameter("year"));
                    int max_year = dao_ped.getMaxYear();
                    int cancelados = dao_ped.getCanceladosCount(year);
                    int pagos = dao_ped.getPagosCount(year);

                    Gson gson = new GsonBuilder().create();
                    List< List<String>> jsonArray = new ArrayList();

                    List<String> pie_chart_data = new ArrayList();
                    //populate pie chart data
                    String pagosJson
                            = gson.toJson(pagos);
                    pie_chart_data.add(pagosJson);
                    String canceladosJson
                            = gson.toJson(cancelados);
                    pie_chart_data.add(canceladosJson);

                    List<String> line_chart_presencial = new ArrayList();
                    List<String> line_chart_online = new ArrayList();
                    List<String> receitas = new ArrayList();
                    List<String> despesas = new ArrayList();
                    //populate line chart data
                    int data, months;
                    String pedidosJson;
                    if (year != max_year) {
                        months = 12;
                    } else {
                        //populate until current month
                        months = dao_ped.getMaxMonth(year);
                    }
                    for (int i = 1; i <= months; i++) {
                        data = 
                                dao_ped.getMonthYearPresCount(i, year);
                        pedidosJson = gson.toJson(data);
                        line_chart_presencial.add(pedidosJson);
                        
                        data = 
                                dao_ped.getMonthYearOnlineCount(i, year);
                        pedidosJson = gson.toJson(data);
                        line_chart_online.add(pedidosJson);
                        
                        
                        List <Pedido> pedidos =
                                dao_ped.getMonthYearPedidos(i, year);                        
                        List <Funcionario> funcionarios =
                                dao_f.all();
                        
                        BigDecimal receita_mes = new BigDecimal(0);
                        BigDecimal despesa_mes = new BigDecimal(0);                      
                        
                        for(Pedido p : pedidos){
                            List<ProdutoPedido> ppList = dao_prod_ped.readProdutos(p.getId());
                            for(ProdutoPedido prod_ped : ppList){
                                BigDecimal qtd = new BigDecimal (prod_ped.getQtd());
                                receita_mes = receita_mes.add(prod_ped.getValor().multiply(qtd));
                                
                                int id_prod = prod_ped.getIdProduto();
                                Produto prod = dao_prod.read(id_prod);
                                despesa_mes = despesa_mes.add(prod.getValor_compra());
                            }
                        }
                        
                        for(Funcionario f : funcionarios){
                            BigDecimal salario = new BigDecimal(f.getSalario());
                            despesa_mes = despesa_mes.add(salario);
                        }
                        
                        String receitaJson = gson.toJson(receita_mes);
                        receitas.add(receitaJson);
                        
                        String despesaJson = gson.toJson(despesa_mes);
                        despesas.add(despesaJson);
                        
                    }

                    jsonArray.add(pie_chart_data);
                    jsonArray.add(line_chart_presencial);
                    jsonArray.add(line_chart_online);
                    jsonArray.add(receitas);
                    jsonArray.add(despesas);

                    response.getOutputStream().print(jsonArray.toString());

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    session.setAttribute("error", ex.getMessage());
                    response.sendError(500);
                    break;
                }
                break;
            }
            
            case "/pedido/info_graphs_lower": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {

                    dao_ped = daoFactory.getPedidoDAO();
                    dao_f = daoFactory.getFuncionarioDAO();
                    int month = Integer.parseInt(request.getParameter("month"));
                    int year = Integer.parseInt(request.getParameter("year"));

                    List <Funcionario> funcionarios = dao_f.all();
                    List<FuncionarioPedidos> array = new ArrayList();
                    
                    for (Funcionario f : funcionarios){
                        int atendidos = dao_ped.getPedidosByFuncionario(f, month, year);
                        FuncionarioPedidos fpedidos = new FuncionarioPedidos();
                        fpedidos.setNome(f.getPNome() + " " + f.getSNome());
                        fpedidos.setQtd(atendidos);
                        array.add(fpedidos);
                    }
                    
                    Gson gson = new GsonBuilder().create();
                    String jsonArray = gson.toJson(array);
                            
                    response.getOutputStream().print(jsonArray);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    session.setAttribute("error", ex.getMessage());
                    response.sendError(500);
                    break;
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

        ProdutoDAO dao_prod;
        PedidoDAO dao_ped;
        MyCancelamentoDAO dao_cancelamento;
        ProdutoPedidoDAO dao_prod_ped;
        RequestDispatcher dispatcher;

        HttpSession session = request.getSession();
        String servletPath = request.getServletPath();

        switch (servletPath) {
            case "/pedido/createPresencial":
            case "/pedido/createOnline": {

                String json = request.getParameter("items");
                int comanda = 0;
                if (servletPath.equals("/pedido/createPresencial")) {
                    comanda = Integer.parseInt(request.getParameter("comanda"));
                }
                String observacao = request.getParameter("observacao");
                int id, qtd;
                String login = null;
                session = request.getSession(false);
                if (session.getAttribute("funcionario") != null) {
                    Funcionario fun = (Funcionario) session.getAttribute("funcionario");
                    login = fun.getLogin();
                } else if (session.getAttribute("cliente") != null) {
                    Cliente cl = (Cliente) session.getAttribute("cliente");
                    login = cl.getLogin();
                } else {
                    throw new SocketTimeoutException("Sessão expirou. Por favor faça login novamente.");
                }

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_prod = daoFactory.getProdutoDAO();
                    dao_ped = daoFactory.getPedidoDAO();
                    dao_prod_ped = daoFactory.getProdutoPedidoDAO();

                    JSONArray jsonArr = new JSONArray(json);
                    try {
                        daoFactory.beginTransaction();

                        Pedido ped = new Pedido();
                        ped.setObs(observacao);

                        if (servletPath.equals("/pedido/createPresencial")) {
                            ped.setComanda(comanda);
                            ped.setFuncionarioLogin(login);
                            dao_ped.create(ped);
                        } else {
                            ped.setClienteLogin(login);
                            dao_ped.createOnline(ped);
                        }

                        int idPedido = dao_ped.getLastPedidoId();

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
                        }
                        daoFactory.commitTransaction();
                        daoFactory.endTransaction();
                    } catch (SQLException ex) {
                        session.setAttribute("error", ex.getMessage());
                        daoFactory.rollbackTransaction();
                        response.sendError(500);
                        break;
                    } catch (NumberFormatException | JSONException ex) {
                        session.setAttribute("error", ex.getMessage());
                        daoFactory.rollbackTransaction();
                        response.sendError(500);
                    }
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    session.setAttribute("error", ex.getMessage());
                    response.sendError(500);
                    break;
                }

                response.setContentType("text/plain");
                response.getWriter().println(request.getContextPath() + "/pedidos");
                break;
            }

            case "/pedido/cancelar_produto": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    int idPed = Integer.parseInt(request.getParameter("id_pedido"));
                    int idProd = Integer.parseInt(request.getParameter("id_produto"));
                    int qtd = Integer.parseInt(request.getParameter("qtd"));

                    dao_prod_ped = daoFactory.getProdutoPedidoDAO();
                    dao_ped = daoFactory.getPedidoDAO();
                    dao_cancelamento = daoFactory.getCancelamentoDAO();

                    try {
                        daoFactory.beginTransaction();

                        ProdutoPedido pp = dao_prod_ped.read(idPed, idProd);
                        /*Cancelamento c = new Cancelamento();
                        c.setIdPedido(idPed);
                        c.setIdProduto(idProd);
                        c.setQtd(qtd);
                        
                        dao_cancelamento.create(c);*/
                        if (pp.getQtd() > qtd) {
                            pp.setQtd(pp.getQtd() - qtd);
                            dao_prod_ped.update(pp);
                        } else {
                            dao_prod_ped.delete(idPed, idProd);
                            List itens_do_pedido = dao_prod_ped.readProdutos(idPed);
                            if (itens_do_pedido.isEmpty()) {
                                Pedido ped = dao_ped.read(idPed);

                                ped.setStatus("cancelado");
                                Timestamp fim_atd = new Timestamp(System.currentTimeMillis());
                                ped.setFimAtd(fim_atd);

                                dao_ped.update(ped);
                            }
                        }

                        daoFactory.commitTransaction();
                        daoFactory.endTransaction();

                    } catch (SQLException ex) {
                        daoFactory.rollbackTransaction();
                        session.setAttribute("error", ex.getMessage());
                        response.sendError(500);
                        break;
                    }

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    session.setAttribute("error", ex.getMessage());
                    response.sendError(500);
                    break;
                }

                response.setContentType("text/plain");
                response.getWriter().println(request.getContextPath() + "/pedidos");
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
