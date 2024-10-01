package model;

import lombok.Data;

import java.util.List;

@Data
public class Cliente extends Pessoa {
    private String email;
    private String cpf;
    private List<Animal> animais;
}
