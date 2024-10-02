package model.dao;

import model.Exame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

public class ExameDAO extends DAO {
    private static ExameDAO instance;

    private ExameDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ExameDAO getInstance() {
        return (instance==null?(instance = new ExameDAO()):instance);
    }

    // CRUD
    public Exame create(String tipo, Date dataSolicitacao, String status, int consultaId) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO exame (tipo, data_solicitacao, status, consulta_id) VALUES (?,?,?,?)");
            stmt.setString(1, tipo);
            stmt.setDate(2, dataSolicitacao);
            stmt.setString(3, status);
            stmt.setInt(4, consultaId);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("exame","codigo"));
    }

    private Exame buildObject(ResultSet rs) {
        Exame exame = null;
        try {
            exame = new Exame();
            exame.setCodigo(rs.getInt("codigo"));
            exame.setTipo(rs.getString("tipo"));
            exame.setDataSolicitacao(rs.getDate("data_solicitacao"));
            exame.setStatus(rs.getString("status"));
            exame.setConsulta_id(rs.getInt("consulta_id"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exame;
    }

    // Generic Retriever
    public List<Exame> retrieve(String query) {
        List<Exame> exames = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                exames.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exames;
    }

    // RetrieveAll
    public List<Exame> retrieveAll() {
        return this.retrieve("SELECT * FROM exame");
    }

    // RetrieveLast
    public List<Exame> retrieveLast(){
        return this.retrieve("SELECT * FROM exame WHERE codigo = " + lastId("exame","codigo"));
    }

    // RetrieveById
    public Exame retrieveById(int id) {
        List<Exame> exames = this.retrieve("SELECT * FROM exame WHERE codigo = " + id);
        return (exames.isEmpty()?null:exames.get(0));
    }
    
    // RetrieveByConsulta
    public List<Exame> retrieveByConsulta(int idConsulta){
        return this.retrieve("SELECT * FROM exame WHERE consulta_id = " + idConsulta);
    }

    // Update
    public void update(Exame exame) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE exame SET tipo=?, data_solicitacao=?, status=?, consulta_id=? WHERE codigo=?");
            stmt.setString(1, exame.getTipo());
            stmt.setDate(2, exame.getDataSolicitacao());
            stmt.setString(3, exame.getStatus());
            stmt.setInt(4, exame.getConsulta_id());
            stmt.setInt(5, exame.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Exame exame) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM exame WHERE codigo = ?");
            stmt.setInt(1, exame.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}