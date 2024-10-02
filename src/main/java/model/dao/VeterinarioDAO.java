package model.dao;

import model.Animal;
import model.Veterinario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeterinarioDAO extends DAO {
    private static VeterinarioDAO instance;

    private VeterinarioDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static VeterinarioDAO getInstance() {
        return (instance==null?(instance = new VeterinarioDAO()):instance);
    }

    // CRUD
    public Veterinario create(String nome, String endereco, String telefone, String crmv, int especialidade, String horaAtendimento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO veterinario (nome, endereco, telefone, crmv, especialidade, hora_atendimento) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, telefone);
            stmt.setString(4, crmv);
            stmt.setInt(5, especialidade);
            stmt.setString(6, horaAtendimento);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("veterinario","codigo"));
    }

    private Veterinario buildObject(ResultSet rs) {
        Veterinario veterinario = null;
        try {
            veterinario = new Veterinario();
            veterinario.setCodigo(rs.getInt("codigo"));
            veterinario.setNome(rs.getString("nome"));
            veterinario.setEndereco(rs.getString("endereco"));
            veterinario.setTelefone(rs.getString("telefone"));
            veterinario.setCrmv(rs.getString("crmv"));
            veterinario.setEspecialidade(rs.getInt("especialidade"));
            veterinario.setHoraAtendimento(rs.getString("hora_atendimento"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinario;
    }

    // Generic Retriever
    public List<Veterinario> retrieve(String query) {
        List<Veterinario> veterinarios = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                veterinarios.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinarios;
    }

    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM veterinario");
    }

    // RetrieveLast
    public List<Veterinario> retrieveLast(){
        return this.retrieve("SELECT * FROM veterinario WHERE codigo = " + lastId("veterinario","codigo"));
    }

    // RetrieveById
    public Veterinario retrieveById(int id) {
        List<Veterinario> veterinarios = this.retrieve("SELECT * FROM veterinario WHERE codigo = " + id);
        return (veterinarios.isEmpty()?null:veterinarios.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM veterinario WHERE nome LIKE '%" + nome + "%'");
    }

    // RetrieveByEspecialidade
    public List retrieveByEspecialidade(int especialidade) {
        return this.retrieve("SELECT * FROM veterinario WHERE especialidade = " + especialidade);
    }
    
    // Update
    public void update(Veterinario veterinario) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE veterinario SET nome=?, endereco=?, telefone=?, crmv=?, especialidade=?, hora_atendimento=? WHERE codigo=?");
            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getEndereco());
            stmt.setString(3, veterinario.getTelefone());
            stmt.setString(4, veterinario.getCrmv());
            stmt.setInt(5, veterinario.getEspecialidade());
            stmt.setString(6, veterinario.getHoraAtendimento());
            stmt.setInt(7, veterinario.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Veterinario veterinario) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM veterinario WHERE codigo = ?");
            stmt.setInt(1, veterinario.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}