/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.sql.Date;
import java.util.List;
import model.Exame;
import model.dao.ExameDAO;

/**
 *
 * @author nathf
 */
public class ExameTableModel extends GenericTableModel {
        
    public ExameTableModel(List vDados){
        super(vDados, new String[]{"Tipo", "Data de Solicitação", "Status"});
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
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Exame examesConsulta = (Exame) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return examesConsulta.getTipo();
            case 1:
                return examesConsulta.getDataSolicitacao(); 
            case 2:
                return examesConsulta.getStatus();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Exame examesConsulta = (Exame) vDados.get(rowIndex);
                
        switch (columnIndex) {
            case 0:
                examesConsulta.setTipo((String)aValue);
                break;
            case 1:
                examesConsulta.setDataSolicitacao((String) aValue);
                break;
            case 2:
                examesConsulta.setStatus((String)aValue);    
                break; 
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        ExameDAO.getInstance().update(examesConsulta);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }   
}
