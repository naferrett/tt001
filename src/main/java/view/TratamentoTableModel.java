/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.sql.Date;
import java.util.List;
import model.Animal;
import model.Cliente;
import model.Tratamento;
import model.dao.AnimalDAO;
import model.dao.ClienteDAO;
import model.dao.TratamentoDAO;

/**
 *
 * @author nathf
 */
public class TratamentoTableModel extends GenericTableModel{
        
    public TratamentoTableModel(List vDados){
        super(vDados, new String[]{"Data de Início", "Data do Fim", "Descrição"});
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
        Tratamento tratamento = (Tratamento) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return tratamento.getDataInicio();
            case 1:
                return tratamento.getDataFim();
            case 2:
                return tratamento.getDescricao();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tratamento tratamento = (Tratamento) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                tratamento.setDataInicio((String)aValue);
                break;
            case 1:
                tratamento.setDataFim((String)aValue);
                break;
            case 2:
                tratamento.setDescricao((String)aValue);    
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        TratamentoDAO.getInstance().update(tratamento);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }      
}
