package view;

import model.ClasseAnimal;
import model.Exame;
import model.dao.ClasseAnimalDAO;
import model.dao.ExameDAO;

import java.util.List;

public class ClasseAnimalTableModel extends GenericTableModel {
    public ClasseAnimalTableModel(List vDados){
        super(vDados, new String[]{"Código", "Classe"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClasseAnimal classeAnimal = (ClasseAnimal) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return classeAnimal.getCodigo();
            case 1:
                return classeAnimal.getNomeClasse();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ClasseAnimal classeAnimal = (ClasseAnimal) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                classeAnimal.setCodigo((Integer) aValue);
                break;
            case 1:
                classeAnimal.setNomeClasse((String) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        ClasseAnimalDAO.getInstance().update(classeAnimal);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
