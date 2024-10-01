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
        super(vDados, new String[]{"Data", "Veterinário", "Status", "Motivo", "Observação"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Timestamp.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
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
                Veterinario vetResponsavel = VeterinarioDAO.getInstance().retrieveById(consultasTratamento.getVeterinario_id());
                if(vetResponsavel != null)
                    return vetResponsavel.getNome();
                return "";
            case 2:
                return consultasTratamento.getStatus();
            case 3:
                return consultasTratamento.getMotivo();
            case 4:
                return consultasTratamento.getObservacoes();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Consulta consultasTratamento = (Consulta) vDados.get(rowIndex);
                
        switch (columnIndex) {
            case 0:
                consultasTratamento.setData((Timestamp)aValue);
                break;
            case 1:
                // Veterinário não é alterado por meio dessa tabela?
                break;
            case 2:
                consultasTratamento.setStatus((String)aValue);    
                break;
            case 3:
                consultasTratamento.setMotivo((String)aValue); 
                break;
            case 4:
                consultasTratamento.setObservacoes((String)aValue);
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
