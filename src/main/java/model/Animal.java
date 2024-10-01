package model;

import lombok.Data;

import java.util.List;

@Data
public class Animal {
    private int codigo;
    private String nome;
    private String especie;
    private String raca;
    private char sexo;
    private double peso;
    private int cliente_id;
    private int classe_animal;
    private List<Tratamento> tratamentos;
}
