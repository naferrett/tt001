package model;

import lombok.Data;

@Data
public class Veterinario {
    private int codigo;
    private String nome;
    private String endereco;
    private String telefone;
    private String crmv;
    private int especialidade;
    private String horaAtendimento;
}
