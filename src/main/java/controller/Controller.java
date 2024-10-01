package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Animal;
import model.ClasseAnimal;
import model.Cliente;
import model.dao.AnimalDAO;
import model.dao.ClasseAnimalDAO;
import model.dao.ClienteDAO;
import view.AnimalTableModel;
import view.ClienteTableModel;
import view.GenericTableModel;

public class Controller {
    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    private static final JTextField classeAnimalSelecionadaTextField = null;
    private static final JTextField especieAnimalSelecionadaTextField = null;
    
    public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }
    
    public static void setTextFields(JTextField cliente, JTextField animal) {
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
        //classeAnimalSelecionadaTextField = animalSelecionado.getClasse_animal();
        //especieAnimalSelecionadaTextField = animalSelecionado.getEspecie();
      }
    
    public static void setSelecionado(Object selecionado) {
        if (selecionado instanceof Cliente) {
            clienteSelecionado = (Cliente) selecionado;
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
            animalSelecionadoTextField.setText("");
        } else if (selecionado instanceof Animal) {
            animalSelecionado = (Animal) selecionado;
            animalSelecionadoTextField.setText(animalSelecionado.getNome());            
            classeAnimalSelecionadaTextField.setText((ClasseAnimalDAO.getInstance().retrieveById(animalSelecionado.getClasse_animal())).getNomeClasse());
            especieAnimalSelecionadaTextField.setText(animalSelecionado.getEspecie());
        }
    }
    
    public static void jTableMostraClientes(JTable table) {
        setTableModel(table, new ClienteTableModel(ClienteDAO.getInstance().retrieveAll()));
    }
    
    public static boolean jTableMostraAnimais(JTable table) {
        if(Controller.getClienteSelecionado() != null) {
            setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveByCliente(Controller.getClienteSelecionado().getCodigo())));
            return true;
        } else {
            setTableModel(table, new AnimalTableModel(new ArrayList()));
            return false;
        }
    }
    
    public static List<Object> buscarCliente(String nome) {
        return ClienteDAO.getInstance().retrieveBySimilarName(nome);
    }

    public static void atualizarBuscaCliente(JTable table, String nome) {
        ((GenericTableModel)table.getModel()).addListOfItems(Controller.buscarCliente(nome));
    }
    
    // Adiciona ao banco
    public static Cliente adicionarCliente(String nome, String endereco, String telefone, String email, String cpf) {
        return ClienteDAO.getInstance().create(nome, endereco, telefone, email, cpf);
    }
    
    // Recupera todos os clientes
    public static List<Object> mostrarTodosClientes() {
        return ClienteDAO.getInstance().retrieveAll();
    }
    
    // Apaga cliente do banco
    public static void deletarCliente(Cliente cliente) {
        List<Animal> animais = AnimalDAO.getInstance().retrieveByCliente(cliente.getCodigo()); 
        
        for(Animal animal : animais) {
           AnimalDAO.getInstance().delete(animal);
        }
        
        ClienteDAO.getInstance().delete(cliente);
    }
    
    public static Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }
    
    public static Animal getAnimalSelecionado() {
        return animalSelecionado;
    }
    
}
