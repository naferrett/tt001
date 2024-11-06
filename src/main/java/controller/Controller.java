package controller;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public static Veterinario vetSelecionado = null;
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
    @Getter
    private static String femeaSelecionado = null;
    @Getter
    private static String machoSelecionado = null;
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
           deletarAnimal(animal);
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
    
    public static void deletarAnimal(Animal animal) {
        List<Tratamento> tratamentos = TratamentoDAO.getInstance().retrieveByAnimal(animal.getCodigo());
        
        for(Tratamento tratamento : tratamentos) { 
           deletarTratamento(tratamento);
        }
        
        AnimalDAO.getInstance().delete(animal);
    }

    // Sexo do animal
    public static String femeaSelecionado() {
        return "F";
    }

    public static String machoSelecionado() {
        return "M";
    }

    // Veterinário
    public static boolean jTableMostraVets(JTable table) {
        if(Controller.getAnimalSelecionado() != null) {
            setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveByEspecialidade(Controller.getAnimalSelecionado().getClasse_animal())));
            return true;
        } else {
            setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveAll()));
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
    
    public static Tratamento adicionarTratamento(String dataIni, String dataFim, String descricao) {
        return TratamentoDAO.getInstance().create(dataIni, dataFim, descricao, getAnimalSelecionado().getCodigo());
    }
    
    public static void deletarTratamento(Tratamento tratamento) {
        List<Consulta> consultas = ConsultaDAO.getInstance().retrievebyTratamento(tratamento.getCodigo());
       
        for(Consulta consulta : consultas) {
           deletarConsulta(consulta);
        }
        
        TratamentoDAO.getInstance().delete(tratamento);
    }

    // Verificar se tratamento existe antes de agendar consulta
    public static boolean tratamentoExiste(int codigoTratamento) {
        if(TratamentoDAO.getInstance().retrieveById(codigoTratamento) == null) {
            return false;
        }
        return true;
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
    
    // Cadastra novo agendamento de consulta
    public static boolean agendarConsulta(String data, int vetSelecionado, String status, Integer tratamentoId, String periodo, Double valor, Double valorPago, String dataPagamento, String motivo, String observacoes, boolean pagamentoEfetuado) {
        List<Consulta> consultasComVetSelecionado = ConsultaDAO.getInstance().retrievebyVeterinario(vetSelecionado);
        
        for(Consulta consulta: consultasComVetSelecionado) {
            if (consulta.getData().equals(data) && consulta.getPeriodo().equals(periodo)) {
                return false;
            }
        }

        String vetSelecionadoNome = (VeterinarioDAO.getInstance().retrieveById(vetSelecionado)).getNome();

        String resultados = "";
        Consulta novaConsulta = ConsultaDAO.getInstance().create(data, periodo, vetSelecionado, vetSelecionadoNome, status, motivo, observacoes, resultados, tratamentoId);
        PagamentoDAO.getInstance().create(valor, valorPago, pagamentoEfetuado, dataPagamento, novaConsulta.getCodigo());
        return true;
    }

    public static void jTableMostraTodasConsultas(JTable table) {
        setTableModel(table, new TodasConsultasTableModel(ConsultaDAO.getInstance().retrieveAll()));
    }
    
    public static List<Object> mostrarTodasConsultas() {
        return ConsultaDAO.getInstance().retrieveAll();
    }

    public static List<Object> buscarConsultaPorData(String data) {
        return ConsultaDAO.getInstance().retrievebyData(data);
    }
    
    public static void atualizaBuscaConsultaPorData(JTable table, String data) {
        ((GenericTableModel)table.getModel()).addListOfItems(Controller.buscarConsultaPorData(data));
    }
    
    public static List mostrarConsultasRecentes() {
    List consultas = ConsultaDAO.getInstance().retrieveAll();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    consultas.sort(new Comparator<Consulta>() {
        @Override
        public int compare(Consulta c1, Consulta c2) {
            try {
                Date data1 = dateFormat.parse(c1.getData());
                Date data2 = dateFormat.parse(c2.getData());
                return data2.compareTo(data1); // da mais recente pra mais antiga
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    });

    return consultas;
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
            setTableModel(table, new PagamentoTableModel(PagamentoDAO.getInstance().retrieveByConsulta(Controller.getConsultaSelecionada().getCodigo())));
            return true;
        } else {
            setTableModel(table, new PagamentoTableModel(new ArrayList()));
            return false;
        }
    }

    public static Pagamento adicionarPagamento(Double valor, Double valorPago, String dataPagamento, boolean consultaPaga) {
        return PagamentoDAO.getInstance().create(valor, valorPago, consultaPaga, dataPagamento, consultaSelecionada.getCodigo());
    }
    
    public static void deletarPagamento(Pagamento pagamento) {
        PagamentoDAO.getInstance().delete(pagamento);
    }

    // Classe Animal
    public static void jTableMostraClasseAnimal(JTable table) {
        setTableModel(table, new ClasseAnimalTableModel(ClasseAnimalDAO.getInstance().retrieveAll()));
    }


    public static void criarClassesAnimais() {
        ClasseAnimalDAO classeAnimal = ClasseAnimalDAO.getInstance();
        classeAnimal.create("Anfíbio");
        classeAnimal.create("Ave");
        classeAnimal.create("Mamífero");
        classeAnimal.create("Peixe");
        classeAnimal.create("Réptil");
    }
}
