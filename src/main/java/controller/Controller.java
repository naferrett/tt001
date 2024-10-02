package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Animal;
import model.ClasseAnimal;
import model.Cliente;
import model.Consulta;
import model.Exame;
import model.Pagamento;
import model.Tratamento;
import model.Veterinario;
import model.dao.AnimalDAO;
import model.dao.ClasseAnimalDAO;
import model.dao.ClienteDAO;
import model.dao.ConsultaDAO;
import model.dao.ExameDAO;
import model.dao.PagamentoDAO;
import model.dao.TratamentoDAO;
import model.dao.VeterinarioDAO;
import view.AnimalTableModel;
import view.ClienteTableModel;
import view.ConsultaTableModel;
import view.ExameTableModel;
import view.GenericTableModel;
import view.PagamentoTableModel;
import view.TratamentoTableModel;
import view.VeterinarioTableModel;

public class Controller {
    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static Veterinario vetSelecionado = null;
    private static Tratamento tratamentoSelecionado = null;
    private static Consulta consultaSelecionada = null;
    private static Exame exameSelecionado = null;
    private static Pagamento pagamentoSelecionado = null;
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    private static JTextField classeAnimalSelecionadaTextField = null;
    private static JTextField especieAnimalSelecionadaTextField = null;
    
    public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }
    
    public static void setTextFields(JTextField cliente, JTextField animal, JTextField classe, JTextField especie) {
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
        classeAnimalSelecionadaTextField = classe;
        especieAnimalSelecionadaTextField = especie;
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
        } else if (selecionado instanceof Tratamento) {
            tratamentoSelecionado = (Tratamento) selecionado;
        } else if (selecionado instanceof Consulta) {
            consultaSelecionada = (Consulta) selecionado;
        } else if (selecionado instanceof Exame) {
            exameSelecionado = (Exame) selecionado;
        } else if (selecionado instanceof Pagamento) {
            pagamentoSelecionado = (Pagamento) selecionado;
        }
    }
    
    // Exibição das tabelas na interface
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
    
    public static boolean jTableMostraVets(JTable table) {
        if(Controller.getAnimalSelecionado() != null) {
            setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveByEspecialidade(Controller.getAnimalSelecionado().getClasse_animal())));
            return true;
        } else {
            setTableModel(table, new VeterinarioTableModel(new ArrayList()));
            return false;
        }
    }
    
    public static boolean jTableMostraTratamentos(JTable table) {
        if(Controller.getAnimalSelecionado() != null) {
            setTableModel(table, new TratamentoTableModel(TratamentoDAO.getInstance().retrieveByAnimal(Controller.getAnimalSelecionado().getCodigo()))); 
            return true;
        } else {
            setTableModel(table, new TratamentoTableModel(new ArrayList()));
            return false;
        }
    }
    
    public static boolean jTableMostraConsultas(JTable table) {
        if(Controller.getTratamentoSelecionado() != null) {
            setTableModel(table, new ConsultaTableModel(ConsultaDAO.getInstance().retrievebyTratamento(Controller.getTratamentoSelecionado().getCodigo()))); 
            return true;
        } else {
            setTableModel(table, new ConsultaTableModel(new ArrayList()));
            return false;
        }
    }
    
    public static boolean jTableMostraExames(JTable table) {
        if(Controller.getConsultaSelecionada() != null) {
            setTableModel(table, new ExameTableModel(ExameDAO.getInstance().retrieveByConsulta(Controller.getConsultaSelecionada().getCodigo()))); 
            return true;
        } else {
            setTableModel(table, new ExameTableModel(new ArrayList()));
            return false;
        }
    }
    
    public static boolean jTableMostraPagamentos(JTable table) {
        if(Controller.getConsultaSelecionada() != null) {
            setTableModel(table, new ExameTableModel(ExameDAO.getInstance().retrieveByConsulta(Controller.getConsultaSelecionada().getCodigo()))); 
            return true;
        } else {
            setTableModel(table, new ExameTableModel(new ArrayList()));
            return false;
        }
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
    
    // Apaga veterinário do banco. Precisa excluir mais entidades junto?
    public static void deletarVet(Veterinario veterinario) {
        VeterinarioDAO.getInstance().delete(veterinario);
    }
    
    // Tratamento ?
    
    
    // Consultas
    //Deletar consulta: tem que deletar o pagamento e exames registrados
    public static void deletarConsulta(Consulta consulta) {
        List<Exame> exames = ExameDAO.getInstance().retrieveByConsulta(consulta.getCodigo());
       
        for(Exame exame : exames) {
           ExameDAO.getInstance().delete(exame);
        }
        
        List<Pagamento> pagamentos = PagamentoDAO.getInstance().retrieveByConsulta(consulta.getCodigo());
        
        for(Pagamento pagamento : pagamentos) {
           PagamentoDAO.getInstance().delete(pagamento);
        }
        
        ConsultaDAO.getInstance().delete(consulta);
    }
    
    // Exames
    public static Exame adicionarExame(String tipo, Date dataSolicitacao, String status) {
        //Consulta consulta = ConsultaDAO.getInstance().retrieveByName(classeAnimal);
        //int cliente_id = getClienteSelecionado().getCodigo();
        return ExameDAO.getInstance().create(tipo, dataSolicitacao, status, consultaSelecionada.getCodigo()); // ? 
    }
    
    public static void deletarExame(Exame exame) {
        ExameDAO.getInstance().delete(exame);
    }
    
    // Pagamentos
    public static Pagamento adicionarPagamento(Double valor, boolean consultaPaga) {
        //Consulta consulta = ConsultaDAO.getInstance().retrieveByName(classeAnimal);
        //int cliente_id = getClienteSelecionado().getCodigo();
        return PagamentoDAO.getInstance().create(valor, consultaPaga, consultaSelecionada.getCodigo()); // ? 
    }
    
    public static void deletarPagamento(Pagamento pagamento) {
        PagamentoDAO.getInstance().delete(pagamento);
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
    
    public static Tratamento getTratamentoSelecionado() {
        return tratamentoSelecionado;
    }
    
    public static Consulta getConsultaSelecionada() {
        return consultaSelecionada;
    }
    
    public static Exame getExameSelecionado() {
        return exameSelecionado;
    }
    
    public static Pagamento getPagamentoSelecionado() {
        return pagamentoSelecionado;
    }
}
