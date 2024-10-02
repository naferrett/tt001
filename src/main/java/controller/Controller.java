package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Animal;
import model.ClasseAnimal;
import model.Cliente;
import model.Veterinario;
import model.dao.AnimalDAO;
import model.dao.ClasseAnimalDAO;
import model.dao.ClienteDAO;
import model.dao.VeterinarioDAO;
import view.AnimalTableModel;
import view.ClienteTableModel;
import view.GenericTableModel;
import view.VeterinarioTableModel;

public class Controller {
    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static Veterinario vetSelecionado = null;
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
        } else if (selecionado instanceof Veterinario) {
            vetSelecionado = (Veterinario) selecionado;
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
    
    public static void jTableMostraVets(JTable table) {
        setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveAll()));
    }
    
    // Busca cliente no banco 
    public static List<Object> buscarCliente(String nome) {
        return ClienteDAO.getInstance().retrieveBySimilarName(nome);
    }

    // Atualiza a tablea com os clientes encontrados no banco
    public static void atualizarBuscaCliente(JTable table, String nome) {
        ((GenericTableModel)table.getModel()).addListOfItems(Controller.buscarCliente(nome));
    }
    
    // Adiciona cliente ao banco
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
    
    // Adiciona animal ao banco relacionado ao cliente selecionado
    public static Animal adicionarAnimal(String nome, String classeAnimal, String especie, String raca, char sexo, Double peso) {
        ClasseAnimal classe = ClasseAnimalDAO.getInstance().retrieveByName(classeAnimal);
        int cliente_id = getClienteSelecionado().getCodigo();
        return AnimalDAO.getInstance().create(nome, especie, raca, sexo, peso, cliente_id, classe.getCodigo());
    }
    
    public static void deletarAnimal(Animal animal) {
        //List<Animal> animais = AnimalDAO.getInstance().retrieveByCliente(cliente.getCodigo()); 
        AnimalDAO.getInstance().delete(animal);
    }
    
    // Busca veterinário no banco 
    public static List<Object> buscarVeterinario(String nome) {
        return VeterinarioDAO.getInstance().retrieveBySimilarName(nome);
    }

    // Atualiza a tabela com os veterinário encontrados no banco
    public static void atualizarBuscaVeterinario(JTable table, String nome) {
        ((GenericTableModel)table.getModel()).addListOfItems(Controller.buscarVeterinario(nome));
    }
    
    // Adiciona veterinário ao banco
    public static Veterinario adicionarVeterinario(String nome, String endereco, String telefone, String especialidade, String periodo, String crmv) {
        ClasseAnimal especialidadeVet = ClasseAnimalDAO.getInstance().retrieveByName(especialidade);
        return VeterinarioDAO.getInstance().create(nome, endereco, telefone, crmv, especialidadeVet.getCodigo(), periodo);
    }
    
    // Recupera todos os clientes
    public static List<Object> mostrarTodosVets() {
        return VeterinarioDAO.getInstance().retrieveAll();
    }
    
    // Apaga cliente do banco. Precisa excluir mais entidades junto?
    public static void deletarVet(Veterinario veterinario) {
        VeterinarioDAO.getInstance().delete(veterinario);
    }
    
    public static Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }
    
    public static Animal getAnimalSelecionado() {
        return animalSelecionado;
    }
    
    public static Veterinario getVeterinarioSelecionado() {
        return vetSelecionado;
    }
    
}
