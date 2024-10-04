//package model.dao;
//
//import model.Pessoa;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class PessoaDAO extends DAO {
//    private static PessoaDAO instance;
//
//    protected PessoaDAO() {
//        getConnection();
//        createTable();
//    }
//
//    // Singleton
//    public static PessoaDAO getInstance() {
//        return (instance==null?(instance = new PessoaDAO()):instance);
//    }
//
//    // CRUD
//    public Pessoa create(String nome, String endereco, String telefone) {
//        try {
//            PreparedStatement stmt;
//            stmt = DAO.getConnection().prepareStatement("INSERT INTO pessoa (nome, endereco, telefone) VALUES (?,?,?)");
//
//            stmt.setString(1, nome);
//            stmt.setString(2, endereco);
//            stmt.setString(3, telefone);
//            //executeUpdate(stmt);
//            int affectedRows = stmt.executeUpdate();
//            if (affectedRows > 0) {
//            Logger.getLogger(PessoaDAO.class.getName()).log(Level.INFO, "Pessoa criada com sucesso");
//        } else {
//            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, "Nenhuma linha afetada. Falha ao criar Pessoa.");
//        }
//        } catch (SQLException ex) {
//            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//        return this.retrieveById(lastId("pessoa","codigo"));
//    }
//
//    private Pessoa buildObject(ResultSet rs) {
//        Pessoa pessoa = null;
//        try {
//            pessoa = new Pessoa();
//            pessoa.setCodigo(rs.getInt("codigo"));
//            pessoa.setNome(rs.getString("nome"));
//            pessoa.setEndereco(rs.getString("endereco"));
//            pessoa.setTelefone(rs.getString("telefone"));
//        } catch (SQLException e) {
//            System.err.println("Exception: " + e.getMessage());
//        }
//        return pessoa;
//    }
//
//    // Generic Retriever
//    public List<Pessoa> retrieve(String query) {
//        List<Pessoa> pessoas = new ArrayList<>();
//        ResultSet rs = getResultSet(query);
//        try {
//            while (rs.next()) {
//                pessoas.add(buildObject(rs));
//            }
//        } catch (SQLException e) {
//            System.err.println("Exception: " + e.getMessage());
//        }
//        return pessoas;
//    }
//
//    // RetrieveAll
//    public List<Pessoa> retrieveAll() {
//        return this.retrieve("SELECT * FROM pessoa");
//    }
//
//    // RetrieveLast
//    public List<Pessoa> retrieveLast(){
//        return this.retrieve("SELECT * FROM pessoa WHERE codigo = " + lastId("pessoa","codigo"));
//    }
//
//    // RetrieveById
//    public Pessoa retrieveById(int id) {
//        List<Pessoa> pessoas = this.retrieve("SELECT * FROM pessoa WHERE codigo = " + id);
//        return (pessoas.isEmpty()?null:pessoas.get(0));
//    }
//
//    // RetrieveBySimilarName
//    public List<Pessoa> retrieveBySimilarName(String nome) {
//        return this.retrieve("SELECT * FROM pessoa WHERE nome LIKE '%" + nome + "%'");
//    }
//
//    // Update
//    public void update(Pessoa pessoa) {
//        try {
//            PreparedStatement stmt;
//            stmt = DAO.getConnection().prepareStatement("UPDATE pessoa SET nome=?, endereco=?, telefone=? WHERE codigo=?");
//            stmt.setString(1, pessoa.getNome());
//            stmt.setString(2, pessoa.getEndereco());
//            stmt.setString(3, pessoa.getTelefone());
//            stmt.setInt(4, pessoa.getCodigo());
//            executeUpdate(stmt);
//        } catch (SQLException e) {
//            System.err.println("Exception: " + e.getMessage());
//        }
//    }
//
//    // Delete
//    public void delete(Pessoa pessoa) {
//        PreparedStatement stmt;
//        try {
//            stmt = DAO.getConnection().prepareStatement("DELETE FROM pessoa WHERE codigo = ?");
//            stmt.setInt(1, pessoa.getCodigo());
//            executeUpdate(stmt);
//        } catch (SQLException e) {
//            System.err.println("Exception: " + e.getMessage());
//        }
//    }
//}