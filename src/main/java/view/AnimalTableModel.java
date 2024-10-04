/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import model.Animal;
import model.ClasseAnimal;
import model.dao.AnimalDAO;
import model.dao.ClasseAnimalDAO;

/**
 *
 * @author nathf
 */
public class AnimalTableModel extends GenericTableModel {
        
    public AnimalTableModel(List vDados){
        super(vDados, new String[]{"Nome", "Classe", "Espécie", "Raça", "Sexo", "Peso"});
    }
   
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class; // return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return char.class;
            case 5:
                return Double.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return animal.getNome();
            case 1:
                ClasseAnimal classeAnimal = ClasseAnimalDAO.getInstance().retrieveById(animal.getClasse_animal());
                if (classeAnimal != null)
                    return classeAnimal.getCodigo(); //return classeAnimal.getNome_classe();
                return "";
            case 2:
                return animal.getEspecie();
            case 3:
                return animal.getRaca();
            case 4:
                return animal.getSexo(); 
            case 5:
                return animal.getPeso();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Animal animal = (Animal)vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                animal.setNome((String)aValue);
                break;
            case 1:
                ClasseAnimalDAO.getInstance().retrieveById((Integer)aValue);
                // ClasseAnimalDAO.getInstance().retrieveByName((String)aValue);
                break;
            case 2:
                animal.setEspecie((String)aValue);    
                break;
            case 3:
                animal.setRaca((String)aValue);
                break;
            case 4:
                animal.setSexo((String)aValue);
                break;
            case 5:
                animal.setPeso((Double)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        AnimalDAO.getInstance().update(animal);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
