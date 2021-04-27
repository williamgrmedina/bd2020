/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Funcionario;
import model.FuncionarioPedidos;
import model.Pedido;

/**
 *
 * @author Medina
 */
public class MyPedidoDAO implements PedidoDAO {

    Connection connection;

    private final static String CREATE_PRESENCIAL_QUERY
            = "INSERT INTO restaurante.pedidos "
            + "(funcionario_login, comanda, status, tipo, observacao) "
            + //id é gerado automaticamente
            "VALUES (?, ?, 'atendido', 'presencial', ?);";

    private final static String CREATE_ONLINE_QUERY
            = "INSERT INTO restaurante.pedidos "
            + "(cliente_login, status, tipo, observacao) "
            + //id é gerado automaticamente
            "VALUES (?, 'aguardando confirmacao', 'online', ?);";

    /*private final static String CREATE_PROD_PEDIDO_QUERY =
		"INSERT INTO restaurante.produtos_pedidos " +
		"(pedido_idPedido, produto_idProduto, valor, qtd, observacao)" +
		"VALUES (?, ?, ?, ?, ?);";
	
	private final static String CREATE_ATENDIMENTO_QUERY =
		"INSERT INTO restaurante.atendimentos " +
		"(inicio, atend_idPedido, atend_idProduto)" +
		"VALUES (?, ?, ?);";*/
    private final static String READ_QUERY
            = "SELECT * FROM restaurante.pedidos "
            + "WHERE idPedido = ?;";

    private final static String READ_ALL_QUERY
            = "SELECT * FROM restaurante.pedidos;";

    private final static String READ_PEDIDOS_NORMAL_QUERY
            = "SELECT * "
            + "FROM restaurante.pedidos "
            + "WHERE cliente_login IS NOT NULL AND status = 'aguardando confirmacao' "
            + "OR funcionario_login = ?;";

    private final static String READ_PEDIDOS_GERENTE_QUERY
            = "SELECT * "
            + "FROM restaurante.pedidos "
            + "WHERE status <> 'pago' AND status <> 'cancelado';";

    private final static String READ_PEDIDOS_CLIENTE_QUERY
            = "SELECT * "
            + "FROM restaurante.pedidos "
            + "WHERE cliente_login = ?;";

    private final static String UPDATE_QUERY
            = "UPDATE restaurante.pedidos "
            + "SET comanda = ?, cliente_login = ?, funcionario_login = ?, tipo = ?, status = ?, observacao = ?, fim_atd = ? "
            + "WHERE idPedido = ?;";

    private final static String UPDATE_TOTAL_QUERY
            = "UPDATE restaurante.pedidos "
            + "SET comanda = ?, cliente_login = ?, funcionario_login = ?, tipo = ?, status = ?, observacao = ?, inicio_atd = ?, fim_atd = ? "
            + "WHERE idPedido = ?;";

    private final static String UPDATE_COMANDA_QUERY
            = "UPDATE restaurante.pedidos "
            + "SET comanda = ? "
            + "WHERE comanda = ?;";

    private final static String UPDATE_FUNCIONARIO_QUERY
            = "UPDATE restaurante.pedidos "
            + "SET funcionario_login = ? "
            + "WHERE cliente_login = ?;";

    private final static String UPDATE_STATUS_QUERY
            = "UPDATE restaurante.pedidos "
            + "SET status = ? "
            + "WHERE idPedido = ?;";

    private final static String FINALIZE_QUERY
            = "UPDATE restaurante.pedidos "
            + "SET fim_atd = CURRENT_TIMESTAMP "
            + "WHERE idPedido = ?;";

    private final static String DELETE_QUERY
            = "DELETE FROM restaurante.pedidos "
            + "WHERE idPedido = ?;";

    private final static String GET_LAST_QUERY
            = "SELECT MAX(idPedido) AS idPedido FROM restaurante.pedidos;";

    private final static String CANCELADOS_YEAR_QUERY
            = "SELECT COUNT(*) AS cnt FROM restaurante.pedidos "
            + "WHERE status = 'cancelado' AND YEAR(inicio_atd) = ?;";

    private final static String PAGOS_YEAR_QUERY
            = "SELECT COUNT(*) AS cnt FROM restaurante.pedidos "
            + "WHERE status = 'pago' AND YEAR(inicio_atd) = ?;";

    private final static String MAX_YEAR_QUERY
            = "SELECT MAX(YEAR(inicio_atd)) AS max_year FROM restaurante.pedidos;";

    private final static String MAX_MONTH_QUERY
            = "SELECT MAX(MONTH(inicio_atd)) AS max_month FROM restaurante.pedidos "
            + "WHERE YEAR(inicio_atd) = ?;";

    private final static String MIN_YEAR_QUERY
            = "SELECT MIN(YEAR(inicio_atd)) AS min_year FROM restaurante.pedidos;";

    private final static String MONTH_YEAR_PRESENCIAL_QUERY
            = "SELECT COUNT(*) AS cnt FROM restaurante.pedidos "
            + "WHERE status = 'pago' AND tipo = 'presencial' AND MONTH(inicio_atd) = ? AND YEAR(inicio_atd) = ?;";

    private final static String MONTH_YEAR_ONLINE_QUERY
            = "SELECT COUNT(*) AS cnt FROM restaurante.pedidos "
            + "WHERE status = 'pago' AND tipo = 'online' AND MONTH(inicio_atd) = ? AND YEAR(inicio_atd) = ?;";

    private final static String READ_RANDOM_QUERY
            = "SELECT * FROM restaurante.pedidos "
            + "ORDER BY RAND() "
            + "LIMIT 1;";

    private final static String PEDIDOS_BY_FUNCIONARIO_QUERY
            = "SELECT COUNT(*) AS pedidos_atendidos "
            + "FROM restaurante.pedidos "
            + "WHERE funcionario_login = ? "
            + "AND MONTH(inicio_atd) = ? "
            + "AND YEAR(inicio_atd) = ?;";
    
    public final static String MONTH_YEAR_PEDIDOS_QUERY 
            = "SELECT * FROM restaurante.pedidos "
            + "WHERE MONTH(inicio_atd) = ? "
            + "AND YEAR(inicio_atd) = ? "
            + "AND status = 'pago';";

    public MyPedidoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Pedido p) throws SQLException {
        //padrao pedido presencial. Para pedido online, utilize o metodo createOnline().
        try (PreparedStatement statement = connection.prepareStatement(CREATE_PRESENCIAL_QUERY)) {
            statement.setString(1, p.getFuncionarioLogin());
            statement.setInt(2, p.getComanda());
            statement.setString(3, p.getObs());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyPedidoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao inserir pedido.");
        }
    }

    @Override
    public void createOnline(Pedido p) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ONLINE_QUERY)) {
            statement.setString(1, p.getClienteLogin());
            statement.setString(2, p.getObs());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MyPedidoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao inserir pedido.");
        }
    }

    @Override
    public Pedido read(Integer id) throws SQLException {
        Pedido p = new Pedido();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    p.setId(result.getInt("idPedido"));
                    p.setComanda(result.getInt("comanda"));
                    p.setClienteLogin(result.getString("cliente_login"));
                    p.setFuncionarioLogin(result.getString("funcionario_login"));
                    p.setStatus(result.getString("status"));
                    p.setTipo(result.getString("tipo"));
                    p.setObs(result.getString("observacao"));
                    return p;
                } else {
                    throw new SQLException("Erro ao visualizar pedido: pedido não encontrado.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().equals("Erro ao visualizar pedido: pedido não encontrado.")) {
                throw new SQLException("Erro ao visualizar pedido: pedido não encontrado.");
            } else {
                throw new SQLException("Erro ao visualizar pedido.");
            }
        }
    }

    @Override
    public List<Pedido> readPedidos(Funcionario f) throws SQLException {
        List<Pedido> allPed = new ArrayList<>();

        String cargo = f.getCargo();
        if (cargo.equalsIgnoreCase("gerente")) {
            try (PreparedStatement statement = connection.prepareStatement(READ_PEDIDOS_GERENTE_QUERY)) {

                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    Pedido p = new Pedido();
                    p.setId(result.getInt("idPedido"));
                    p.setComanda(result.getInt("comanda"));
                    p.setClienteLogin(result.getString("cliente_login"));
                    p.setFuncionarioLogin(result.getString("funcionario_login"));
                    p.setComanda(result.getInt("comanda"));
                    p.setTipo(result.getString("tipo"));
                    p.setStatus(result.getString("status"));
                    p.setObs(result.getString("observacao"));
                    allPed.add(p);
                }
                return allPed;
            } catch (SQLException ex) {
                Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao listar pedidos do gerente.");
            }
        } else {
            //eh funcionario normal
            try (PreparedStatement statement = connection.prepareStatement(READ_PEDIDOS_NORMAL_QUERY)) {
                statement.setString(1, f.getLogin());

                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    Pedido p = new Pedido();
                    p.setId(result.getInt("idPedido"));
                    p.setComanda(result.getInt("comanda"));
                    p.setClienteLogin(result.getString("cliente_login"));
                    p.setFuncionarioLogin(result.getString("funcionario_login"));
                    p.setComanda(result.getInt("comanda"));
                    p.setTipo(result.getString("tipo"));
                    p.setStatus(result.getString("status"));
                    p.setObs(result.getString("observacao"));
                    allPed.add(p);
                }
                return allPed;
            } catch (SQLException ex) {
                Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao listar pedidos do funcionário.");
            }
        }
    }

    @Override
    public List<Pedido> readPedidos(Cliente clnt) throws SQLException {
        List<Pedido> allPed = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(READ_PEDIDOS_CLIENTE_QUERY)) {

            statement.setString(1, clnt.getLogin());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Pedido p = new Pedido();
                p.setId(result.getInt("idPedido"));
                p.setComanda(result.getInt("comanda"));
                p.setClienteLogin(result.getString("cliente_login"));
                p.setFuncionarioLogin(result.getString("funcionario_login"));
                p.setComanda(result.getInt("comanda"));
                p.setTipo(result.getString("tipo"));
                p.setStatus(result.getString("status"));
                p.setObs(result.getString("observacao"));
                allPed.add(p);
            }
            return allPed;
        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar pedidos do cliente.");
        }
    }

    @Override
    public void update(Pedido p) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, p.getComanda());
            statement.setString(2, p.getClienteLogin());
            statement.setString(3, p.getFuncionarioLogin());
            statement.setString(4, p.getTipo());
            statement.setString(5, p.getStatus());
            statement.setString(6, p.getObs());
            statement.setTimestamp(7, p.getFimAtd());
            statement.setInt(8, p.getId());
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar pedido: um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao atualizar pedido.");
            }
        }
    }

    @Override
    public void updateAllAttr(Pedido p) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TOTAL_QUERY)) {
            statement.setInt(1, p.getComanda());
            statement.setString(2, p.getClienteLogin());
            statement.setString(3, p.getFuncionarioLogin());
            statement.setString(4, p.getTipo());
            statement.setString(5, p.getStatus());
            statement.setString(6, p.getObs());
            statement.setTimestamp(7, p.getInicioAtd());
            statement.setTimestamp(8, p.getFimAtd());
            statement.setInt(9, p.getId());
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao atualizar pedido.");
        }
    }

    public void updateTotal(Pedido p) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, p.getComanda());
            statement.setString(2, p.getClienteLogin());
            statement.setString(3, p.getFuncionarioLogin());
            statement.setString(4, p.getTipo());
            statement.setString(5, p.getStatus());
            statement.setString(6, p.getObs());
            statement.setTimestamp(7, p.getInicioAtd());
            statement.setTimestamp(8, p.getFimAtd());
            statement.setInt(9, p.getId());
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar pedido: um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao atualizar pedido.");
            }
        }
    }

    @Override
    public void updateStatus(int id, String status) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_QUERY)) {
            statement.setString(1, status);
            statement.setInt(2, id);

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao atualizar pedido: um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao atualizar pedido.");
            }
        }
    }

    @Override
    public void finalize(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FINALIZE_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao finalizar pedido.");
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao remover pedido.");
        }
    }

    @Override
    public List<Pedido> all() throws SQLException {
        List<Pedido> allPed = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Pedido p = new Pedido();
                p.setId(result.getInt("idPedido"));
                p.setComanda(result.getInt("comanda"));
                p.setClienteLogin(result.getString("cliente_login"));
                p.setFuncionarioLogin(result.getString("funcionario_login"));
                p.setStatus(result.getString("status"));
                p.setTipo(result.getString("tipo"));
                p.setObs(result.getString("observacao"));
                allPed.add(p);
            }
            return allPed;
        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao listar pedidos.");
        }
    }

    @Override
    public int getLastPedidoId() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_LAST_QUERY)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("idPedido");
                } else {
                    throw new SQLException("Erro ao recuperar pedido: pedido não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar pedido.");
        }
    }

    @Override
    public int getCanceladosCount(int year) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CANCELADOS_YEAR_QUERY)) {

            statement.setInt(1, year);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("cnt");
                } else {
                    throw new SQLException("Erro ao recuperar contagem de pedidos cancelados.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar contagem de pedidos cancelados.");
        }
    }

    @Override
    public int getPagosCount(int year) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(PAGOS_YEAR_QUERY)) {

            statement.setInt(1, year);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("cnt");
                } else {
                    throw new SQLException("Erro ao recuperar contagem de pedidos pagos.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar contagem de pedidos pagos.");
        }
    }

    @Override
    public int getMaxYear() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(MAX_YEAR_QUERY)) {

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("max_year");
                } else {
                    throw new SQLException("Erro ao recuperar ano da tabela pedidos.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar ano da tabela pedidos.");
        }
    }

    @Override
    public int getMinYear() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(MIN_YEAR_QUERY)) {

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("min_year");
                } else {
                    throw new SQLException("Erro ao recuperar ano da tabela pedidos.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar ano da tabela pedidos.");
        }
    }

    @Override
    public int getMaxMonth(int year) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(MAX_MONTH_QUERY)) {

            statement.setInt(1, year);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("max_month");
                } else {
                    throw new SQLException("Erro ao recuperar mes da tabela pedidos.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar mes da tabela pedidos.");
        }
    }

    @Override
    public int getMonthYearPresCount(int month, int year) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(MONTH_YEAR_PRESENCIAL_QUERY)) {

            statement.setInt(1, month);
            statement.setInt(2, year);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("cnt");
                } else {
                    throw new SQLException("Erro ao recuperar contagem de pedidos do mes " + month + " ano " + year + ".");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar contagem de pedidos do mes " + month + " ano " + year + ".");
        }
    }

    @Override
    public int getMonthYearOnlineCount(int month, int year) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(MONTH_YEAR_ONLINE_QUERY)) {

            statement.setInt(1, month);
            statement.setInt(2, year);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("cnt");
                } else {
                    throw new SQLException("Erro ao recuperar contagem de pedidos do mes " + month + " ano " + year + ".");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar contagem de pedidos do mes " + month + " ano " + year + ".");
        }
    }

    public Pedido getRandom() throws SQLException {
        Pedido p = new Pedido();

        try (PreparedStatement statement = connection.prepareStatement(READ_RANDOM_QUERY)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    p.setId(result.getInt("idPedido"));
                    p.setComanda(result.getInt("comanda"));
                    p.setClienteLogin(result.getString("cliente_login"));
                    p.setFuncionarioLogin(result.getString("funcionario_login"));
                    p.setStatus(result.getString("status"));
                    p.setTipo(result.getString("tipo"));
                    p.setObs(result.getString("observacao"));
                    return p;
                } else {
                    throw new SQLException("Erro ao retornar pedido aleatório.");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao retornar pedido aleatório.");
        }
    }

    @Override
    public int getPedidosByFuncionario(Funcionario f, int month, int year) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(PEDIDOS_BY_FUNCIONARIO_QUERY)) {

            statement.setString(1, f.getLogin());
            statement.setInt(2, month);
            statement.setInt(3, year);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                   return result.getInt("pedidos_atendidos");
                }
                else {
                    throw new SQLException("Erro ao recuperar contagem de pedidos para funcionario " + f.getPNome());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar contagem de pedidos por funcionario.");
        }
    }
    
    @Override
    public List<Pedido> getMonthYearPedidos(int month, int year) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(MONTH_YEAR_PEDIDOS_QUERY)) {

            statement.setInt(1, month);
            statement.setInt(2, year);
            
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Pedido p = new Pedido();
                p.setId(result.getInt("idPedido"));
                p.setComanda(result.getInt("comanda"));
                p.setClienteLogin(result.getString("cliente_login"));
                p.setFuncionarioLogin(result.getString("funcionario_login"));
                p.setStatus(result.getString("status"));
                p.setTipo(result.getString("tipo"));
                p.setObs(result.getString("observacao"));
                pedidos.add(p);
            }
            return pedidos;
        } catch (SQLException ex) {
            Logger.getLogger(MyProdutoDAO.class
                    .getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao recuperar pedidos.");
        }
    }
}
