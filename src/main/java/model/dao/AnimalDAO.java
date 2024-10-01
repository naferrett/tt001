package model.dao;

import model.Animal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalDAO extends DAO {
    private static AnimalDAO instance;

    private AnimalDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static AnimalDAO getInstance() {
        return (instance==null?(instance = new AnimalDAO()):instance);
    }

    // CRUD
    public Animal create(String nome, String especie, String raca, char sexo, double peso, int cliente_id, int classeAnimal) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO animal (nome, especie, raca, sexo, peso, cliente_id, classe_animal) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, especie);
            stmt.setString(3, raca);
            stmt.setString(4, String.valueOf(sexo));
            stmt.setDouble(5, peso);
            stmt.setInt(6, cliente_id);
            stmt.setInt(7, classeAnimal);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("animal","codigo"));
    }

    private Animal buildObject(ResultSet rs) {
        Animal animal = null;
        try {
            animal = new Animal();
            animal.setCodigo(rs.getInt("codigo"));
            animal.setNome(rs.getString("nome"));
            animal.setEspecie(rs.getString("especie"));
            animal.setRaca(rs.getString("raca"));
            animal.setSexo(rs.getString("sexo").charAt(0));
            animal.setPeso(rs.getDouble("peso"));
            animal.setCliente_id(rs.getInt("cliente_id"));
            animal.setClasse_animal(rs.getInt("classe_animal"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return animal;
    }

    // Generic Retriever
    public List<Animal> retrieve(String query) {
        List<Animal> animais = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                animais.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return animais;
    }

    // RetrieveAll
    public List<Animal> retrieveAll() {
        return this.retrieve("SELECT * FROM animal");
    }

    // RetrieveLast
    public List<Animal> retrieveLast(){
        return this.retrieve("SELECT * FROM animal WHERE codigo = " + lastId("animal","codigo"));
    }

    // RetrieveById
    public Animal retrieveById(int id) {
        List<Animal> animais = this.retrieve("SELECT * FROM animal WHERE codigo = " + id);
        return (animais.isEmpty()?null:animais.get(0));
    }

    // RetrieveBySimilarName
    public List<Animal> retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM animal WHERE nome LIKE '%" + nome + "%'");
    }

    // Update
    public void update(Animal animal) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE animal SET nome=?, especie=?, raca=?, sexo=?, peso=?, cliente_id=?, classe_animal=? WHERE codigo=?");
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setString(4, String.valueOf(animal.getSexo()));
            stmt.setDouble(5, animal.getPeso());
            stmt.setInt(6, animal.getCliente_id());
            stmt.setInt(7, animal.getClasse_animal());
            stmt.setInt(8, animal.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Animal animal) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM animal WHERE codigo = ?");
            stmt.setInt(1, animal.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // RetrieveByCliente
    public List<Animal> retrieveByCliente(int clienteId) {
        return this.retrieve("SELECT * FROM animal WHERE cliente_id = " + clienteId);
    }

}