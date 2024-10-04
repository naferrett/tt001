/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import model.ClasseAnimal;
import model.Veterinario;
import model.dao.ClasseAnimalDAO;
import model.dao.VeterinarioDAO;

/**
 *
 * @author nathf
 */
public class VeterinarioTableModel extends GenericTableModel {
    
    public VeterinarioTableModel(List vDados){
        super(vDados, new String[]{"Nome", "Endereço", "Telefone", "Especialidade", "Período de Atendimento", "CRMV"});
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
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return veterinario.getNome();
            case 1:
                return veterinario.getEndereco();
            case 2:
                return veterinario.getTelefone();
            case 3:
                ClasseAnimal classeAnimal = ClasseAnimalDAO.getInstance().retrieveById(veterinario.getEspecialidade());
                if (classeAnimal != null)
                    return classeAnimal.getNomeClasse();
                return "";
            case 4:
                return veterinario.getHoraAtendimento();   
            case 5:
                return veterinario.getCrmv();   
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                veterinario.setNome((String)aValue);
                break;
            case 1:
                veterinario.setEndereco((String)aValue);
                break;
            case 2:
                veterinario.setTelefone((String)aValue);    
                break;
            case 3:
                ClasseAnimal classeAnimal = ClasseAnimalDAO.getInstance().retrieveByName((String)aValue);
                if (classeAnimal == null)
                    classeAnimal = ClasseAnimalDAO.getInstance().create((String)aValue);
                
                veterinario.setEspecialidade(classeAnimal.getCodigo());
                break;
            case 4:
                veterinario.setHoraAtendimento((String)aValue);
                break;
            case 5:
                veterinario.setCrmv((String)aValue);
                break;    
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        VeterinarioDAO.getInstance().update(veterinario);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }      
}
