package model.dao;

import model.Tratamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

public class TratamentoDAO extends DAO {
    private static TratamentoDAO instance;

    private TratamentoDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static TratamentoDAO getInstance() {
        return (instance==null?(instance = new TratamentoDAO()):instance);
    }

    // CRUD
    public Tratamento create(String dataInicio, String dataFim, String descricao, int animalTratado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO tratamento (data_inicio, data_fim, descricao, animal_tratado) VALUES (?,?,?,?)");
            stmt.setString(1, dataInicio);
            stmt.setString(2, dataFim);
            stmt.setString(3, descricao);
            stmt.setInt(4, animalTratado);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("tratamento","codigo"));
    }

    private Tratamento buildObject(ResultSet rs) {
        Tratamento tratamento = null;
        try {
            tratamento = new Tratamento();
            tratamento.setCodigo(rs.getInt("codigo"));
            tratamento.setDataInicio(rs.getString("data_inicio"));
            tratamento.setDataFim(rs.getString("data_fim"));
            tratamento.setDescricao(rs.getString("descricao"));
            tratamento.setAnimalTratado(rs.getInt("animal_tratado"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamento;
    }

    // Generic Retriever
    public List<Tratamento> retrieve(String query) {
        List<Tratamento> tratamentos = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tratamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamentos;
    }

    // RetrieveById
    public Tratamento retrieveById(int id) {
        List<Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamento WHERE codigo = " + id);
        return (tratamentos.isEmpty()?null:tratamentos.get(0));
    }
    
    // RetrieveByAnimal
    public List<Tratamento> retrieveByAnimal(int animalTratado) {
        return this.retrieve("SELECT * FROM tratamento WHERE animal_tratado = " + animalTratado);
    }

    public List retrieveAll() {
        return this.retrieve("SELECT * FROM tratamento");
    }
    
    // Update
    public void update(Tratamento tratamento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE tratamento SET data_inicio=?, data_fim=?, descricao=?, animal_tratado=? WHERE codigo=?");
            stmt.setString(1, tratamento.getDataInicio());
            stmt.setString(2, tratamento.getDataFim());
            stmt.setString(3, tratamento.getDescricao());
            stmt.setInt(4, tratamento.getAnimalTratado());
            stmt.setInt(5, tratamento.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Tratamento tratamento) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE codigo = ?");
            stmt.setInt(1, tratamento.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}