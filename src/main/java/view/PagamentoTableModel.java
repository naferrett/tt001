/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import model.Pagamento;
import model.dao.PagamentoDAO;

/**
 *
 * @author nathf
 */
public class PagamentoTableModel extends GenericTableModel {
        
    public PagamentoTableModel(List vDados){
        super(vDados, new String[]{"Valor", "Data", "Efetuado"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Double.class;
            case 1:   
                return String.class;
            case 2:
                return Boolean.class;                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pagamento pagamentoConsulta = (Pagamento) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return pagamentoConsulta.getValor();
            case 1:
                return pagamentoConsulta.getDataPagamento();
            case 2:
                return pagamentoConsulta.isConsultaPaga();    
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Pagamento pagamentoConsulta = (Pagamento) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                pagamentoConsulta.setValor((Double)aValue);
                break;
            case 1:
                pagamentoConsulta.setDataPagamento((String)aValue);
            case 2:
                pagamentoConsulta.setConsultaPaga((Boolean)aValue);    
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        PagamentoDAO.getInstance().update(pagamentoConsulta);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }       
}
