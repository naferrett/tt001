/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import model.Animal;
import model.Cliente;
import model.Consulta;
import model.Tratamento;
import model.Veterinario;
import model.dao.AnimalDAO;
import model.dao.ClienteDAO;
import model.dao.ConsultaDAO;
import model.dao.TratamentoDAO;
import model.dao.VeterinarioDAO;

/**
 *
 * @author nathf
 */
public class TodasConsultasTableModel extends GenericTableModel {
    
    public TodasConsultasTableModel(List vDados){
        super(vDados, new String[]{"Data", "Periodo", "Veterinário", "Status", "Motivo", "Observação", "Resultados", "Cliente", "Animal"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            case 7:
                return String.class;
            case 8:
                return String.class;    
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consulta consultas = (Consulta) vDados.get(rowIndex);
        
        Tratamento tratamento = TratamentoDAO.getInstance().retrieveById(consultas.getTratamento_id());
        Animal animal = AnimalDAO.getInstance().retrieveById(tratamento.getAnimalTratado());
                
        switch (columnIndex) {
            case 0:
                return consultas.getData();
            case 1:
                return  consultas.getPeriodo();
            case 2:
                Veterinario vetResponsavel = VeterinarioDAO.getInstance().retrieveById(consultas.getVeterinario_id());
                if(vetResponsavel != null)
                    return vetResponsavel.getNome();
                return "";
            case 3:
                return consultas.getStatus();
            case 4:
                return consultas.getMotivo();
            case 5:
                return consultas.getObservacoes();
            case 6:
                return consultas.getResultados();
            case 7:
                Cliente cliente = ClienteDAO.getInstance().retrieveById(animal.getCliente_id());
                if(cliente != null)
                    return cliente.getNome();
                return "";
            case 8:
                if(animal != null)
                    return animal.getNome();
                return "";   
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Consulta consultasTratamento = (Consulta) vDados.get(rowIndex);
                
        switch (columnIndex) {
            case 0:
                consultasTratamento.setData((String) aValue);
                break;
            case 1:
                consultasTratamento.setPeriodo((String) aValue);
                break;
            case 2:
                // Veterinário não é alterado por meio dessa tabela?
                break;
            case 3:
                consultasTratamento.setStatus((String)aValue);    
                break;
            case 4:
                consultasTratamento.setMotivo((String)aValue); 
                break;
            case 5:
                consultasTratamento.setObservacoes((String)aValue);
                break;
            case 6:
                consultasTratamento.setResultados((String)aValue);
                break;
            case 7:
                //
                break;
            case 8:
                consultasTratamento.setResultados((String)aValue);
                break;    
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        ConsultaDAO.getInstance().update(consultasTratamento);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }   
}
