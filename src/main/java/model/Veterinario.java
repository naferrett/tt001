package model;

import lombok.Data;

@Data
public class Veterinario extends Pessoa {
    private String crmv;
    private int especialidade;
    private String horaAtendimento;
}
