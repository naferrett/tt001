package model.dao;

import model.Cliente;
import model.Pessoa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO extends PessoaDAO {
    private static ClienteDAO instance;

    private ClienteDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ClienteDAO getInstance() {
        return (instance==null?(instance = new ClienteDAO()):instance);
    }

    // CRUD
    public Cliente create(String nome, String endereco, String telefone, String email, String cpf) {
        Pessoa pessoa = super.create(nome, endereco, telefone);
        
        if (pessoa == null) {
        // Trate a falha, por exemplo, lançando uma exceção ou retornando null
        Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, "Falha ao criar Pessoa");
        return null; // Ou lance uma exceção, se preferir
        }
        
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO cliente (codigo, email, cpf) VALUES (?,?,?)");
            stmt.setInt(1, pessoa.getCodigo());
            stmt.setString(2, email);
            stmt.setString(3, cpf);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(pessoa.getCodigo());
    }

    private Cliente buildObject(ResultSet rs) {
        Cliente cliente = null;
        try {
            cliente = new Cliente();
            cliente.setCodigo(rs.getInt("codigo"));
            cliente.setNome(rs.getString("nome"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEmail(rs.getString("email"));
            cliente.setCpf(rs.getString("cpf"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return cliente;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Cliente> clientes = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                clientes.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return clientes;
    }

    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM pessoa INNER JOIN cliente ON pessoa.codigo = cliente.codigo");
    }

    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM pessoa INNER JOIN cliente ON pessoa.codigo = cliente.codigo WHERE pessoa.codigo = " + lastId("pessoa","codigo"));
    }

    // RetrieveById
    public Cliente retrieveById(int id) {
        List<Cliente> clientes = this.retrieve("SELECT * FROM pessoa INNER JOIN cliente ON pessoa.codigo = cliente.codigo WHERE pessoa.codigo = " + id);
        return (clientes.isEmpty()?null:clientes.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM pessoa INNER JOIN cliente ON pessoa.codigo = cliente.codigo WHERE pessoa.nome LIKE '%" + nome + "%'");
    }

    // Update
    public void update(Cliente cliente) {
        super.update(cliente);
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE cliente SET email=?, cpf=? WHERE codigo=?");
            stmt.setString(1, cliente.getEmail());
            stmt.setString(2, cliente.getCpf());
            stmt.setInt(3, cliente.getCodigo());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Cliente cliente) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM cliente WHERE codigo = ?");
            stmt.setInt(1, cliente.getCodigo());
            executeUpdate(stmt);
            super.delete(cliente);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}