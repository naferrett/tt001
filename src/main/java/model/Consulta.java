package model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Consulta {
    private int codigo;
    private Timestamp data;
    private int veterinario_id;
    private String motivo;
    private String status;
    private String observacoes;
    private int tratamento_id;
    private List<Exame> exames;
}
