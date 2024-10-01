package model;

import lombok.Data;

import java.util.List;

@Data
public class Cliente {
    private int codigo;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String cpf;
    private List<Animal> animais;
}
