package model.dao;

import model.ClasseAnimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClasseAnimalDAO extends DAO {
    private static ClasseAnimalDAO instance;

    private ClasseAnimalDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ClasseAnimalDAO getInstance() {
        return (instance==null?(instance = new ClasseAnimalDAO()):instance);
    }

    // CRUD
    public ClasseAnimal create(String nomeClasse) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO classe_animal (nome_classe) VALUES (?)");
            stmt.setString(1, nomeClasse);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 23505) {
                System.err.println("Erro: O nome da classe '" + nomeClasse + "' já existe.");
            } else {
                Logger.getLogger(ClasseAnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.retrieveById(lastId("classe_animal","codigo"));
    }

    private ClasseAnimal buildObject(ResultSet rs) {
        ClasseAnimal classeAnimal = null;
        try {
            classeAnimal = new ClasseAnimal();
            classeAnimal.setCodigo(rs.getInt("codigo"));
            classeAnimal.setNomeClasse(rs.getString("nome_classe"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return classeAnimal;
    }

    // Generic Retriever
    public List<ClasseAnimal> retrieve(String query) {
        List<ClasseAnimal> classeAnimais = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                classeAnimais.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return classeAnimais;
    }

    // RetrieveAll
    public List<ClasseAnimal> retrieveAll() {
        return this.retrieve("SELECT * FROM classe_animal");
    }

    // RetrieveLast
    public List<ClasseAnimal> retrieveLast(){
        return this.retrieve("SELECT * FROM classe_animal WHERE codigo = " + lastId("classe_animal","codigo"));
    }

    // RetrieveById
    public ClasseAnimal retrieveById(int id) {
        List<ClasseAnimal> classeAnimais = this.retrieve("SELECT * FROM classe_animal WHERE codigo = " + id);
        return (classeAnimais.isEmpty()?null:classeAnimais.get(0));
    }
    
    // RetrieveByName
    public ClasseAnimal retrieveByName(String nomeClasse) {
        List<ClasseAnimal> classeAnimais = this.retrieve("SELECT * FROM classe_animal WHERE nome_classe = '" + nomeClasse + "'");
        return (classeAnimais.isEmpty()?null:classeAnimais.get(0));
    }

    // RetrieveBySimilarName
    public ClasseAnimal retrieveBySimilarName(String nomeClasse) {
        List<ClasseAnimal> classeAnimais = this.retrieve("SELECT * FROM classe_animal WHERE nome_classe LIKE '%" + nomeClasse + "%'");
        return (classeAnimais.isEmpty()?null:classeAnimais.get(0));
    }

    // Update
    public void update(ClasseAnimal classeAnimal) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE classe_animal SET nome_classe=? WHERE codigo=?");
            stmt.setString(1, classeAnimal.getNomeClasse());
            stmt.setInt(2, classeAnimal.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // DeleteAll
    public void deleteAllGreaterThanFive() {
        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement("DELETE FROM classe_animal WHERE codigo >= 1");
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }


    // Delete
    public void delete(ClasseAnimal classeAnimal) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM classe_animal WHERE codigo = ?");
            stmt.setInt(1, classeAnimal.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}