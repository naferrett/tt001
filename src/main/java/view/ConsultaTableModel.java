/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import model.Consulta;
import model.Pagamento;
import model.Veterinario;
import model.dao.ConsultaDAO;
import model.dao.PagamentoDAO;
import model.dao.VeterinarioDAO;

/**
 *
 * @author nathf
 */
public class ConsultaTableModel extends GenericTableModel{
        
    public ConsultaTableModel(List vDados){
        super(vDados, new String[]{"Data", "Periodo", "Veterinário", "Status", "Motivo", "Observação", "Resultados"});
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
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consulta consultasTratamento = (Consulta) vDados.get(rowIndex);
                
        switch (columnIndex) {
            case 0:
                return consultasTratamento.getData();
            case 1:
                return  consultasTratamento.getPeriodo();
            case 2:
                Veterinario vetResponsavel = VeterinarioDAO.getInstance().retrieveById(consultasTratamento.getVeterinario_id());
                if(vetResponsavel != null)
                    return vetResponsavel.getNome();
                return "";
            case 3:
                return consultasTratamento.getStatus();
            case 4:
                return consultasTratamento.getMotivo();
            case 5:
                return consultasTratamento.getObservacoes();
            case 6:
                return consultasTratamento.getResultados();
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
