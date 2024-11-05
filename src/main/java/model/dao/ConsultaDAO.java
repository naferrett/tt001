package model.dao;

import model.Consulta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;

public class ConsultaDAO extends DAO {
    private static ConsultaDAO instance;

    private ConsultaDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ConsultaDAO getInstance() {
        return (instance==null?(instance = new ConsultaDAO()):instance);
    }

    // CRUD
    public Consulta create(String data, String periodo, int veterinarioId, String status, String motivo, String observacoes, String resultados, int tratamentoId) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (data, periodo, veterinario_id, status, motivo, observacoes, resultados, tratamento_id) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setString(1, data);
            stmt.setString(2, periodo);
            stmt.setInt(3, veterinarioId);
            stmt.setString(4, status);
            stmt.setString(5, motivo);
            stmt.setString(6, observacoes);
            stmt.setString(7, resultados);
            stmt.setInt(8, tratamentoId);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("consulta","codigo"));
    }

    private Consulta buildObject(ResultSet rs) {
        Consulta consulta = null;
        try {
            consulta = new Consulta();
            consulta.setCodigo(rs.getInt("codigo"));
            consulta.setData(rs.getString("data"));
            consulta.setPeriodo(rs.getString("periodo"));
            consulta.setVeterinario_id(rs.getInt("veterinario_id"));
            consulta.setStatus(rs.getString("status"));
            consulta.setMotivo(rs.getString("motivo"));
            consulta.setObservacoes(rs.getString("observacoes"));
            consulta.setResultados(rs.getString("resultados"));
            consulta.setTratamento_id(rs.getInt("tratamento_id"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consulta;
    }

    // Generic Retriever
    public List<Consulta> retrieve(String query) {
        List<Consulta> consultas = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }

    // RetrieveById
    public Consulta retrieveById(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE codigo = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }
    
    // RetrieveByTratamento
    public List<Consulta> retrievebyTratamento(int tratamento_id) {
        return this.retrieve("SELECT * FROM consulta WHERE tratamento_id = " + tratamento_id);
    }
    
    // RetrieveByVeterinario
    public List<Consulta> retrievebyVeterinario(int veterinario_id) {
        return this.retrieve("SELECT * FROM consulta WHERE veterinario_id = " + veterinario_id);
    }

    public List retrieveAll() {
        return this.retrieve("SELECT * FROM consulta");
    }

    public List retrievebyData(String data) {
        return this.retrieve("SELECT * FROM consulta WHERE data LIKE '%" + data + "%'");
    }

    // Update
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consulta SET data=?, periodo=?, veterinario_id=?, status=?, motivo=?, observacoes=?, resultados=?, tratamento_id=? WHERE codigo=?");
            stmt.setString(1, consulta.getData());
            stmt.setString(2, consulta.getPeriodo());
            stmt.setInt(3, consulta.getVeterinario_id());
            stmt.setString(4, consulta.getStatus());
            stmt.setString(5, consulta.getMotivo());
            stmt.setString(6, consulta.getObservacoes());
            stmt.setString(7, consulta.getResultados());
            stmt.setInt(8, consulta.getTratamento_id());
            stmt.setInt(9, consulta.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE codigo = ?");
            stmt.setInt(1, consulta.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}