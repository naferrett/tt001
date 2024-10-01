package model.dao;

import model.Veterinario;
import model.Pessoa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeterinarioDAO extends PessoaDAO {
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
        Pessoa pessoa = super.create(nome, endereco, telefone);
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO veterinario (codigo, crmv, especialidade, hora_atendimento) VALUES (?,?,?,?)");
            stmt.setInt(1, pessoa.getCodigo());
            stmt.setString(2, crmv);
            stmt.setInt(3, especialidade);
            stmt.setString(4, horaAtendimento);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(pessoa.getCodigo());
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
    public List retrieve(String query) {
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
        return this.retrieve("SELECT * FROM pessoa INNER JOIN veterinario ON pessoa.codigo = veterinario.codigo");
    }

    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM pessoa INNER JOIN veterinario ON pessoa.codigo = veterinario.codigo WHERE pessoa.codigo = " + lastId("pessoa","codigo"));
    }

    // RetrieveById
    public Veterinario retrieveById(int id) {
        List<Veterinario> veterinarios = this.retrieve("SELECT * FROM pessoa INNER JOIN veterinario ON pessoa.codigo = veterinario.codigo WHERE pessoa.codigo = " + id);
        return (veterinarios.isEmpty()?null:veterinarios.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM pessoa INNER JOIN veterinario ON pessoa.codigo = veterinario.codigo WHERE pessoa.nome LIKE '%" + nome + "%'");
    }

    // Update
    public void update(Veterinario veterinario) {
        super.update(veterinario);
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE veterinario SET crmv=?, especialidade=?, hora_atendimento=? WHERE codigo=?");
            stmt.setString(1, veterinario.getCrmv());
            stmt.setInt(2, veterinario.getEspecialidade());
            stmt.setString(3, veterinario.getHoraAtendimento());
            stmt.setInt(4, veterinario.getCodigo());
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
            super.delete(veterinario);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}