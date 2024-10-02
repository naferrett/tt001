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
    public Consulta create(Timestamp data, int veterinarioId, String status, String motivo, String observacoes, int tratamentoId) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (data, veterinario_id, status, motivo, observacoes, tratamento_id) VALUES (?,?,?,?,?,?)");
            stmt.setTimestamp(1, data);
            stmt.setInt(2, veterinarioId);
            stmt.setString(3, status);
            stmt.setString(4, motivo);
            stmt.setString(5, observacoes);
            stmt.setInt(6, tratamentoId);
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
            consulta.setData(rs.getTimestamp("data"));
            consulta.setVeterinario_id(rs.getInt("veterinario_id"));
            consulta.setStatus(rs.getString("status"));
            consulta.setMotivo(rs.getString("motivo"));
            consulta.setObservacoes(rs.getString("observacoes"));
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

    // RetrieveAll
    public List<Consulta> retrieveAll() {
        return this.retrieve("SELECT * FROM consulta");
    }

    // RetrieveLast
    public List<Consulta> retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE codigo = " + lastId("consulta","codigo"));
    }

    // RetrieveById
    public Consulta retrieveById(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE codigo = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }
    
    // RetrieveByTratamento
    public List<Consulta> retrievebyTratamento(int idTratamento) {
        return this.retrieve("SELECT * FROM consulta WHERE tratamento_id = " + idTratamento);
    }

    // Update
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consulta SET data=?, veterinario_id=?, status=?, motivo=?, observacoes=?, tratamento_id=? WHERE codigo=?");
            stmt.setTimestamp(1, consulta.getData());
            stmt.setInt(2, consulta.getVeterinario_id());
            stmt.setString(3, consulta.getStatus());
            stmt.setString(4, consulta.getMotivo());
            stmt.setString(5, consulta.getObservacoes());
            stmt.setInt(6, consulta.getTratamento_id());
            stmt.setInt(7, consulta.getCodigo());
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