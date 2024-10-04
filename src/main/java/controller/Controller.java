package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;

import lombok.Getter;
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
import view.*;

public class Controller {
    @Getter
    private static Cliente clienteSelecionado = null;
    @Getter
    private static Animal animalSelecionado = null;
    @Getter
    private static Veterinario vetSelecionado = null;
    @Getter
    private static Tratamento tratamentoSelecionado = null;
    @Getter
    private static Consulta consultaSelecionada = null;
    @Getter
    private static Exame exameSelecionado = null;
    @Getter
    private static Pagamento pagamentoSelecionado = null;
    @Getter
    private static ClasseAnimal classeAnimalSelecionada = null;
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
        } else if (selecionado instanceof ClasseAnimal) {
            classeAnimalSelecionada = (ClasseAnimal) selecionado;
        }
    }

    // Cliente
    public static void jTableMostraClientes(JTable table) {
        setTableModel(table, new ClienteTableModel(ClienteDAO.getInstance().retrieveAll()));
    }

    public static List<Object> buscarCliente(String nome) {
        return ClienteDAO.getInstance().retrieveBySimilarName(nome);
    }

    public static void atualizarBuscaCliente(JTable table, String nome) {
        ((GenericTableModel)table.getModel()).addListOfItems(Controller.buscarCliente(nome));
    }

    public static Cliente adicionarCliente(String nome, String endereco, String telefone, String email, String cpf) {
        return ClienteDAO.getInstance().create(nome, endereco, telefone, email, cpf);
    }

    public static List<Object> mostrarTodosClientes() {
        return ClienteDAO.getInstance().retrieveAll();
    }

    public static void deletarCliente(Cliente cliente) {
        List<Animal> animais = AnimalDAO.getInstance().retrieveByCliente(cliente.getCodigo()); 
        
        for(Animal animal : animais) { // Deletar um cliente inclui deletar todos os animais relacionados a ele no banco
           AnimalDAO.getInstance().delete(animal);
        }
        
        ClienteDAO.getInstance().delete(cliente);
    }

    // Animal
    public static boolean jTableMostraAnimais(JTable table) {
        if(Controller.getClienteSelecionado() != null) {
            setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveByCliente(Controller.getClienteSelecionado().getCodigo())));
            return true;
        } else {
            setTableModel(table, new AnimalTableModel(new ArrayList()));
            return false;
        }
    }

    public static Animal adicionarAnimal(String nome, String classeAnimal, String especie, String raca, String sexo, Double peso) {
        return AnimalDAO.getInstance().create(nome, especie, raca, sexo, peso, getClienteSelecionado().getCodigo(), getClasseAnimalSelecionada().getCodigo());
    }

//    public static Animal adicionarAnimal(String nome, String classeAnimal, String especie, String raca, String sexo, Double peso) {
//        ClasseAnimal classe = ClasseAnimalDAO.getInstance().retrieveByName(classeAnimal);
//        int cliente_id = getClienteSelecionado().getCodigo();
//        return AnimalDAO.getInstance().create(nome, especie, raca, sexo, peso, cliente_id, classe.getCodigo());
//    }
    
    public static void deletarAnimal(Animal animal) {
        AnimalDAO.getInstance().delete(animal);
    }

    // Veterinário
    public static boolean jTableMostraVets(JTable table) {
        if(Controller.getAnimalSelecionado() != null) {
            setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveByEspecialidade(Controller.getAnimalSelecionado().getClasse_animal())));
            return true;
        } else {
            setTableModel(table, new VeterinarioTableModel(new ArrayList()));
            return false;
        }
    }

    public static List<Object> buscarVeterinario(String nome) {
        return VeterinarioDAO.getInstance().retrieveBySimilarName(nome);
    }

    public static void atualizarBuscaVeterinario(JTable table, String nome) {
        ((GenericTableModel)table.getModel()).addListOfItems(Controller.buscarVeterinario(nome));
    }
    
    public static Veterinario adicionarVeterinario(String nome, String endereco, String telefone, String especialidade, String periodo, String crmv) {
        return VeterinarioDAO.getInstance().create(nome, endereco, telefone, crmv, getClasseAnimalSelecionada().getCodigo(), periodo);
    }

//    public static Veterinario adicionarVeterinario(String nome, String endereco, String telefone, String especialidade, String periodo, String crmv) {
//        ClasseAnimal especialidadeVet = ClasseAnimalDAO.getInstance().retrieveByName(especialidade);
//        return VeterinarioDAO.getInstance().create(nome, endereco, telefone, crmv, especialidadeVet.getCodigo(), periodo);
//    }

    public static List<Object> mostrarTodosVets() {
        return VeterinarioDAO.getInstance().retrieveAll();
    }
    
    public static void deletarVet(Veterinario veterinario) {
        VeterinarioDAO.getInstance().delete(veterinario);
    }
    
    // Tratamento
    public static boolean jTableMostraTratamentos(JTable table) {
        if(Controller.getAnimalSelecionado() != null) {
            setTableModel(table, new TratamentoTableModel(TratamentoDAO.getInstance().retrieveByAnimal(Controller.getAnimalSelecionado().getCodigo())));
            return true;
        } else {
            setTableModel(table, new TratamentoTableModel(new ArrayList()));
            return false;
        }
    }
    
    
    // Consultas
    public static boolean jTableMostraConsultas(JTable table) {
        if(Controller.getTratamentoSelecionado() != null) {
            setTableModel(table, new ConsultaTableModel(ConsultaDAO.getInstance().retrievebyTratamento(Controller.getTratamentoSelecionado().getCodigo())));
            return true;
        } else {
            setTableModel(table, new ConsultaTableModel(new ArrayList()));
            return false;
        }
    }

    public static void deletarConsulta(Consulta consulta) { // Deleta os pagamentos e exames relacionados no banco
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
    public static boolean jTableMostraExames(JTable table) {
        if(Controller.getConsultaSelecionada() != null) {
            setTableModel(table, new ExameTableModel(ExameDAO.getInstance().retrieveByConsulta(Controller.getConsultaSelecionada().getCodigo())));
            return true;
        } else {
            setTableModel(table, new ExameTableModel(new ArrayList()));
            return false;
        }
    }

    public static Exame adicionarExame(String tipo, String dataSolicitacao, String status) {
        return ExameDAO.getInstance().create(tipo, dataSolicitacao, status, consultaSelecionada.getCodigo()); // ? 
    }
    
    public static void deletarExame(Exame exame) {
        ExameDAO.getInstance().delete(exame);
    }
    
    // Pagamentos
    public static boolean jTableMostraPagamentos(JTable table) {
        if(Controller.getConsultaSelecionada() != null) {
            setTableModel(table, new ExameTableModel(ExameDAO.getInstance().retrieveByConsulta(Controller.getConsultaSelecionada().getCodigo())));
            return true;
        } else {
            setTableModel(table, new ExameTableModel(new ArrayList()));
            return false;
        }
    }

    public static Pagamento adicionarPagamento(Double valor, boolean consultaPaga) {
        return PagamentoDAO.getInstance().create(valor, consultaPaga, consultaSelecionada.getCodigo()); // ? 
    }
    
    public static void deletarPagamento(Pagamento pagamento) {
        PagamentoDAO.getInstance().delete(pagamento);
    }

    // Classe Animal
    public static void jTableMostraClasseAnimal(JTable table) {
        setTableModel(table, new ClasseAnimalTableModel(ClasseAnimalDAO.getInstance().retrieveAll()));
    }

    // Criação das classes de animais no banco
    public static void criarClassesAnimais() {
        ClasseAnimalDAO classeAnimal = ClasseAnimalDAO.getInstance();

        classeAnimal.create("Anfíbio");
        classeAnimal.create("Ave");
        classeAnimal.create("Mamífero");
        classeAnimal.create("Peixe");
        classeAnimal.create("Réptil");
    }

    public static void printAllClasseAnimais() {
        ClasseAnimalDAO dao = ClasseAnimalDAO.getInstance();
        //dao.deleteAllGreaterThanFive();

        // Chame o método para recuperar todos os animais
        List<ClasseAnimal> listaClasseAnimais = dao.retrieveAll();

        // Verifique se a lista não está vazia
        if (listaClasseAnimais.isEmpty()) {
            System.out.println("Nenhuma classe animal encontrada.");
        } else {
            // Itere pela lista e imprima as informações
            for (ClasseAnimal classeAnimal : listaClasseAnimais) {
                System.out.println("Código: " + classeAnimal.getCodigo() + ", Nome da Classe: " + classeAnimal.getNomeClasse());
            }
        }
    }

    public static void exibirAnimaisDoCliente(int clienteId) {
        // Buscar cliente pelo ID
        Cliente cliente = ClienteDAO.getInstance().retrieveById(clienteId);

        // Verificar se o cliente foi encontrado
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente.getNome());

            // Buscar animais do cliente
            List<Animal> animais = AnimalDAO.getInstance().retrieveByCliente(cliente.getCodigo());

            // Verificar se o cliente tem animais
            if (!animais.isEmpty()) {
                System.out.println("Animais do cliente " + cliente.getNome() + ":");
                for (Animal animal : animais) {
                    System.out.println("- " + animal.getNome() + " (" + animal.getEspecie() + ")");
                }
            } else {
                System.out.println("O cliente " + cliente.getNome() + " não possui animais cadastrados.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public static Animal adicionarAnimalParaCliente1() {
        String nome = "silvinho";
        String especie = "cachorro";
        String raca = "labrador";
        String sexo = "M";
        double peso = 30.5;
        int clienteId = 1; // Cliente de código 1
        int classeAnimal = 1; // Exemplo de classe do animal (precisa definir corretamente)

        // Cria e retorna o animal
        return AnimalDAO.getInstance().create(nome, especie, raca, sexo, peso, clienteId, classeAnimal);
    }

}
