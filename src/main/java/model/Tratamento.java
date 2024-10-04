package model;

import lombok.Data;

import java.util.List;

@Data
public class Tratamento {
    private int codigo;
    private String dataInicio;
    private String dataFim;
    private String descricao;
    private int animalTratado;
    private List<Consulta> consultas;
}
