package model;

import lombok.Data;
import java.sql.Date;

@Data
public class Exame {
    private int codigo;
    private String tipo;
    private String dataSolicitacao;
    private String status;
    private int consulta_id;
}
