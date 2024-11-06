package model.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    public static final String DBURL = "jdbc:h2:./ClinicaVeterinaria";
    private static Connection con;
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // Connect to SQLite
    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(DBURL);
                if (con != null) {
                    DatabaseMetaData meta = con.getMetaData();
                }
            } catch (SQLException e) {
                //System.err.println("Exception: " + e.getMessage());
                 e.printStackTrace();
            }
        }
        return con;
    }

    protected ResultSet getResultSet(String query) {
        Statement s;
        ResultSet rs = null;
        try {
            s = (Statement) con.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return rs;
    }

    protected int executeUpdate(PreparedStatement queryStatement) throws SQLException {
        return queryStatement.executeUpdate();
    }

    protected int lastId(String tableName, String primaryKey) {
        Statement s;
        int lastId = -1;
        try {
            s = (Statement) con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return lastId;
    }

    public static void terminar() {
        try {
            (DAO.getConnection()).close();
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    protected final boolean createTable() {
        try {
            PreparedStatement stmt;

//            // Table Pessoa:
//            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS pessoa( \n"
//                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
//                    + "nome VARCHAR(200), \n"
//                    + "endereco VARCHAR(200), \n"
//                    + "telefone VARCHAR(20)); \n");
//            executeUpdate(stmt);

            // Table ClasseAnimal:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS classe_animal( \n"
                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
                    + "nome_classe VARCHAR(100) UNIQUE); \n");
            executeUpdate(stmt);

            // Table Veterinario:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS veterinario( \n"
                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
                    + "nome VARCHAR(200), \n"
                    + "endereco VARCHAR(200), \n"
                    + "telefone VARCHAR(20), \n"
                    + "crmv VARCHAR(14), \n"
                    + "especialidade INT, \n"
                    + "hora_atendimento VARCHAR(50), \n"
                    + "FOREIGN KEY (especialidade) REFERENCES classe_animal(codigo)); \n");
            executeUpdate(stmt);

            // Table Cliente:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS cliente( \n"
                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
                    + "nome VARCHAR(200), \n"
                    + "endereco VARCHAR(200), \n"
                    + "telefone VARCHAR(20), \n"
                    + "email VARCHAR(200), \n"
                    + "cpf VARCHAR(20)); \n");
            executeUpdate(stmt);

            // Table Animal:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS animal( \n"
                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
                    + "nome VARCHAR(200), \n"
                    + "especie VARCHAR(100), \n"
                    + "raca VARCHAR(100), \n"
                    + "sexo CHAR(1), \n"
                    + "peso DECIMAL(5,2), \n"
                    + "cliente_id INT, \n"
                    + "classe_animal INT, \n"
                    + "FOREIGN KEY (cliente_id) REFERENCES cliente(codigo), \n"
                    + "FOREIGN KEY (classe_animal) REFERENCES classe_animal(codigo)); \n");
            executeUpdate(stmt);

            // Table Tratamento:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tratamento( \n"
                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
                    + "data_inicio VARCHAR(20), \n"
                    + "data_fim VARCHAR(20), \n"
                    + "descricao TEXT, \n"
                    + "animal_tratado INT, \n"
                    + "FOREIGN KEY (animal_tratado) REFERENCES animal(codigo)); \n");
            executeUpdate(stmt);

            // Table Consulta:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS consulta( \n"
                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
                    + "data VARCHAR(20), \n"
                    + "periodo VARCHAR(20), \n"
                    + "veterinario_id INT, \n"
                    + "nome_vet VARCHAR(50), \n"
                    + "status VARCHAR(50), \n"
                    + "motivo VARCHAR(300), \n"
                    + "observacoes TEXT, \n"
                    + "resultados TEXT, \n"
                    + "tratamento_id INT, \n"
                    + "FOREIGN KEY (veterinario_id) REFERENCES veterinario(codigo) ON DELETE SET NULL, \n"
                    + "FOREIGN KEY (tratamento_id) REFERENCES tratamento(codigo)); \n");
            executeUpdate(stmt);

            // Table Pagamento:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS pagamento( \n"
                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
                    + "valor DECIMAL(10,2), \n"
                    + "valor_pago DECIMAL(10,2), \n"
                    + "consulta_paga BOOLEAN, \n"
                    + "data_pagamento VARCHAR(20), \n"
                    + "consulta_id INT, \n"
                    + "FOREIGN KEY (consulta_id) REFERENCES consulta(codigo)); \n");
            executeUpdate(stmt);

            // Table Exame:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS exame( \n"
                    + "codigo INT PRIMARY KEY AUTO_INCREMENT, \n"
                    + "tipo VARCHAR(100), \n"
                    + "data_solicitacao VARCHAR(20), \n"
                    + "status VARCHAR(50), \n"
                    + "consulta_id INT, \n"
                    + "FOREIGN KEY (consulta_id) REFERENCES consulta(codigo)); \n");
            executeUpdate(stmt);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
