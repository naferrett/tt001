package model.dao;

import model.Pagamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoDAO extends DAO {
    private static PagamentoDAO instance;

    private PagamentoDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static PagamentoDAO getInstance() {
        return (instance==null?(instance = new PagamentoDAO()):instance);
    }

    // CRUD
    public Pagamento create(double valor, double valorPago, boolean consultaPaga, String dataPagamento, int consultaId) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO pagamento (valor, valor_pago, consulta_paga, data_pagamento, consulta_id) VALUES (?,?,?,?,?)");
            stmt.setDouble(1, valor);
            stmt.setDouble(2, valorPago);
            stmt.setBoolean(3, consultaPaga);
            stmt.setString(4, dataPagamento);
            stmt.setInt(5, consultaId);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(PagamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("pagamento","codigo"));
    }

    private Pagamento buildObject(ResultSet rs) {
        Pagamento pagamento = null;
        try {
            pagamento = new Pagamento();
            pagamento.setCodigo(rs.getInt("codigo"));
            pagamento.setValor(rs.getDouble("valor"));
            pagamento.setValorPago(rs.getDouble("valor_pago"));
            pagamento.setConsultaPaga(rs.getBoolean("consulta_paga"));
            pagamento.setDataPagamento(rs.getString("data_pagamento"));
            pagamento.setConsulta_id(rs.getInt("consulta_id"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return pagamento;
    }

    // Generic Retriever
    public List<Pagamento> retrieve(String query) {
        List<Pagamento> pagamentos = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                pagamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return pagamentos;
    }

    // RetrieveById
    public Pagamento retrieveById(int id) {
        List<Pagamento> pagamentos = this.retrieve("SELECT * FROM pagamento WHERE codigo = " + id);
        return (pagamentos.isEmpty()?null:pagamentos.get(0));
    }
    
    // RetrieveByConsulta
    public List<Pagamento> retrieveByConsulta(int consulta_id){
        return this.retrieve("SELECT * FROM pagamento WHERE consulta_id = " + consulta_id);
    }

    // Update
    public void update(Pagamento pagamento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE pagamento SET valor=?, valor_pago=?, consulta_paga=?, data_pagamento=?, consulta_id=? WHERE codigo=?");
            stmt.setDouble(1, pagamento.getValor());
            stmt.setDouble(2, pagamento.getValorPago());
            stmt.setBoolean(3, pagamento.isConsultaPaga());
            stmt.setString(4, pagamento.getDataPagamento());
            stmt.setInt(5, pagamento.getConsulta_id());
            stmt.setInt(6, pagamento.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Pagamento pagamento) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM pagamento WHERE codigo = ?");
            stmt.setInt(1, pagamento.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}